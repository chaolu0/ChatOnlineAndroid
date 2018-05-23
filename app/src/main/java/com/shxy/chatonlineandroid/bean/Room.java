package com.shxy.chatonlineandroid.bean;

import java.io.Serializable;

/**
 * Created by shxy on 2018/5/22.
 */

public class Room implements Serializable{
    private Integer houseId;
    private Integer nowCount;
    private Integer maxCount;

    public Room() {
    }

    public Room(Integer houseId, Integer maxCount, Integer nowCount) {
        this.houseId = houseId;
        this.maxCount = maxCount;
        this.nowCount = nowCount;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
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
