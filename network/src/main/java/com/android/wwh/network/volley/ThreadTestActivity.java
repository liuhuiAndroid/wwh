package com.android.wwh.network.volley;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.wwh.network.R;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by lh on 2017/8/31.
 */

public class ThreadTestActivity extends AppCompatActivity {


    private static final String TAG = "ThreadTestActivity";
    BlockingQueue<String> mCacheQueue = new ArrayBlockingQueue<String>(10);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thread_test);
        final CacheDispatcher cacheDispatcher = new CacheDispatcher();
        cacheDispatcher.start();


        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "║ 点击了按钮");
                mCacheQueue.add("║ 当前时间 ：" + System.currentTimeMillis());
            }
        });

        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cacheDispatcher.quit();
            }
        });

    }


    class CacheDispatcher extends Thread {

        private volatile boolean mQuit = false;

        public void quit() {
            mQuit = true;
            interrupt();
        }

        @Override
        public void run() {
            super.run();

            while (true) {
                try {
                    Log.i(TAG, "║ 阻塞前");
                    String take = mCacheQueue.take();
                    Log.i(TAG, "║ take ：" + take);
                } catch (InterruptedException e) {
                    Log.i(TAG, "║ e ：" + e.getMessage());
                    if (mQuit) {
                        return;
                    }
                    continue;
                }
            }

        }
    }
}
