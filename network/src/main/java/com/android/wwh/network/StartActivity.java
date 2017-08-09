package com.android.wwh.network;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.wwh.network.okhttp.OkhttpMainActivity;

/**
 * Created by we-win on 2017/7/11.
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void okhttp(View view){
        startActivity(new Intent(this, OkhttpMainActivity.class));
    }

    public void volley(View view){

    }


}
