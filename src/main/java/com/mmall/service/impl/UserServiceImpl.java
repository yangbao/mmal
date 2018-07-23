package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by u6035457 on 8/28/2017.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public ServerResponse<User> login(String username, String password) {
        int userCount = userMapper.checkUser(username);
        if(userCount == 0){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //TODO password 加密
        User user  = userMapper.selectLogin(username,MD5Util.MD5EncodeUtf8(password));
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);//密码置空
        return ServerResponse.createBySuccess("登录成功",user);
    }
    //TODO 密码输入超过5次,用户名3次, 显示验证码
    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = this.validate(user.getUsername(),Const.VALIDATE_USERNAME);
        //与校验结果相反
        if(validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.validate(user.getEmail(),Const.VALIDATE_EMAIL);
        if(validResponse.isSuccess()){
            return validResponse;
        }
        /*MD5 加密也可以用common包的,或者Guava 的工具类
         DigestUtils.md5Hex(user.getPassword().getBytes());
         user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
         Hasher hasher = Hashing.md5().newHasher();
         hasher.putString(user.getPassword(), StandardCharsets.UTF_8);
        */
        //MD5 加密, user -->set 一个角色 ROLE, 常量接口-->枚举太重
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));//set 加密后的password
        int count = userMapper.insert(user);
        if(count == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }
    public ServerResponse<String> validate(String validatedString, String type){

        if (StringUtils.isNotBlank(type)) {
            if (Const.VALIDATE_USERNAME.equals(type)) {
                if (userMapper.checkUser(validatedString) > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            } else if (Const.VALIDATE_EMAIL.equals(type)) {
                if (userMapper.checkEmail(validatedString) > 0) {
                    return ServerResponse.createByErrorMessage("Email已经存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }
    //忘记密码获取提示问题
    @Override
    public ServerResponse<String> selectQuestion(String username) {

        if(!this.validate(username,Const.VALIDATE_USERNAME).isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    /**
     * 用 guava 实现 有效期的问题
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @Override
    public ServerResponse<String> checkForgetAnswer(String username,String question, String answer) {
    //  用count 效率高, 不比较返回answer
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            String forgetToken = UUID.randomUUID().toString();//唯一值
            TokenCache.setToCache(TokenCache.TOKEN_PREFIX+username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }
    //忘记密码根据提示问题找回密码, 重置密码
    @Override
    public ServerResponse<String> forgetResetPassword(String username, String newPassword, String tokenString) {
        //TODO 密码的位数等校验
        if (StringUtils.isBlank(newPassword)){
            return ServerResponse.createByErrorMessage("密码不能为空");
        }
        if (StringUtils.isBlank(tokenString)){
            return ServerResponse.createByErrorMessage("Token不能为空");
        }
        if(!this.validate(username,Const.VALIDATE_USERNAME).isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String cachedToken = TokenCache.getFromCache(TokenCache.TOKEN_PREFIX+username);
        String md5NewPassword = MD5Util.MD5EncodeUtf8(newPassword);
        if (StringUtils.equals(tokenString,cachedToken)){
            int resultCount = userMapper.updatePassword(username,md5NewPassword);
            if(resultCount > 0){
                return ServerResponse.createBySuccessMessage("修改成功");
            }
        }else{
            return ServerResponse.createByErrorMessage("Token 错误, 请重新获取");
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }
    //登录状态的重置密码
    @Override
    public ServerResponse<String> resetPassword(String username, String newPassword, User user) {
        //不能只是检测password, 防止横向越权, 应该检测该userID 和 password的对应关系
        //如果没有这种对应关系，可能会修改别人的密码
        /*
         * select cout(1)
         from mmall_user
         where id = #{userId}
         and password = #{password};
         </select>
         */
        int resultCount = userMapper.checkUserPassword(user.getId(), MD5Util.MD5EncodeUtf8(user.getPassword()));
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        //TODO newPassword 的验证
        int insertCount = userMapper.updateByPrimaryKeySelective(user);//哪个不为null, 更新哪个
        if (insertCount > 0) {
            user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));//更新session
            return ServerResponse.createBySuccessMessage("密码修改成功");
        }
        return ServerResponse.createByErrorMessage("密码修改失败");
    }

    @Override
    public ServerResponse<User> modifyUserInformation(User user) {
        //username 不能被更改
        //email(注意不校验这个当前user对应的email,因为有可能没有改)
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) {
            ServerResponse.createByErrorMessage("email 已经存在,请更换后再尝试");
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setId(user.getId());
        newUser.setEmail(user.getEmail());
        newUser.setAnswer(user.getAnswer());
        newUser.setPhone(user.getPhone());
        newUser.setQuestion(user.getQuestion());
        int updateCount = userMapper.updateByPrimaryKeySelective(newUser);
        if (updateCount > 0) {
            return ServerResponse.createBySuccess("更新个人信息成功",newUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer userId) {

        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("找不到当前用户");//注意返回的user泛型, 也没事
        }//密码置空
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    public static void main(String[] args) {

        System.out.println(StringUtils.equals("", null));


//        System.out.println(StringUtils.isNotBlank(""));
//        System.out.println(StringUtils.isNotEmpty(""));
//        System.out.println(StringUtils.isNotBlank(" "));
//        System.out.println(StringUtils.isNotEmpty(" "));
//
//        try {
//            System.out.println( DigestUtils.md5Hex("bgkp108512".getBytes("UTF-8")));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Hasher hasher = Hashing.md5().newHasher();
//        System.out.println(hasher.putString("bgkp108512", StandardCharsets.UTF_8).hash().toString());

    }
    //backend

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
