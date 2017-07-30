package com.android.wwh.picture.rxglide;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by lh on 2017/7/30.
 */

public class MemoryCacheObservable extends CacheObservable {

    int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    int cacheSize = maxMemory / 8;
    LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        }
    };

    @Override
    public Image getDataFromCache(String url) {
        Bitmap bitmap =  mLruCache.get(url);
        if(bitmap != null){
            return new Image(url,bitmap);
        }
        return new Image();
    }

    @Override
    public void putDataToCache(Image image) {
        mLruCache.put(image.getUrl(),image.getBitmap());
    }
}