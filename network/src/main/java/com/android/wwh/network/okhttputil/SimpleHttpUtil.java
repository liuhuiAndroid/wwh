package com.android.wwh.network.okhttputil;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by lh on 2017/8/9.
 * 封装GET、POST FORM、POST JSON、自动转换JSON为JavaBean
 */

public class SimpleHttpUtil {

    private SimpleHttpUtil() {
    }

    public void enqueue(BaseCallback callback){

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private String url;
        private String method;
        private boolean isJsonParam; // 默认不写是false
        private List<RequestParam> mParams;

        private Builder() {
            method = "GET";
        }

        public SimpleHttpUtil build() {
            return new SimpleHttpUtil();
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder get(){
            method = "GET";
            return this;
        }

        /**
         * Form 表单
         * @return
         */
        public Builder post(){
            method = "POST";
            return this;
        }

        /**
         * JSON 参数
         * @return
         */
        public Builder json(){
            isJsonParam = true;
            return post();
        }

        public Builder addParam(String key,Object value){
            if(mParams == null){
                //PASS: android中ArrayList效率不高???
                mParams = new ArrayList<>();
            }
            mParams.add(new RequestParam(key,value));
            return this;
        }
    }

}
