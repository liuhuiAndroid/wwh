package com.android.wwh.network.okhttp;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.wwh.library.log.Logger;
import com.android.wwh.network.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
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
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private OkHttpClient mOkHttpClient;
    private RxPermissions mRxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_main);
        ButterKnife.bind(this);

        //创建okHttpClient对象
        //        mOkHttpClient = new OkHttpClient();
        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response.newBuilder().body(new ProgressResponseBody(response.body(), new MyProgressListener())).build();
                    }
                }).build();

        mRxPermissions = new RxPermissions(this);
    }

    class MyProgressListener implements ProgressListener {

        @Override
        public void onProgress(final int progress) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(progress);
                }
            });
        }

        @Override
        public void onDone(long totalSize) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OkhttpMainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
     * 文件下载
     * 使用OkHttp实现下载的进度监听 两种方式
     */
    public void testHttpFileDownload(View view) {
        // 申请权限
        mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            Request request = new Request.Builder()
                                    .url("http://imtt.dd.qq.com/16891/850C513181BCB459AE2B713FA5820E45.apk?fsname=com.jianshu.haruki_2.5.2_2017080714.apk&csr=1bbd")
                                    .build();
                            mOkHttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Logger.i(e.getMessage());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if (response.isSuccessful()) {
                                        //TODO TODO TODO 也可以通过拦截器方式写入内容和文件上传，待研究
                                        writeFile(response);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(OkhttpMainActivity.this, "没有SdCard权限!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 将流写入文件
     *
     * @param response
     */
    private void writeFile(Response response) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        Logger.i("path = " + path);
        File file = new File(path, "test2.apk");
        InputStream is = response.body().byteStream();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            long totalSize = response.body().contentLength();
            long sum = 0;
            while ((len = is.read(bytes)) != -1) {
                sum += len;
                fos.write(bytes, 0, len); // 注意不能用fos.write(bytes)
                // 方法一是常规方法
                //                int progress = (int) ((sum * 1.0f / totalSize) * 100);
                //                Message message = handler.obtainMessage(1);
                //                message.arg1 = progress;
                //                handler.sendMessage(message);
                //方法二是用拦截器模式
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mProgressBar.setProgress(msg.arg1);
            }
        }
    };

    /**
     * Http Post 携带参数
     * 没有对应的API，无法测试真的很尴尬
     */
    public void testHttpPostJson(View view) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("city", CITY);
            jsonObject.put("key", API_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonParams = jsonObject.toString();
        Logger.i("jsonParams:" + jsonParams);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonParams);
        Request request = new Request.Builder()
                .url("https://free-api.heweather.com/v5/now")
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Logger.i("onResponse:");
                    Logger.json(response.body().string());
                }
            }
        });
    }


    /**
     * Http Post 携带参数
     */
    public void testHttpPostForm(View view) {
        // okhttp3.FormBody instead of FormEncodingBuilder.
        // 添加多个String键值对，然后去构造RequestBody，最后完成我们Request的构造。
        FormBody body = new FormBody.Builder()
                .add("city", CITY)
                .add("key", API_KEY)
                .build();
        // RequestBody 的实现类有FormBody和MultipartBody
        Request request = new Request.Builder()
                .url("https://free-api.heweather.com/v5/now")
                .post(body)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Logger.i("onResponse:");
                    Logger.json(response.body().string());
                }
            }
        });
    }

    /**
     * 发送一个get请求
     */
    public void testHttpGet(View view) {

        //创建一个Request
        final Request request = new Request.Builder()
                .get()
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
                if (response.isSuccessful()) {
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
            }
        });

    }

}
