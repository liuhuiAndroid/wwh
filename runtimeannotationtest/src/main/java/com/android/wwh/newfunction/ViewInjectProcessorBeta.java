package com.android.wwh.newfunction;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Created by lh on 2017/8/21.
 */

// 当前类支持的注解的完整类路径，支持通配符
@SupportedAnnotationTypes("com.android.wwh.newfunction.ioc")
// 标识该处理器支持的源码版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ViewInjectProcessorBeta extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }

}
