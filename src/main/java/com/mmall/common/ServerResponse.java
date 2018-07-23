package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by u6035457 on 8/27/2017.
 * 封装返回值类型. - 高复用的服务响应对象. 返回前端 需要序列号
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //返回value为空的时候，比如有的data没有就别返回key了
public class ServerResponse<T> implements Serializable{

    private int status;
    private String message;
    private T data; //返回的时候使用泛型, 可以指定也可以不指定. 但是声明的时候用泛型就可以了.
    //-------------------构造器全部私有, 只用我们提供的外部方法----------------------

    private ServerResponse() {
    }
    private ServerResponse(int code) {
        this.status = code;
    }
    //下面2个构造器看起来是有冲突的,实验后发现是int string类型就调用第一个, int 其他调用第二个.
    //如果是需要返回code和message的T data 是否有冲突, 可以用下面的public方法来规避
    private ServerResponse(int code, String message) {
        this.status = code;
        this.message = message;
    }
    private ServerResponse(int code, T data) {
        this.status = code;
        this.data = data;
    }
    private ServerResponse(int code, String message, T data) {
        this.status = code;
        this.message = message;
        this.data = data;
    }
    //-------------------构造器结束,get方法开始----------------------
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
    //-------------------get方法结束----------------------

    //仅仅是成功, 返回成功code
    public static <T> ServerResponse<T> createBySuccess(){

        return new ServerResponse(ResponseCode.SUCCESS.getCode());
    }
    //成功, 返回成功code.message
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){

        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg);
    }
    //成功,返回code, 和data
    public static <T> ServerResponse<T> createBySuccess(T data){

        return new ServerResponse(ResponseCode.SUCCESS.getCode(),data);
    }
    //成功,返回code, message和data
    public static <T> ServerResponse<T> createBySuccess(String msg,T data){

        return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    //失败，code+描述
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDescription());
    }
    //失败，code+message
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse(ResponseCode.ERROR.getCode(),errorMessage);
    }
    //失败，code（特定的error code）+message
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse(errorCode,errorMessage);
    }
    @JsonIgnore // 无需返回给前端， 所以不需要返回而且他也是public的
    public boolean isSuccess(){
        return this.getStatus() == ResponseCode.SUCCESS.getCode();
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
