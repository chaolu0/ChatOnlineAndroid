package com.shxy.chatonlineandroid.bean;

/**
 * Created by shxy on 2018/5/9.
 */

public class RoomMessage {
    private Integer roomId;
    private Integer hasUser;
    private Integer maxUser;

    public Integer getHasUser() {
        return hasUser;
    }

    public void setHasUser(Integer hasUser) {
        this.hasUser = hasUser;
    }

    public Integer getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(Integer maxUser) {
        this.maxUser = maxUser;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
