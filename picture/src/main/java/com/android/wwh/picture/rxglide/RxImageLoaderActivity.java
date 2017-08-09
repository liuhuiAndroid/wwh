package com.android.wwh.picture.rxglide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.android.wwh.picture.R;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * Created by lh on 2017/7/29.
 * 有空看  https://github.com/DroidWorkerLYF/RxImageLoader  这个怎么实现的
 *         https://github.com/fengzhizi715/RxJavaInAction
 */

public class RxImageLoaderActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.imageView)
    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_imageloader);
        ButterKnife.bind(this);

        RxView.clicks(mButton).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                RxImageLoader.with(RxImageLoaderActivity.this)
                        .load("http://img.my.csdn.net/uploads/201309/01/1378037235_3453.jpg")
                        .into(mImageView);
            }
        });
    }
}
