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
        String methodName = method.getName();
        Method realMethod = methods.get(methodName);
        return realMethod.invoke(targetObject,objects);
    }

}
