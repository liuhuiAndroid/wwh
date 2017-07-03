package com.android.wwh.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.wwh.view.acquaintance.customattr.CustomAttrActivity;
import com.android.wwh.view.acquaintance.first.FirstActivity;
import com.android.wwh.view.acquaintance.firstviewgroup.FirstViewGroupActivity;
import com.android.wwh.view.acquaintance.fourth.FourthActivity;
import com.android.wwh.view.acquaintance.second.SecondActivity;
import com.android.wwh.view.acquaintance.third.ThirdActivity;
import com.android.wwh.view.acquaintance.viewdraghelper.ViewDragHelperActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by we-win on 2017/3/24.
 *
 */

public class AcquaintanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acquaintance);
        ButterKnife.bind(this);
    }

    // ============  初识自定义View  ===============

    @OnClick(R.id.btn_first)
    public void mBtnFirst(){
        Intent intent = new Intent(this,FirstActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_second)
    public void mBtnSecond(){
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_third)
    public void mBtnThird(){
        Intent intent = new Intent(this,ThirdActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_four)
    public void mBtnFour(){
        Intent intent = new Intent(this,FourthActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_first_view_group)
    public void mBtnFirstViewGroup() {
        Intent intent = new Intent(this, FirstViewGroupActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_custom_attr)
    public void mBtnCustomAttr() {
        Intent intent = new Intent(this, CustomAttrActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_view_drag_helper)
    public void mBtnViewDragHelper() {
        Intent intent = new Intent(this, ViewDragHelperActivity.class);
        startActivity(intent);
    }

}
