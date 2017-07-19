package com.android.wwh.androidarchitecture.mvp_spalsh_demo;

/**
 * Created by lh on 2017/7/19.
 */

public interface BaseView {

    void showMessage(String msg);

    void close();

    /**
     * loading
     */
    void showProgress(String msg);

    void showProgress(String msg, int progress);

    void hideProgress();

    /**
     * 加载错误
     */
    void showErrorMessage(String msg,String content);
}

