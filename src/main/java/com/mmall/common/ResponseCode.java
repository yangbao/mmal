package com.mmall.common;

/**
 * Created by u6035457 on 8/28/2017.
 */
public enum ResponseCode {
    SUCCESS(0,"success"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String description;
    ResponseCode(int code,String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
