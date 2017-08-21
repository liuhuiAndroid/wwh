package com.android.wwh.newfunction.ioc.annotationsourcedemo;

import com.google.auto.service.AutoService;

import lombok.javac.apt.Processor;

/**
 * Created by lh on 2017/8/21.
 * 生成注解处理器
 * android项目访问不了AbstractProcessor，可以依赖下module：https://stackoverflow.com/questions/36024618/unable-to-extend-abstractprocessor-to-create-java-annotation-processor
 */

@AutoService(Processor.class)
public class ProcessorA {}

//        extends AbstractProcessor {
//
//    //这个方法主要是获取工具类
//    public synchronized void init(ProcessingEnvironment env){ }
//
//
//    //这个方法里面写处理的过程，输入参数annotations是getSupportedAnnotationTypes()的子集，即是工程代码中所有注解集合与getSupportedAnnotationTypes()的交集，RoundEnvironment env代表这一轮扫描后的结果,返回true则表示消费完此次扫描，此轮扫描注解结束
//    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) { }
//
//
//    //返回需要处理的注解的类的getCanonicalName()集合
//    public Set<String> getSupportedAnnotationTypes() { }
//
//
//    //返回SourceVersion.latestSupported()即可
//    public SourceVersion getSupportedSourceVersion() { }
//
//}
