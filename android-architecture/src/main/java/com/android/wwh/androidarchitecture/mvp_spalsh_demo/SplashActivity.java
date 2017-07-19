package com.android.wwh.androidarchitecture.mvp_spalsh_demo;

import android.content.Intent;

import com.android.wwh.androidarchitecture.MainActivity;
import com.android.wwh.androidarchitecture.R;

/**
 * Created by lh on 2017/7/19.
 * 官方示例代码采用的方式是fragment作为View的实现，这里灵活变通，采用Activity作为MVP中View的实现。
 * 这里继承自AbsBaseActivity其中完成了一些初始化操作，将会在另外的文章中进行讲解。
 * 这里我们就采用Dagger2进行了SplashPresenter的实例的获取。也实现了toMainActivity方法，用于跳转到主页面。
 */

public class SplashActivity extends AbsBaseActivity implements SplashContract.View {

    @Inject
    SplashPresenter mPresenter;


    @Override
    public void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void initViewsAndEvents() {
        mPresenter.attachView(this);
        mPresenter.initData();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void injectDagger(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

}
