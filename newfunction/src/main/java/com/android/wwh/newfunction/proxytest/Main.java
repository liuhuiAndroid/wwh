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

    private void init() throws Exception {
        // 初始化了一个OnClickListenerHandler，把Main的当前实例传入
        OnClickListenerHandler h = new OnClickListenerHandler(this);

        // 拿到Main的click方法，添加到OnClickListenerHandler中的Map中。
        Method method = Main.class.getMethod("click",null);
        h.addMethod("onClick",method);

        // 拿到OnClickListener这个接口的一个代理,这样执行这个接口的所有的方法，都会去调用OnClickListenerHandler的invoke方法。
        Object clickProxy = Proxy.newProxyInstance(OnClickListener.class.getClassLoader(),
                new Class[]{View.OnClickListener.class}, h);

        Method clickMethod = button.getClass().getMethod("setOnClickListener", OnClickListener.class);
        clickMethod.invoke(button,clickProxy);
    }

    public void click(){
        System.out.print("Button clicked！");
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.button.click();
    }

}
