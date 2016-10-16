package com.example.zt.httpforzha;

import android.os.AsyncTask;

import java.net.HttpURLConnection;

/**
 * 异步任务
 * Created by zt on 16/9/25.
 */
public class HttpRequestTask extends AsyncTask<Void, Integer, Object> {
    public Request request;


    public HttpRequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Object doInBackground(Void... params) {
          return   retry(0);
    }

    /** 重新请求
     * chong
     * @param tryCount
     * @return
     */
    public  Object retry(int tryCount){
        try {
            HttpURLConnection connection = HttpConnectionUtil.execute(request);
            return request.callBack.parse(connection, new DownLoadProgressListener() {
                @Override
                public void downloadProgress(int curlen, int totallen) {
                    publishProgress(curlen,totallen);
                }
            });
        } catch (AppException e) {
            if(e.type== AppException.ErrorType.TIMEOUT){
                if(tryCount < request.maxRetryCount){
                     tryCount++;
                    return  retry(tryCount);
                }
            }
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof AppException) {
            //如果没理异常，让上层调用onFailure()
            if(request.listener!=null){
                if(!(request.listener.handleException((AppException) o))) {
                 request.callBack.OnFailure((AppException) o);
            }else{
                    request.callBack.OnFailure((AppException) o);
                }
            }
        } else {
            request.callBack.onSuccess(o);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        request.callBack.downloadProgress(values[0],values[1]);
    }
}
