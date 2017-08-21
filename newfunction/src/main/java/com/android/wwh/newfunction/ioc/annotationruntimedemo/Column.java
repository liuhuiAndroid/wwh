package com.android.wwh.newfunction.ioc.annotationruntimedemo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lh on 2017/8/21.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name();
}
