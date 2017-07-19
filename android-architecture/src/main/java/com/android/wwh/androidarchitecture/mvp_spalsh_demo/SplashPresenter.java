package com.android.wwh.androidarchitecture.mvp_spalsh_demo;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by lh on 2017/7/19.
 */

public class SplashPresenter implements SplashContract.Presenter {

    private static final short SPLASH_SHOW_SECONDS = 1;
    private long mShowMainTime;

    private SplashContract.View mView;
    private Context mContext;
    private Subscription mSubscription;

    @Inject
    ToastUtils mToastUtil;

    @Inject
    DataManager mDataManager;

    private MyApplication mApplication;

    @Inject
    public SplashPresenter(@ActivityContext Context context, MyApplication application) {
        mContext = context;
        this.mApplication = application;
    }


    @Override
    public void initData() {
        mShowMainTime = System.currentTimeMillis() + SPLASH_SHOW_SECONDS * 2000;


        //load channel list data ,then save to database
        mSubscription = mDataManager.loadChannelList(Constants.FIRST_MENU_URL)
                .doOnNext(mDataManager.saveChannelListToDb)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<List<Channel>>() {
                    @Override
                    public void onNext(List<Channel> channels) {
                        showView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * menu url to load channels
     *
     * @return
     */
    //    private String getFirstMenuUrl() {
    //        return "raw://news_menu";  //local data fot testing
    //    }

    private void showView() {
        AsyncTask<String, String, String> showMainTask = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String[] params) {
                if (System.currentTimeMillis() < mShowMainTime) {
                    try {
                        long sleepTime = mShowMainTime - System.currentTimeMillis();
                        if (sleepTime > 0) {
                            Thread.sleep(mShowMainTime - System.currentTimeMillis());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(String o) {
                mView.toMainActivity();
                mView.close();
            }
        };

        showMainTask.execute();
    }


    /**
     * 在attackView中完成了view的实例获取mView
      * @param view
     */
    @Override
    public void attachView(SplashContract.View view) {
        mView = view;
    }

    /**
     * 在需要解除绑定的时候使用detachView方法
     */
    @Override
    public void detachView() {
        mView = null;
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


}
