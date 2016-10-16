package com.example.zt.httpforzha;

import java.net.HttpURLConnection;

/**
 * 异步任务的回调方法
 * Created by zt on 16/9/25.
 */
public interface CallBack<T> {
    /**
     * 回调成功
     * @param result
     */
    void onSuccess(T result);

    /**
     * 回调失败
     * @param e
     */
    void OnFailure(AppException e);

    T parse(HttpURLConnection connection, DownLoadProgressListener listener) throws AppException;

    /**
     * 下载进度
     * @param curlen 目前的下载的文件长度
     * @param totallen 文件总长度
     */
    void downloadProgress( int curlen, int totallen);

}
