package com.android.wwh.picture.photowallfalls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.android.wwh.picture.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by we-win on 2017/7/13.
 */

public class ImageDetailActivity extends AppCompatActivity {

    @BindView(R.id.zoom_image_view)
    ZoomImageView mZoomImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_details);
        ButterKnife.bind(this);
        String imagePath = getIntent().getStringExtra("image_path");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        mZoomImageView.setImageBitmap(bitmap);
    }

}
