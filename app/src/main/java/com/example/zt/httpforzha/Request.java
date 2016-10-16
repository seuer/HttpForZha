package com.example.zt.httpforzha;

import java.util.Map;

/**
 * 封装请求相关的参数
 * Created by zt on 16/9/25.
 */
public class Request {
    
    public String url;
    public String content;
    public Map<String, String> headers;
    public CallBack callBack;
    public  OnGlobalExecptionListener listener;
    public int maxRetryCount = 5;
    public boolean isCancelled;

    public void setCallBack(CallBack callBack) {
       this.callBack=callBack;   
    }

    public void setonGlobalExceptionListener(OnGlobalExecptionListener listener) {
        this.listener=listener;
    }

    public boolean cancel() {
       return isCancelled=true;

    }

    public enum RequestMethod {GET, POST, DELETE, PUT}

    public RequestMethod method;


    public Request(String url) {
        this.url = url;
        this.method = RequestMethod.GET;

    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;


    }

    public Request(String url, String content, Map<String, String> headers) {
        this.url = url;
        this.content = content;
        this.headers = headers;
    }

}