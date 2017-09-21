package com.mmall.vo;

import java.time.LocalDateTime;

/**
 * Created by u6035457 on 9/1/2017.
 */
public class UserLoginRecord {
    /*上一次登录失败的时间*/
    private LocalDateTime lastLoginTime;
    /*尝试失败登录的次数*/
    private Integer errorNum;

    private String username;

    public UserLoginRecord() {
    }

    public UserLoginRecord(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public UserLoginRecord(LocalDateTime LocalDateTime, Integer errorNum) {
        this.lastLoginTime = lastLoginTime;
        this.errorNum = errorNum;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserLoginRecord{" +
                "lastLoginTime=" + lastLoginTime +
                ", errorNum=" + errorNum +
                ", username='" + username + '\'' +
                '}';
    }
}
