package com.android.wwh.network.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by lh on 2017/8/18.
 */

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testProxy();
        testConverterFactory();
    }

    /**
     * 可以完成我们的ReponseBody到List<User>或者User的转化了。
     */
    private void testConverterFactory() {
        Retrofit retrofit = new Retrofit.Builder()
                .callFactory(new OkHttpClient())
                .baseUrl("http://example/springmvc_users/user/")
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new UserConverterFactory())
                .build();
    }

    private void testProxy() {
        ITest iTest = (ITest) Proxy.newProxyInstance(ITest.class.getClassLoader(), new Class<?>[]{ITest.class}, new InvocationHandler()
        {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
            {
                Integer a = (Integer) args[0];
                Integer b = (Integer) args[1];
                System.out.println("方法名：" + method.getName());
                System.out.println("参数：" + a + " , " + b);

                GET get = method.getAnnotation(GET.class);
                System.out.println("注解：" + get.value());
                return null;
            }
        });
        iTest.add(3, 5);
    }
}
