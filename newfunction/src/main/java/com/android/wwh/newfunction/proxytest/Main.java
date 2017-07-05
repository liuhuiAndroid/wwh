package com.android.wwh.newfunction.proxytest;

import android.view.View;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by we-win on 2017/7/5.
 */

public class Main {

    private Button button = new Button();

    public Main() throws Exception {
        init();
    }

    public void click(){
        System.out.print("Button clicked！");
    }

    private void init() throws Exception {
        OnClickListenerHandler h = new OnClickListenerHandler(this);
        Method method = Main.class.getMethod("click",null);
        h.addMethod("onClick",method);
        Object clickProxy = Proxy.newProxyInstance(OnClickListener.class.getClassLoader(),
                new Class[]{View.OnClickListener.class}, h);
        Method clickMethod = button.getClass().getMethod("setOnClickListener", OnClickListener.class);
        clickMethod.invoke(button,clickProxy);
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.button.click();
    }
}
