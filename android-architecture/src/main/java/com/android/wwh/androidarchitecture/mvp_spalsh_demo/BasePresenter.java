package com.android.wwh.androidarchitecture.mvp_spalsh_demo;

/**
 * Created by lh on 2017/7/19.
 * 在BasePresenter中获取View的实例，也就是通过下面的attachView方法完成了View和Presenter的关联性。
 */

public interface BasePresenter<T extends BaseView> {

    /**
     * 这个方法会在相应的Presenter的实现类里面得到实现。
     * 而我们需要通过在相应的View类里面通过mPresenter.attachView(this)进行初始化关联，
     * 这样就可以在Presenter的实例中使用View的实例。
     * @param view
     */
    void attachView(T view );

    void detachView();

}
