package com.android.wwh.newfunction.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by we-win on 2017/7/5.
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Seriable {
}
