package com.android.wwh.network.okhttputil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.wwh.network.model.BaseResult;
import com.android.wwh.network.model.User;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by lh on 2017/8/9.
 */

public class OkHttpUtilTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实现GET请求
        SimpleHttpUtil.newBuilder().url("www.baidu.com").get().build()
                .enqueue(new BaseCallback<User>() {

                    @Override
                    public void onSuccess(User user) {

                    }

                    @Override
                    public void onError(int code) {

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                });

        //实现POST FORM表单
        SimpleHttpUtil.newBuilder().url("www.baidu.com")
                .post()
                .addParam("key", "value")
                .addParam("key2", "value2")
                .build()
                .enqueue(new BaseCallback<BaseResult>(){

                    @Override
                    public void onSuccess(BaseResult baseResult) {

                    }

                    @Override
                    public void onError(int code) {

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                } );

        //实现POST JSON参数
        SimpleHttpUtil.newBuilder().url("www.baidu.com")
                .json()
                .addParam("key", "value")
                .addParam("key2", "value2")
                .build()
                .enqueue(new BaseCallback<BaseResult>(){

                    @Override
                    public void onSuccess(BaseResult baseResult) {

                    }

                    @Override
                    public void onError(int code) {

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                } );

    }

}
