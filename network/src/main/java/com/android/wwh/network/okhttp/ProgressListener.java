package com.android.wwh.network.okhttp;

/**
 * Created by lh on 2017/8/9.
 */

public interface ProgressListener {

    void onProgress(int progress);

    void onDone(long totalSize);

}
