package com.android.wwh.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.wwh.view.advance.BigBitmapActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by we-win on 2017/3/27.
 */

public class AdvanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_big_bitmap)
    public void mBtnBigBitmap(){
        Intent intent = new Intent(this,BigBitmapActivity.class);
        startActivity(intent);
    }

}
