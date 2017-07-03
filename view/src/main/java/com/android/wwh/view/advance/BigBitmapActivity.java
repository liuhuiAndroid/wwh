package com.android.wwh.view.advance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.wwh.view.R;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by we-win on 2017/3/27.
 */

public class BigBitmapActivity extends AppCompatActivity {

    @BindView(R.id.large_imageView)
    LargeImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_bitmap);
        ButterKnife.bind(this);

        try {
            InputStream inputStream = getAssets().open("qm.jpg");
            mImageView.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // =============================================================================
        /*  初识 BitmapRegionDecoder
        try
        {
            InputStream inputStream = getAssets().open("tangyan.jpg");

            //获得图片的宽、高
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, tmpOptions);
            int width = tmpOptions.outWidth;
            int height = tmpOptions.outHeight;

            //设置显示图片的中心区域
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100), options);
            mImageView.setImageBitmap(bitmap);


        } catch (IOException e)
        {
            e.printStackTrace();
        }

         */
    }

}
