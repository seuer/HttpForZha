package com.example.zt.httpforzha;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * 封装共同的解析方法
 * Created by zt on 16/10/8.
 */
public abstract class  AbstractCallBack<T> implements CallBack<T> {

     String filePath;
     

    @Override
    public T parse(HttpURLConnection connection,DownLoadProgressListener listener) throws AppException {
        try {
            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                if(filePath==null) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[2 * 1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);

                    }
                    is.close();
                    outputStream.flush();
                    outputStream.close();
                    is.close();
                    String result = new String(outputStream.toByteArray());
                    return parseJsonData(result);
                }else{
                    FileOutputStream outputStream = new FileOutputStream(filePath);
                    int totalLen=connection.getContentLength();
                    int curlen = 0;
                    byte[] buffer = new byte[2 * 1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                        curlen+=len;
                        listener.downloadProgress(curlen,totalLen);

                    }
                    is.close();
                    outputStream.flush();
                    outputStream.close();
                    is.close();
                    return parseJsonData(filePath);
                }

            }else {
                throw  new AppException(connection.getResponseCode(),connection.getResponseMessage());
            }
        } catch (IOException e) {
            throw  new AppException(AppException.ErrorType.SERVERERROR,e.getMessage());
        }

    }


    @Override
    public void downloadProgress(int curlen, int totallen) {

    }

    /**
     * 用于子类解析具体的数据
     * @param result
     * @return
     * @throws Exception
     */
    protected abstract  T parseJsonData(String result) throws AppException;


    public  CallBack setUseCache(String filePath){
        this.filePath=filePath;
        return  this;
    }
     
    
    



}
