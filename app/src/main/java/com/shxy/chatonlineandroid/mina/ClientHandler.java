package com.shxy.chatonlineandroid.mina;

import com.chatonline.server.bean.SendBody;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by shxy on 2018/5/22.
 */

public class ClientHandler extends IoHandlerAdapter{

    private MsgCallBack callBack = null;
    public void setCallBack(MsgCallBack callBack){
        this.callBack = callBack;
    }
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        SendBody body = (SendBody) message;
        callBack.onRecvMsg(body);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("close connect");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("connect with chat server");
    }
}
