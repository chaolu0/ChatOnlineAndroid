package com.shxy.chatonlineandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chatonline.server.bean.SendBody;
import com.shxy.chatonlineandroid.R;
import com.shxy.chatonlineandroid.bean.ChatMessage;
import com.shxy.chatonlineandroid.bean.JoinRoomResp;
import com.shxy.chatonlineandroid.mina.ClientHandler;
import com.shxy.chatonlineandroid.mina.MsgCallBack;
import com.shxy.chatonlineandroid.utils.SPHelper;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
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
    private ChatAdapter adapter;
    private EditText msgEdit;
    private Button sendButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        new MyAsyncTask().execute();
        mListView = (ListView) findViewById(R.id.listview);
        adapter = new ChatAdapter(messageList, getApplicationContext());
        mListView.setAdapter(adapter);
        msgEdit = (EditText) findViewById(R.id.edit);
        sendButton = (Button) findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session == null)
                    return;
                SendBody body = new SendBody();
                String tempMessage = SPHelper.get(getApplicationContext(),"nickname") + ":"+msgEdit.getText().toString();
                body.setKey("forward");
                body.setData(tempMessage);
                WriteFuture future = session.write(body);
                future.awaitUninterruptibly();
                messageList.add(new ChatMessage(tempMessage,SPHelper.get(getApplication(),"nickname")));
                adapter.notifyDataSetChanged();
                msgEdit.setText("");
            }
        });
    }
    class MyAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            initMina();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
        }
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
        System.out.println("connected");
        int i = intent.getIntExtra("i",0);
        SendBody body = new SendBody();
        body.setKey("add");
        body.setData(i+"");
        System.out.println(session.isConnected());
        session.write(body);
        System.out.println("send finish");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connector.dispose();
    }

    @Override
    public void onRecvMsg(ChatMessage body) {
        messageList.add(body);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

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
            holder.idTv.setText(msg.getMsg());
            return view;
        }
    }

    private class ViewHolder {
        public TextView idTv;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.normal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt1:
                startActivity(new Intent(ChatActivity.this, ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
