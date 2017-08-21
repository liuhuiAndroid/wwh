package com.android.wwh.newfunction.ioc.annotationruntimedemo;

/**
 * Created by lh on 2017/8/21.
 */

public class NameAndType {

    private String type;
    private String name;

    public NameAndType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
