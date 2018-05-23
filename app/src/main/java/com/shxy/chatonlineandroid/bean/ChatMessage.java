package com.shxy.chatonlineandroid.bean;

import java.io.Serializable;

/**
 * Created by shxy on 2018/5/9.
 */

public class ChatMessage implements Serializable{
    private String username;
    private String msg;

    public ChatMessage() {
    }

    public ChatMessage(String msg, String username) {
        this.msg = msg;
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
