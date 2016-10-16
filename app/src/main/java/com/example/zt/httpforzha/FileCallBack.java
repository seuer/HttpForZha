package com.example.zt.httpforzha;

/**
 * Created by zt on 16/10/8.
 */
public abstract  class FileCallBack extends AbstractCallBack<String>  {
    /**
     *
     * @param path
     * @return  返回文件路径
     * @throws Exception
     */
    @Override
    protected String parseJsonData(String path) throws AppException {
        return path;
    }

    @Override
    protected void checkIfCanceled() {

    }
}
