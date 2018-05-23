package com.shxy.chatonlineandroid.mina;

import com.chatonline.server.bean.SendBody;
import com.shxy.chatonlineandroid.bean.ChatMessage;

/**
 * Created by shxy on 2018/5/22.
 */

public interface MsgCallBack {
    void onRecvMsg(ChatMessage body);
}
