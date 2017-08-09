package com.android.wwh.picture.rxglide;

import android.content.Context;
import android.widget.ImageView;

import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * Created by lh on 2017/7/30.
 */

public class RxImageLoader {

    private RequestCreator mRequestCreator;

    private RxImageLoader(Builder builder) {
        mRequestCreator = new RequestCreator(builder.mContext);
    }

    static volatile RxImageLoader rxImageLoader = null;

    public static RxImageLoader with(Context context) {
        if (rxImageLoader == null) {
            synchronized (RxImageLoader.class) {
                if (rxImageLoader == null) {
                    rxImageLoader = new Builder(context).build();
                }
            }
        }
        return rxImageLoader;
    }

    private String mUrl;

    public RxImageLoader load(String url) {
        this.mUrl = url;
        return rxImageLoader;
    }

    /**
     * 显示图片
     *
     * @param imageView
     */
    public void into(final ImageView imageView) {
        Observable.concat(mRequestCreator.getImageFromMemory(mUrl),
                mRequestCreator.getImageFromDisk(mUrl),
                mRequestCreator.getImageFromNet(mUrl))
                .filter(new Predicate<Image>() {
                    @Override
                    public boolean test(@NonNull Image image) throws Exception {
                        return image.getBitmap()!=null;
                    }
                })
                .firstElement()
                .subscribe(new MaybeObserver<Image>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull Image image) {
                        imageView.setImageBitmap(image.getBitmap());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 建造者模式
     */
    public static class Builder {

        private Context mContext;

        public Builder(Context context) {
            this.mContext = context;
        }

        public RxImageLoader build() {
            return new RxImageLoader(this);
        }
    }

}
