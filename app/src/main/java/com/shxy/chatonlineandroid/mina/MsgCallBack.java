package com.shxy.chatonlineandroid.mina;

import com.chatonline.server.bean.SendBody;

/**
 * Created by shxy on 2018/5/22.
 */

public interface MsgCallBack {
    void onRecvMsg(SendBody body);
}
