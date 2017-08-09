package com.android.wwh.network.model;

import java.io.Serializable;

/**
 * Created by lh on 2017/8/9.
 */

public class User implements Serializable {
    private String username;
    private String url;
    private String head_url;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }
}
