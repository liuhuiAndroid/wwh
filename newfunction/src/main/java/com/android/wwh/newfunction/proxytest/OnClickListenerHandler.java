package com.android.wwh.newfunction.proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by we-win on 2017/7/5.
 */

public class OnClickListenerHandler implements InvocationHandler {

    private Object targetObject;

    public OnClickListenerHandler(Object object) {
        this.targetObject = object;
    }

    private Map<String,Method> methods = new HashMap<String, Method>();

    public void addMethod(String methodName,Method method){
        methods.put(methodName,method);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        // 我们显示的把要执行的方法，通过键值对存到Map里面了
        // 等调用到invoke的时候，其实是通过传入的方法名，得到Map中存储的方法，然后调用我们预设的方法~。
        String methodName = method.getName();
        Method realMethod = methods.get(methodName);
        return realMethod.invoke(targetObject,objects);
    }

}
