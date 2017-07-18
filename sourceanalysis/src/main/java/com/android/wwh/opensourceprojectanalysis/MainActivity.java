package com.android.wwh.opensourceprojectanalysis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.wwh.opensourceprojectanalysis.EventBus.EventBusActivity;
import com.android.wwh.opensourceprojectanalysis.handler.HandlerThreadActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void eventBusOnClick(View view){
        startActivity(new Intent(MainActivity.this,EventBusActivity.class));
    }

    public void handlerThreadOnClick(View view){
        startActivity(new Intent(MainActivity.this,HandlerThreadActivity.class));
    }

}
