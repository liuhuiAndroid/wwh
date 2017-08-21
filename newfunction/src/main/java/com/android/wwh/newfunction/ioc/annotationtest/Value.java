package com.android.wwh.newfunction.ioc.annotationtest;

/**
 * Created by lh on 2017/8/21.
 */

public @interface Value {
    String value();
    String other() default "it's great";
}
