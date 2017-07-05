package com.android.wwh.newfunction.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by we-win on 2017/7/5.
 */
// @Target表示该注解可以用于什么地方，可能的类型TYPE（类）,FIELD（成员变量）
@Target(ElementType.FIELD)
// @Retention表示：表示需要在什么级别保存该注解信息；我们这里设置为运行时。
@Retention(RetentionPolicy.RUNTIME)
// 定义的关键字@interface ;
public @interface ViewInject {
    int value();
}
