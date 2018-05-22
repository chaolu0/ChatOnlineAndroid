package com.chatonline.server.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SendBody implements Serializable {

    private String key;
    private String msg;

    private String data;
    private Map<String, String> map = new HashMap<>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return key + "\n" + msg + "\n" + data + "\n"
                + map.toString();
    }
}
