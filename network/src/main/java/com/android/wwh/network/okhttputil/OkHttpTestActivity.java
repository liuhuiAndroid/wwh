package com.android.wwh.network.okhttputil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.wwh.network.model.BaseResult;

/**
 * Created by lh on 2017/8/10.
 */

public class OkHttpTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void testGet(){
        String url = "";
        SimpleHttpClient.newBuilder()
                .addParam("username","")
                .addParam("password","")
                .post()
                .url(url)
                .build()
                .enqueue(new BaseCallback<BaseResult>(){
                    @Override
                    void onSuccess(BaseResult baseResult) {
                        Toast.makeText(OkHttpTestActivity.this,baseResult.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
