package com.android.wwh.newfunction.rxjava;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.android.wwh.newfunction.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by we-win on 2017/7/7.
 */

public class RxJavaStudyActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaStudyActivity";
    @BindView(R.id.btn_code)
    Button mBtnCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_study);
        ButterKnife.bind(this);

    }

    /**
     * 购物车合并本地和网络数据的案例
     */
    @OnClick(R.id.btn_code)
    public void click(){
        Observable.merge(getDataFromLocal(),getDataFromNetWork())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onNext(@NonNull List<String> strings) {
                        for (int i = 0; i < strings.size(); i++) {
                            Log.i(TAG,"onNext string = "+strings.get(i));
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onComplete() {
                        Log.i(TAG,"onComplete. ");
                    }
                });
    }

    private Observable<List<String>> getDataFromLocal(){
        List<String> list = new ArrayList<>();
        list.add("Local");
        list.add("Local2");
        return Observable.just(list);
    }

    private Observable<List<String>> getDataFromNetWork(){
        List<String> list = new ArrayList<>();
        list.add("NetWork");
        list.add("NetWork2");
        return Observable.just(list).subscribeOn(Schedulers.io());
    }

    /**
     * 发送验证码倒计时案例
     */
    public void mBtnCode() {
        int time = 10;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(time + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return 10 - aLong;
                    }
                })
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i(TAG, " accept : " + disposable.isDisposed());
                        mBtnCode.setEnabled(false);
                        mBtnCode.setTextColor(Color.GRAY);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mBtnCode.setText("剩余" + aLong + "秒");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        mBtnCode.setEnabled(true);
                        mBtnCode.setText("发送验证码");
                        mBtnCode.setTextColor(Color.BLACK);
                    }
                });
    }

}
