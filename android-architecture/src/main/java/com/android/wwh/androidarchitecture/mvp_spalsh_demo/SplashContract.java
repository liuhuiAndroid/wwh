package com.android.wwh.androidarchitecture.mvp_spalsh_demo;

/**
 * Created by lh on 2017/7/19.
 * 采用契约类来完成Presenter和View接口的展现。这里Presenter主要是完成加载数据
 */

public class SplashContract {

    interface Presenter extends BasePresenter<View> {
        void initData();
    }

    interface View extends BaseView {
        void toMainActivity();
    }

}
