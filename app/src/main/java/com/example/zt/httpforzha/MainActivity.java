package com.example.zt.httpforzha;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends BaseActivity {

    private Button btnHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHttp = (Button) findViewById(R.id.test_http);
        btnHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downLoadForProgress();
                //testTastForGeneric();

            }
        });

    }

   /* private void testTask() {
        String url = "http://api.stay4it.com/v1/public/core/?service=user.login";
        String content = "account=stay4it&password=123456";
        Request request=new Request(url, Request.RequestMethod.POST);
        request.content=content;
        HttpRequestTask task =new HttpRequestTask(request);
        request.setCallBack(new Callback<User>() {
            @Override
            public void onSuccess(String result) {
                Log.e("stay", "testHttpGet return:" + result.toString());
            }

            @Override
            public void OnFailure(Exception e) {
                e.printStackTrace();

            }

            @Override
            public User parse(HttpURLConnection connection) throws IOException {
                return null;
            }
        });
        task.execute();

    }*/

    private void testTastForGeneric() {
        // String url = "http://api.stay4it.com/v1/public/core/?service=user.login";
        // String content = "account=stay4it&password=123456";
        String url = "http://121.42.178.236:5090/system/test/toJson";
        Request request = new Request(url, Request.RequestMethod.GET);

        request.setCallBack(new JsonCallback<User>() {
            @Override
            public void onSuccess(User result) {
                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
                Log.e("zha", "testHttpGet return:" + result.toString());
            }

            @Override
            public void OnFailure(AppException e) {

            }


        });
        //request.content=content;
        HttpRequestTask task = new HttpRequestTask(request);
        task.execute();

    }

    private void downLoad() {

        String url = "http://121.42.178.236:5090/system/test/toJson";
        Request request = new Request(url, Request.RequestMethod.GET);
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "zhagege.txt";
        request.setCallBack(new JsonCallback<String>() {


            @Override
            public void onSuccess(String path) {
                Toast.makeText(MainActivity.this, path, Toast.LENGTH_LONG).show();
                // Log.e("---zha--",path);

            }

            @Override
            public void OnFailure(AppException e) {
                e.printStackTrace();

            }


        }.setUseCache(filePath));
        HttpRequestTask task = new HttpRequestTask(request);
        task.execute();

    }

    private void downLoadForProgress() {

        String url = "http://api.stay4it.com/uploads/test.jpg";
        final Request request = new Request(url, Request.RequestMethod.GET);
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "shishi.jpg";
        HttpRequestTask task = new HttpRequestTask(request);
        task.execute();
        request.setCallBack(new FileCallBack() {
            @Override
            public void downloadProgress(int curlen, int totallen) {
                Log.e("--zha-", curlen + File.separator + totallen);
                if (curlen * 100l / totallen > 50) {
                    request.cancel();
                }

            }

            @Override
            public void onSuccess(String path) {
                if (path != null) {
                    Toast.makeText(MainActivity.this, path, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_LONG).show();
                }
                // Log.e("---zha--",path);

            }

            @Override
            public void OnFailure(AppException e) {
                if (e.statusCode == 403 || e.statusCode == 405 || e.statusCode == 0) {
                    Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_LONG).show();
                }

            }


        }.setUseCache(filePath));
        request.setonGlobalExceptionListener(this);
        task.cancel(true);
        request.cancel();

    }


}
