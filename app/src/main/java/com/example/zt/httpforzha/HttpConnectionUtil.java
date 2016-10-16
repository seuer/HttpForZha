package com.example.zt.httpforzha;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 对HttpConnectionUrl的请求方式封装
 * Created by zt on 16/9/25.
 */
public class HttpConnectionUtil {

    public static int TIME_OUT=3000;

    public  static  HttpURLConnection execute(Request request ) throws  AppException{
        switch (request.method){
            case GET:
            case DELETE:
                return get(request);
            case POST:
            case PUT:
              return   post(request);

        }
        return  null;

    }



    /**
     * GET方法
     * @param
     * @param request
     * @return
     * @throws IOException
     */
    private   static  HttpURLConnection get(Request request ) throws AppException{
        try {
            checkIfCancelRequest(request);
            URL url =new URL(request.url);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(TIME_OUT);
            connection.setDoOutput(true);

            addHeaders(connection,request.headers );
            checkIfCancelRequest(request);
            return  connection;
        } catch (InterruptedIOException e) {
            throw new AppException(AppException.ErrorType.TIMEOUT,e.getMessage());
        } catch (IOException e){
            throw  new AppException(AppException.ErrorType.SERVERERROR,e.getMessage());
        }
    }

    /**
     *
     * @param request
     * @return
     * @throws IOException
     */
    private   static  HttpURLConnection post(Request request)throws  AppException{
        try {
            checkIfCancelRequest(request);
            URL url =new URL(request.url);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(TIME_OUT);
            connection.setDoOutput(true);

            addHeaders(connection,request.headers);
            //上传 content 内容
            checkIfCancelRequest(request);
            OutputStream os=connection.getOutputStream();
            os.write(request.content.getBytes());
            checkIfCancelRequest(request);

            return  connection;
        } catch (InterruptedIOException e) {
            throw  new  AppException(AppException.ErrorType.SERVERERROR,e.getMessage());
        }catch (IOException e){
            throw new  AppException(AppException.ErrorType.TIMEOUT,e.getMessage());
        }
    }

    /**
     * 添加headers
     * @param connection
     * @param headers
     */
    private static void addHeaders(HttpURLConnection connection, Map<String, String> headers) {
        if(headers==null || headers.size()==0){
            return;
        }
        for (Map.Entry<String,String> entry:headers.entrySet()) {
            connection.addRequestProperty(entry.getKey(),entry.getValue());
        }
    }

    private  static boolean checkIfCancelRequest(Request request) throws  AppException{
        if(request.isCancelled){
            throw  new AppException(AppException.ErrorType.CANCEL,"http request can not be canceled");
        }
        return  false;
    }
}
