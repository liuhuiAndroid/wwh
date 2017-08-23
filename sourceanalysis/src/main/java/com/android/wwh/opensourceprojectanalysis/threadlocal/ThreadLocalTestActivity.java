package com.android.wwh.opensourceprojectanalysis.threadlocal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by lh on 2017/8/23.
 * 例子来自于Android开发艺术探索
 */

public class ThreadLocalTestActivity extends AppCompatActivity {

    private static final String TAG = "Test";
    private static ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBooleanThreadLocal.set(true);
        Log.i(TAG, "main : mBooleanThreadLocal = " + mBooleanThreadLocal.get());

        new Thread("Thread#1") {
            @Override
            public void run() {
                mBooleanThreadLocal.set(false);
                Log.i(TAG, "Thread#1 : mBooleanThreadLocal = " + mBooleanThreadLocal.get());
            }
        }.run();

        new Thread("Thread#2") {
            @Override
            public void run() {
                Log.i(TAG, "Thread#2 : mBooleanThreadLocal = " + mBooleanThreadLocal.get());
            }
        }.run();

        //       日志
        //       I/Test: main : mBooleanThreadLocal = true
        //       I/Test: Thread#1 : mBooleanThreadLocal = false
        //       I/Test: Thread#2 : mBooleanThreadLocal = false
    }
}
