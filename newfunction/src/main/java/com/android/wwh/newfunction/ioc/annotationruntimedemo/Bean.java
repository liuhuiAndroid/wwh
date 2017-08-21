package com.android.wwh.newfunction.ioc.annotationruntimedemo;

/**
 * Created by lh on 2017/8/21.
 * 例子来自于：http://blog.csdn.net/duo2005duo/article/details/50505884
 */

@Table(name = "BeanTable")
public class Bean {

    @Column(name = "field")
    int field;

    @Column(name = "description")
    String description;

}