package com.android.wwh.network.retrofit;

import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by lh on 2017/8/18.
 */

public class UserConverterFactory extends Converter.Factory {

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //根据type判断是否是自己能处理的类型，不能的话，return null ,交给后面的Converter.Factory
        return new UserResponseConverter(type);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new UserRequestBodyConverter<>();
    }

    @Nullable
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }

    public class UserResponseConverter<T> implements Converter<ResponseBody, T> {
        private Type type;
        Gson gson = new Gson();

        public UserResponseConverter(Type type) {
            this.type = type;
        }

        @Override
        public T convert(ResponseBody responseBody) throws IOException {
            String result = responseBody.string();
            T users = gson.fromJson(result, type);
            return users;
        }
    }

    public class UserRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private Gson mGson = new Gson();

        @Override
        public RequestBody convert(T value) throws IOException {
            String string = mGson.toJson(value);
            return RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), string);
        }
    }
}
