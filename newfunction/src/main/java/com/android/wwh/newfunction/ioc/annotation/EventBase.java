package com.android.wwh.newfunction.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EventBase主要用于给OnClick这类注解上添加注解，毕竟事件很多，并且设置监听器的名称，监听器的类型，调用的方法名都是固定的
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    Class<?> listenerType();

    String listenerSetter();

    String methodName();

}
