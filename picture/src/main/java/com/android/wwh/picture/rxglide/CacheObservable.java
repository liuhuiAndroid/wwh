package com.android.wwh.picture.rxglide;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lh on 2017/7/30.
 */

public abstract class CacheObservable {

    public Observable<Image> getImage(final String url) {
        return Observable.create(new ObservableOnSubscribe<Image>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Image> e) throws Exception {
                if (!e.isDisposed()) {
                    Image image = getDataFromCache(url);
                    e.onNext(image);
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 从缓存里面拿数据
     */
    public abstract Image getDataFromCache(String url);

    /**
     * 把数据放到缓存里
     */
    public abstract void putDataToCache(Image image);

}
