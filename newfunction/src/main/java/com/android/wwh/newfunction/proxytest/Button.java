package com.android.wwh.newfunction.proxytest;

/**
 * Created by we-win on 2017/7/5.
 */

public class Button {

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void click(){

        if(listener!=null){
            listener.onClick();
        }
    }

}
