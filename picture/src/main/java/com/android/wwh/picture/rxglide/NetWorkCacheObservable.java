package com.android.wwh.picture.rxglide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lh on 2017/7/30.
 */

public class NetWorkCacheObservable extends CacheObservable {

    //当做是从网络获取
    @Override
    public Image getDataFromCache(String url) {
        Bitmap bitmap = downloadImage(url);
        if(bitmap !=null){
            return new Image(url,bitmap);
        }
        return new Image();
    }

    // 不需要实现
    @Override
    public void putDataToCache(Image image) {

    }

    /**
     * 简单获取图片
     * @param url
     * @return
     */
    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            URLConnection con = new URL(url).openConnection();
            inputStream = con.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }
}