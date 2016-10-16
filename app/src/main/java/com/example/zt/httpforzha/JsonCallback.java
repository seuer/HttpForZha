package com.example.zt.httpforzha;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *  用于解析json数据
 * Created by zt on 16/9/30.
 */
public abstract  class JsonCallback<T>  extends AbstractCallBack<T> {


    @Override
    protected T parseJsonData(String result) throws AppException{
        try {
            JSONObject json=new JSONObject(result);
            JSONObject data=json.optJSONObject("data");
            Gson gson=new Gson();
            Type type = ((ParameterizedType)getClass().getGenericSuperclass()).
                    getActualTypeArguments()[0];
            return gson.fromJson(data.toString(),type);
        } catch (JSONException e) {
            throw  new AppException(AppException.ErrorType.JSON,e.getMessage());
        }

    }


}
