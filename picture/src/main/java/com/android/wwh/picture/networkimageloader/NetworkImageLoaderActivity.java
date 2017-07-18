package com.android.wwh.picture.networkimageloader;

import android.support.v4.app.Fragment;

import com.android.wwh.picture.R;

public class NetworkImageLoaderActivity extends AbsSingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ListImgsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_single_fragment;
    }

}
