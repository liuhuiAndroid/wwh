package com.android.wwh.newfunction.bean;

import com.android.wwh.newfunction.ioc.Seriable;

/**
 * Created by we-win on 2017/7/5.
 */

public class User {

    @Seriable
    private String username;
    @Seriable
    private String password;

    private String three;
    private String four;

}
