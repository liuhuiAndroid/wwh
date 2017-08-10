package com.android.wwh.network.okhttputil;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by lh on 2017/8/9.
 */

public interface BaseCallback<T> {

    void onSuccess(T t);

    void onError(int code);

    void onFailure(Call call, IOException e);

}
