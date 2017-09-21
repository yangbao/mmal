package com.mmall.common;

import java.io.Serializable;

/**
 * Created by u6035457 on 8/27/2017.
 */
public class ServerResponse<T> implements Serializable{

    private int status;
    private String message;
    private T data;

    public ServerResponse() {
    }
    public ServerResponse(int code) {
        this.status = code;
    }
    public ServerResponse(int code, String message) {
        this.status = code;
        this.message = message;
    }
    public ServerResponse(int code, T data) {
        this.status = code;
        this.data = data;
    }
    public ServerResponse(int code, String message, T data) {
        this.status = code;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
    public static <T> ServerResponse<T> createBySuccess(){

        return new ServerResponse(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){

        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> ServerResponse<T> createBySuccess(T data){

        return new ServerResponse(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T> ServerResponse<T> createBySuccess(String msg,T data){

        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDescription());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse(errorCode,errorMessage);
    }
    public boolean isSuccess(){
        return this.getStatus() == ResponseCode.SUCCESS.getCode();
    }
}
