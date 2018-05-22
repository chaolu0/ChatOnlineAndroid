package com.shxy.chatonlineandroid.bean;

import java.io.Serializable;

/**
 * Created by shxy on 2018/5/22.
 */

public class JoinRoomResp implements Serializable{
    private String msg;
    private Integer state;
    private String remote;
    private String token;
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public JoinRoomResp() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
