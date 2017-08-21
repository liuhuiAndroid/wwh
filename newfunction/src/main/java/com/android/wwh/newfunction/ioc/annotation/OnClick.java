package com.android.wwh.newfunction.ioc.annotation;

/**
 * Created by we-win on 2017/7/5.
 */

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
// 设置监听器的类型,调用的方法名,监听器的名称
@EventBase(listenerType = View.OnClickListener.class, listenerSetter = "setOnClickListener", methodName = "onClick")
public @interface OnClick {

    int[] value();

}
