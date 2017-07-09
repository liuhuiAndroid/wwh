package com.android.wwh.newfunction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wwh.library.log.Logger;
import com.android.wwh.newfunction.ioc.ViewInjectUtils;
import com.android.wwh.newfunction.ioc.annotation.ContentView;
import com.android.wwh.newfunction.ioc.annotation.OnClick;
import com.android.wwh.newfunction.ioc.annotation.ViewInject;
import com.android.wwh.newfunction.rxjava.RxJavaStudyActivity;


@ContentView(value = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.textView)
    private TextView mTextView;
    @ViewInject(R.id.btn1)
    private Button mBtn1;
    @ViewInject(R.id.btn2)
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ViewInjectUtils.inject(this);
        mTextView.setText("oleoleoleole");
    }

    @OnClick({R.id.btn1,R.id.btn2})
    public void clickBtnInvoked(View view){
        Logger.i("clickBtnInvoked");
        switch (view.getId()){
            case R.id.btn1:
                Toast.makeText(this,"Inject Btn1",Toast.LENGTH_SHORT);
                break;
            case R.id.btn2:
                Toast.makeText(this,"Inject Btn2",Toast.LENGTH_SHORT);
                break;
        }
    }

    public void btnRxJavaStudy(View view){
        startActivity(new Intent(MainActivity.this,RxJavaStudyActivity.class));
    }

}
