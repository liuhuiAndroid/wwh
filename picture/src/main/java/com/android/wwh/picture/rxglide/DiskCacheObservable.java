package com.android.wwh.picture.rxglide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lh on 2017/7/30.
 */

public class DiskCacheObservable extends CacheObservable {

    private DiskLruCache mDiskLruCache;

    private Context mContext;
    //DiskLruCache中对于图片的最大缓存值
    private int maxSize = 20 * 1024 * 1024;

    public DiskCacheObservable(Context context) {
        mContext = context;
        initDiskLruCache();
    }

    @Override
    public Image getDataFromCache(String url) {

        Bitmap bitmap = getDataFromDiskLruCache(url);
        if(bitmap !=null){
            return new Image(url,bitmap);
        }
        return new Image();
    }

    @Override
    public void putDataToCache(final Image image) {
        Observable.create(new ObservableOnSubscribe<Image>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Image> e) throws Exception {
                putDataToDiskLruCache(image);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    /**
     * 缓存文件数据
     * @param image
     */
    private void putDataToDiskLruCache(Image image) {
        try {
            String key = DiskCacheUtils.getMD5String(image.getUrl());
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                boolean isSuccess = downloadUrlToStream(image.getUrl(), outputStream);
                if (isSuccess) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化DiskLruCache
     */
    private void initDiskLruCache() {
        File cacheDir = DiskCacheUtils.getDiskCacheDir(mContext, "our_cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        int versionCode = DiskCacheUtils.getAppVersion(mContext);
        try {
            //这里需要注意参数二：缓存版本号，只要不同版本号，缓存都会被清除，重新使用新的
            mDiskLruCache = DiskLruCache.open(cacheDir, versionCode, 1, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件缓存
     * @param url
     * @return
     */
    private Bitmap getDataFromDiskLruCache(String url){
        Bitmap bitmap = null;
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        try {
            final String key = DiskCacheUtils.getMD5String(url);
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);

            if (snapshot != null) {
                fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }
            if (fileDescriptor != null) {
                bitmap = BitmapFactory.decodeStream(fileInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * 下载文件
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
