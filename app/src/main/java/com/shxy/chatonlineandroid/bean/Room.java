package com.shxy.chatonlineandroid.bean;

/**
 * Created by shxy on 2018/5/22.
 */

public class Room {
    private Integer hoseId;
    private Integer nowCount;
    private Integer maxCount;

    public Room() {
    }

    public Room(Integer hoseId, Integer maxCount, Integer nowCount) {
        this.hoseId = hoseId;
        this.maxCount = maxCount;
        this.nowCount = nowCount;
    }

    public Integer getHoseId() {
        return hoseId;
    }

    public void setHoseId(Integer hoseId) {
        this.hoseId = hoseId;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getNowCount() {
        return nowCount;
    }

    public void setNowCount(Integer nowCount) {
        this.nowCount = nowCount;
    }
}
