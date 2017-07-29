package com.android.wwh.newfunction.rxjava;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.android.wwh.library.log.Logger;
import com.android.wwh.newfunction.R;
import com.android.wwh.newfunction.rxjava.api.DemoApi;
import com.android.wwh.newfunction.rxjava.entity.BaseResult;
import com.android.wwh.newfunction.rxjava.entity.User;
import com.android.wwh.newfunction.rxjava.entity.UserParam;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by we-win on 2017/7/7.
 */

public class RxJavaStudyActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaStudyActivity";
    @BindView(R.id.btn_code)
    Button mBtnCode;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.edit_search)
    EditText mEditSearch;
    @BindView(R.id.btnThrottleFirst)
    Button mBtnThrottleFirst;

    private DemoApi mDemoApi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_study);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mDemoApi = retrofit.create(DemoApi.class);

        initListener();
        testChangeThread();
    }

    private void testChangeThread() {

        Observable.just(1, 2)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 subscribe() 发生在 main 线程
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        Logger.i("apply testChangeThread:" + Thread.currentThread().getName());
                        return integer.intValue()+" - ";
                    }
                })

                .observeOn(Schedulers.io()) // 指定 subscribe() 发生在 main 线程
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(@NonNull String s) throws Exception {
                        Logger.i("apply3 testChangeThread:" + Thread.currentThread().getName());
                        return 1;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Logger.i("subscribe testChangeThread:" + Thread.currentThread().getName());
                    }
                });
    }

    private void initListener() {
        RxTextView.textChanges(mEditSearch)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                //过滤数据
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        Logger.i("filter = " + charSequence);
                        return charSequence.toString().trim().length() > 0;
                    }
                })
                .switchMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(@NonNull CharSequence charSequence) throws Exception {
                        Logger.i("flatMap = " + charSequence);
                        //search from net
                        List<String> stringList = new ArrayList<String>();
                        stringList.add("abc");
                        stringList.add("ada");
                        return Observable.just(stringList);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        Logger.i("subscribe = " + strings.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });


        RxView.clicks(mBtnThrottleFirst).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        Logger.i("按钮点击：" + System.currentTimeMillis());
                    }
                });

    }

    /**
     * 登录后获取用户信息
     */
    @OnClick(R.id.btn_login)
    public void mBtnLogin() {
        Observable.just(getUserParam())
                .flatMap(new Function<UserParam, ObservableSource<BaseResult>>() {
                    @Override
                    public ObservableSource<BaseResult> apply(@NonNull UserParam userParam) throws Exception {
                        BaseResult baseResult = mDemoApi.login(userParam).execute().body();
                        return Observable.just(baseResult);
                        //方法二
                        //return mDemoApi.login2(userParam);
                    }
                })
                .flatMap(new Function<BaseResult, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(@NonNull BaseResult baseResult) throws Exception {
                        User user = mDemoApi.getUserInfoWithPath(baseResult.getUser_id()).execute().body();
                        return Observable.just(user);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {

                    }
                });
    }


    private UserParam getUserParam() {
        UserParam userParam = new UserParam("name", "password");
        return userParam;
    }

    /**
     * 购物车合并本地和网络数据的案例
     */
    @OnClick(R.id.btn_code)
    public void click() {
        Observable.merge(getDataFromLocal(), getDataFromNetWork())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<String> strings) {
                        for (int i = 0; i < strings.size(); i++) {
                            Log.i(TAG, "onNext string = " + strings.get(i));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete. ");
                    }
                });
    }

    private Observable<List<String>> getDataFromLocal() {
        List<String> list = new ArrayList<>();
        list.add("Local");
        list.add("Local2");
        return Observable.just(list);
    }

    private Observable<List<String>> getDataFromNetWork() {
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
