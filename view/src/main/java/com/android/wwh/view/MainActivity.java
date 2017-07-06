package com.android.wwh.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void acquaintance(View view){
        startActivity(new Intent(MainActivity.this,AcquaintanceActivity.class));
    }

    public void advance(View view){
        startActivity(new Intent(MainActivity.this,AdvanceActivity.class));
    }
}
