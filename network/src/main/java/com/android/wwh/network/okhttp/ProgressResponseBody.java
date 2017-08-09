package com.android.wwh.network.okhttp;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by lh on 2017/8/9.
 * 自定义ResponseBody
 * 需要看一下Interceptor的内容
 */

public class ProgressResponseBody extends ResponseBody {

    private ResponseBody mResponseBody;

    private BufferedSource mBufferedSource;

    private ProgressListener mListener;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        mResponseBody = responseBody;
        mListener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if(mBufferedSource == null){
            mBufferedSource = Okio.buffer(getSource(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source getSource(Source source) {
        return new ForwardingSource(source) {

            long totalSize = 0;
            long sum = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                if (totalSize == 0) {
                    totalSize = contentLength();
                }
                long len = super.read(sink, byteCount);
                sum += (len == -1 ? 0 : len);
                int progress = (int) ((sum * 1.0f / totalSize) * 100);

                if (len == -1) {
                    mListener.onDone(totalSize);
                } else {
                    mListener.onProgress(progress);
                }
                return len;
            }
        };
    }

}
