package com.android.wwh.newfunction.rxjava.api;

import com.android.wwh.newfunction.rxjava.entity.BaseResult;
import com.android.wwh.newfunction.rxjava.entity.User;
import com.android.wwh.newfunction.rxjava.entity.UserParam;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lh on 2017/7/29.
 */

public interface DemoApi {

    @GET("user/{id}")
    Call<User> getUserInfoWithPath(@Path("id") int user_id);

    @POST("login/json")
    Call<BaseResult> login(@Body UserParam userParam);

    @POST("login/json")
    Observable<BaseResult> login2(@Body UserParam userParam);

}
