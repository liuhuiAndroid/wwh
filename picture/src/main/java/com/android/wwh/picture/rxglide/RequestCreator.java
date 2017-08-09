package com.android.wwh.picture.rxglide;

import android.content.Context;

import com.android.wwh.library.log.Logger;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by lh on 2017/7/30.
 */

public class RequestCreator {

    private MemoryCacheObservable mMemoryCacheObservable;
    private DiskCacheObservable mDiskCacheObservable;
    private NetWorkCacheObservable mNetWorkCacheObservable;

    public RequestCreator(Context context) {
        mMemoryCacheObservable = new MemoryCacheObservable();
        mDiskCacheObservable = new DiskCacheObservable(context);
        mNetWorkCacheObservable = new NetWorkCacheObservable();
    }

    public Observable<Image> getImageFromMemory(String url) {
        return mMemoryCacheObservable.getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(@NonNull Image image) throws Exception {
                        return image.getBitmap()!= null;
                    }
                })
                .doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(@NonNull Image image) throws Exception {
                        Logger.i("get data from memory");
                    }
                });
    }

    public Observable<Image> getImageFromDisk(String url) {
        return mDiskCacheObservable.getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(@NonNull Image image) throws Exception {
                        return image.getBitmap()!= null;
                    }
                })
                .doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(@NonNull Image image) throws Exception {
                        Logger.i("get data from disk");
                        //缓存到内存
                        mMemoryCacheObservable.putDataToCache(image);
                    }
                });
    }

    public Observable<Image> getImageFromNet(String url) {
        return mNetWorkCacheObservable.getImage(url)
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(@NonNull Image image) throws Exception {
                        return image.getBitmap()!= null;
                    }
                })
                .doOnNext(new Consumer<Image>() {
                    @Override
                    public void accept(@NonNull Image image) throws Exception {
                        Logger.i("get data from network");
                        //缓存到disk
                        mDiskCacheObservable.putDataToCache(image);
                        //缓存到内存
                        mMemoryCacheObservable.putDataToCache(image);
                    }
                });
    }
}
