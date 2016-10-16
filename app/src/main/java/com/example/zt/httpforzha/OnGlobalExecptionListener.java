package com.example.zt.httpforzha;

/**
 * 全局处理token过期的问题
 * Created by zt on 16/10/10.
 */
public interface OnGlobalExecptionListener {
    boolean handleException(AppException e);

}
