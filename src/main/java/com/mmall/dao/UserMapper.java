package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUser(String username);
    //Mybatis 多个参数传递需要用注解， 验证登录的用户名密码
    User selectLogin(@Param("username") String username, @Param("password")String password);

    int checkEmail(String email);

    String selectQuestionByUsername(String username);
    //多参数的情况下用@param注解
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int updatePassword(@Param("username")String username, @Param("newPassword")String newPassword);

    int checkUserPassword(@Param("userId")Integer userId, @Param("password")String password);

    int checkEmailByUserId(@Param("email")String email, @Param("userId")Integer userId);

}