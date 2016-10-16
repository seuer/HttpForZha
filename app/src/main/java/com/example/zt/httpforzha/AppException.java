package com.example.zt.httpforzha;

/**
 * 全局异常处理
 * Created by zt on 16/10/9.
 */
public class AppException extends  Exception {

    public  int statusCode;
    public  String message;

    public enum  ErrorType{CANCEL,TIMEOUT,SERVERERROR,JSON}
    public ErrorType type;

    public AppException(int statusCode, String responseMessage) {
        super(responseMessage);
        this.type=ErrorType.SERVERERROR;
        this.statusCode=statusCode;
        this.message=responseMessage;

    }

    public AppException(ErrorType type,  String message) {
        super(message);
        this.type=type;
    }
}
