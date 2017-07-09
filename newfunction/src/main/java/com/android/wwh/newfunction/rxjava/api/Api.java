package com.android.wwh.newfunction.rxjava.api;

import com.android.wwh.newfunction.rxjava.entity.LoginRequest;
import com.android.wwh.newfunction.rxjava.entity.LoginResponse;
import com.android.wwh.newfunction.rxjava.entity.RegisterRequest;
import com.android.wwh.newfunction.rxjava.entity.RegisterResponse;
import com.android.wwh.newfunction.rxjava.entity.UserBaseInfoRequest;
import com.android.wwh.newfunction.rxjava.entity.UserBaseInfoResponse;
import com.android.wwh.newfunction.rxjava.entity.UserExtraInfoRequest;
import com.android.wwh.newfunction.rxjava.entity.UserExtraInfoResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/12/6
 * Time: 11:30
 * FIXME
 */
public interface Api {
    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @GET
    Observable<UserBaseInfoResponse> getUserBaseInfo(@Body UserBaseInfoRequest request);

    @GET
    Observable<UserExtraInfoResponse> getUserExtraInfo(@Body UserExtraInfoRequest request);

    @GET("v2/movie/top250")
    Observable<Response<ResponseBody>> getTop250(@Query("start") int start, @Query("count") int count);
}
