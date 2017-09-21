package com.mmall.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.mmall.common.Const;
import com.mmall.vo.UserLoginRecord;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 设计用户连续3次输入错误密码,出现验证码; 如果10次错误锁定账户一天
 *
 * Created by u6035457 on 9/1/2017.
 */
public class UserLoginRecordUtil {

    //LRU算法
    static Cache<String,UserLoginRecord> localCache= CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).build();
    /**
     * 自是在用户登录失败的时候才会更新失败的时间, 成功的时候会clear这条记录
     */
    public static void updateUserLoginStatus(int loginStatus, String username, LocalDateTime loginTime) throws ExecutionException {
        if(Const.LoginStatus.LOGIN_SUCCESS == loginStatus){
            UserLoginRecord record = localCache.get(username, () -> {
                //Callable只有在缓存值不存在时，才会调用-- TODO 以后可能会做数据保存到数据库,缓存会同步数据库的内容
                return null;
            });
            //use lambda instead
            Optional<UserLoginRecord> recordOptional = Optional.ofNullable(record);
            recordOptional.ifPresent(cachedRecord->{
                cachedRecord.setLastLoginTime(LocalDateTime.now());
                localCache.put(username,record);
            });
            recordOptional.orElseGet(()-> {
                localCache.put(username,new UserLoginRecord(loginTime));
                return null;//no need return
            });
        } else if(Const.LoginStatus.LOGIN_FAIL == loginStatus) {
            UserLoginRecord record = localCache.get(username, () -> {
                //Callable只有在缓存值不存在时，才会调用-- TODO 以后可能会做数据保存到数据库,缓存会同步数据库的内容
                return null;
            });
            //do not use lambda instead
            if(record == null){
                record.setErrorNum(1);
                localCache.put(username,new UserLoginRecord(loginTime));
            }else{
                int errorNum = record.getErrorNum();
                record.setErrorNum(errorNum++);
                record.setLastLoginTime(LocalDateTime.now());
                localCache.put(username,record);
            }
        }
    }
}
