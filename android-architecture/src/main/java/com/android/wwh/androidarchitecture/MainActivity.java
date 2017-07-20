package com.android.wwh.androidarchitecture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.wwh.androidarchitecture.mvp_spalsh_demo.MvpActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void android_network_imageloader(View view) {
        startActivity(new Intent(MainActivity.this, MvpActivity.class));
    }

}
