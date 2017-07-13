package com.android.wwh.picture.photowall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.android.wwh.picture.Images;
import com.android.wwh.picture.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Android照片墙应用实现，再多的图片也不怕崩溃
 */

public class PhotoWallActivity extends AppCompatActivity {

    @BindView(R.id.photo_wall)
    GridView mPhotoWall;

    /**
     * GridView的适配器
     */
    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        ButterKnife.bind(this);
        adapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls, mPhotoWall);
        mPhotoWall.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        adapter.cancelAllTasks();
    }

}
