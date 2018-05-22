package com.shxy.chatonlineandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shxy on 2018/5/22.
 */

public class LoginResp implements Serializable{
    private String msg;
    private Integer state;
    private String token;
    private String nickname;
    private List<Room> rooms;

    public LoginResp() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
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
