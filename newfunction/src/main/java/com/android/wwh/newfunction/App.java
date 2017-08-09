package com.android.wwh.newfunction;

import android.app.Application;
import android.util.Log;

import com.android.wwh.library.log.CrashlyticsTree;
import com.android.wwh.library.log.Logger;
import com.android.wwh.library.log.Settings;

/**
 * Created by we-win on 2017/7/3.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化日志功能
        Logger.initialize(
                new Settings()
                        .isShowMethodLink(true)
                        .isShowThreadInfo(false)
                        .setMethodOffset(0)
                        .setLogPriority(BuildConfig.DEBUG ? Log.VERBOSE : Log.ASSERT)
        );
        if (!BuildConfig.DEBUG) {
            // for release
            Logger.plant(new CrashlyticsTree());
        }
    }
}
