package com.android.wwh.network.okhttp;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.wwh.library.log.Logger;
import com.android.wwh.network.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * API接口来自和风天气，感谢：https://www.kancloud.cn/hefengyun/weather/222344
 * API说明文档：https://www.heweather.com/documents/api/v5/scenic
 */

public class OkhttpMainActivity extends AppCompatActivity {


    private static final String API_KEY = "5d5fc651748d45898d50d4f204e7defb";
    private static final String BASE_URL = "https://free-api.heweather.com/v5/";
    private static final String CITY = "CN101020100";//上海

    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_main);

        //创建okHttpClient对象
        mOkHttpClient = new OkHttpClient();

    }

    /**
     * 如何去访问自签名证书的网站:需要让OkhttpClient去信任这个证书
     * 当客户端进行SSL连接时，就可以根据我们设置的证书去决定是否信任服务端的证书
     */
    public void testCertificates(View view) {

        final Request request = new Request.Builder()
                //                .addHeader()
                //                .method()
                .url(" https://kyfw.12306.cn/otn/")
                .build();
        //然后通过request的对象去构造得到一个Call对象,类似于将你的请求封装成了任务
        Call call = mOkHttpClient.newCall(request);
        //call加入调度队列，然后等待任务执行完成.这里是异步的方式去执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.i("onFailure:" + e.getMessage());
                //onFailure: java.security.cert.CertPathValidatorException: Trust anchor for certification path not found.
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 希望获得返回的字符串
                Logger.i("onResponse:" + response.body().string());

            }
        });
    }

    /**
     * 基于Http的文件上传
     * 向服务器传递了一个键值对username:刘晖和一个文件
     */
    public void testHttpFileUpload(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "balabala.mp4");

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        // MultipartBuilder已被升级成MultipartBody,MultipartBody.Part和MultipartBody.Builder
        MultipartBody body = new MultipartBody.Builder("AaB03x")
                .setType(MultipartBody.FORM)
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"username\""),
                        RequestBody.create(null, "刘晖"))
                .addFormDataPart("files", null, new MultipartBody.Builder("BbC04y")
                        .addPart(Headers.of("Content-Disposition", "form-data; filename=\"img.png\""),
                                RequestBody.create(MediaType.parse("image/png"), new File("path")))
                        .build())
                .build();

        // 有时候需要直接把内存中的一张图片上传到服务器，可以采用二进制流的方式。
        //        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        //        MultipartBody body = new MultipartBody.Builder("AaB03x")
        //                .setType(MultipartBody.FORM)
        //                .addFormDataPart("files", null, new MultipartBody.Builder("BbC04y")
        //                        .addPart(Headers.of("Content-Disposition", "form-data; filename=\"img.png\""),
        //                                RequestBody.create(MediaType.parse("image/png"), bos.toByteArray()))
        //                        .build())
        //                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.103:8080/okHttpServer/fileUpload")
                .post(body)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }


    /**
     * Http Post 携带参数
     */
    public void testHttpPost(View view) {
        // okhttp3.FormBody instead of FormEncodingBuilder.
        // 添加多个String键值对，然后去构造RequestBody，最后完成我们Request的构造。
        FormBody body = new FormBody.Builder()
                .add("username", "刘晖")
                .build();
        Request request = new Request.Builder()
                .url("url")
                .post(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 发送一个get请求
     */
    public void testHttpGet(View view) {

        //创建一个Request
        final Request request = new Request.Builder()
                //                .addHeader()
                //                .method()
                .url("https://free-api.heweather.com/v5/now?city=" + CITY + "&key=" + API_KEY) // 实况天气-now
                .build();
        //然后通过request的对象去构造得到一个Call对象,类似于将你的请求封装成了任务
        Call call = mOkHttpClient.newCall(request);
        //call加入调度队列，然后等待任务执行完成.这里是异步的方式去执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.i("onFailure:" + call.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 希望获得返回的字符串
                Logger.i("onResponse:");
                Logger.json(response.body().string());
                // 希望获得返回的二进制字节数组
                //                response.body().bytes();
                // 想拿到返回的inputStream,这里支持大文件下载.有inputStream我们就可以通过IO的方式写文件
                //                response.body().byteStream();
                // 可以通过IO的方式写文件，说明：onResponse执行的线程并不是UI线程

                // 希望操作UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                if (response.body() != null) {
                    response.body().close();
                }
            }
        });

    }

}
