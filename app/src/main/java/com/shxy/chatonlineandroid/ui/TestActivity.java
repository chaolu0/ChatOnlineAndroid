package com.shxy.chatonlineandroid.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chatonline.server.bean.SendBody;
import com.shxy.chatonlineandroid.R;
import com.shxy.chatonlineandroid.mina.ClientHandler;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * Created by shxy on 2018/5/23.
 */

public class TestActivity extends AppCompatActivity{

    IoSession session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        new MyAsyncTask().execute();
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendBody body = new SendBody();
                body.setKey("add");
                body.setData("211311");
                System.out.println(session.isConnected());
                session.write(body);
                System.out.println("send finish");
            }
        });
    }

    class MyAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            IoConnector connector = new NioSocketConnector();
            connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new ObjectSerializationCodecFactory())
            );

            connector.setHandler(new ClientHandler());

            ConnectFuture future = connector.connect(new InetSocketAddress("183.175.12.154", 9876));
            future.awaitUninterruptibly();
            session = future.getSession();
            System.out.println("connected");

            return null;
        }

    }
}
