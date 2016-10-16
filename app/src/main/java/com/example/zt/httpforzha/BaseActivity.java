package com.example.zt.httpforzha;

import android.support.v7.app.AppCompatActivity;

/**
 * 在父类统一处理token失效的问题，跳转到登陆页面
 * Created by zt on 16/10/10.
 */
public class BaseActivity extends AppCompatActivity implements  OnGlobalExecptionListener {
    @Override
    public boolean handleException(AppException e) {
         if(e.statusCode==403){
             if("token invalid".equals(e.message)){
                 return true;
             }
         }
        return  false;
    }
}
