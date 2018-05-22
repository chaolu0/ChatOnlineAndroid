package com.shxy.chatonlineandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chatonline.server.bean.SendBody;
import com.shxy.chatonlineandroid.R;
import com.shxy.chatonlineandroid.bean.ChatMessage;
import com.shxy.chatonlineandroid.bean.JoinRoomResp;
import com.shxy.chatonlineandroid.mina.ClientHandler;
import com.shxy.chatonlineandroid.mina.MsgCallBack;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shxy on 2018/5/9.
 */

public class ChatActivity extends AppCompatActivity implements MsgCallBack{


    private ListView mListView;
    private List<ChatMessage> messageList = new ArrayList<>();
    private static final String IP = "";
    private static final Integer PORT = 9876;
    private IoSession session = null;
    private IoConnector connector = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initMina();
        mListView = (ListView) findViewById(R.id.listview);
        ChatAdapter adapter = new ChatAdapter(messageList, getApplicationContext());
        mListView.setAdapter(adapter);
    }

    private void initMina() {
        Intent intent = getIntent();
        JoinRoomResp resp = (JoinRoomResp) intent.getSerializableExtra("data");

        connector = new NioSocketConnector();
        connector.getFilterChain().addLast(
                "codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        ClientHandler h = new ClientHandler();
        h.setCallBack(this);
        connector.setHandler(h);
        ConnectFuture future = connector.connect(new InetSocketAddress(resp.getRemote(),resp.getPort()));
        future.awaitUninterruptibly();
        session = future.getSession();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connector.dispose();
    }

    @Override
    public void onRecvMsg(SendBody body) {

    }

    private class ChatAdapter extends BaseAdapter {
        private Context mContext;
        private List<ChatMessage> list;
        private LayoutInflater inflater;

        public ChatAdapter(List<ChatMessage> list, Context mContext) {
            this.list = list;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.chatlist_item, null);
                holder.idTv = view.findViewById(R.id.chat_tv);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            ChatMessage msg = list.get(i);
            holder.idTv.setText(msg.getUsername() + ":" + msg.getMsg());
            return view;
        }
    }

    private class ViewHolder {
        public TextView idTv;

    }
}
