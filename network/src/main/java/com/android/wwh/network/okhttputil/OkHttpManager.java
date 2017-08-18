package com.android.wwh.network.okhttputil;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by lh on 2017/8/10.
 */

public class OkHttpManager {

    private static OkHttpManager mInstance;

    private OkHttpClient mOkHttpClient;

    private Handler mHandler;

    private Gson mGson;

    private OkHttpManager() {
        initOkHttp();
        mHandler = new Handler();
        mGson = new Gson();
    }

    public static synchronized OkHttpManager getInstance(){
        if(mInstance == null){
            mInstance = new OkHttpManager();
        }
        return mInstance;
    }

    private void initOkHttp(){
        mOkHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(30000, TimeUnit.SECONDS)
                .connectTimeout(30000,TimeUnit.SECONDS)
                .build();
    }

    public void request(final SimpleHttpClient client, final BaseCallback callback){

        if(callback == null){
            throw new NullPointerException("callback is null");
        }

        mOkHttpClient.newCall(client.buildRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendOnFailureMessage(callback,call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String result = response.body().string();
                    if(callback.mType == null || callback.mType == String.class){
                        sendOnSuccessMessage(callback,result);
                    }else{
                        //这里可能需要try..cache...
                        sendOnSuccessMessage(callback,mGson.fromJson(result,callback.mType));
                    }

                    // response是流？用完需要关掉
                    if(response.body()!=null){
                        response.body().close();
                    }
                }else{
                    sendOnErrorMessage(callback,response.code());
                }
            }
        });
    }

    /**
     * 异步转同步???
     */
    private void sendOnFailureMessage(final BaseCallback callback, final Call call, final IOException e){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(call,e);
            }
        });
    }

    private void sendOnErrorMessage(final BaseCallback callback,final int code){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(code);
            }
        });
    }

    private void sendOnSuccessMessage(final BaseCallback callback,final Object object){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(object);
            }
        });
    }
}
