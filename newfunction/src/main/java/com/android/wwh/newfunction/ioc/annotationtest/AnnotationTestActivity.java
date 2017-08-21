package com.android.wwh.newfunction.ioc.annotationtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

/**
 * Created by lh on 2017/8/21.
 */

public class AnnotationTestActivity extends AppCompatActivity {

    @Inheritable
    public static class Super{

    }

    public static class Sub extends Super{

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Super instance = new Sub();
        System.out.println(Arrays.toString(instance.getClass().getAnnotations()));
    }

}
