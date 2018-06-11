package com.shxy.chatonlineandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.shxy.chatonlineandroid.utils.HttpUtils;
import com.shxy.chatonlineandroid.R;
import com.shxy.chatonlineandroid.bean.JoinRoomResp;
import com.shxy.chatonlineandroid.bean.LoginResp;
import com.shxy.chatonlineandroid.bean.Room;
import com.shxy.chatonlineandroid.utils.SPHelper;

import java.util.ArrayList;
import java.util.List;

public class RoomsActivity extends AppCompatActivity {


    private static final String ROOMS_URL = "/";
    private ListView mListView;
    private List<Room> rooms = new ArrayList<>();
    private LoginResp resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        initData();
        mListView = (ListView) findViewById(R.id.listview);
        RoomsAdapter adapter = new RoomsAdapter(rooms, getApplicationContext());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                RequestParams params = new RequestParams();
                params.put("houseId", rooms.get(i).getHouseId());
                params.put("username", SPHelper.get(getApplicationContext(), "username"));
                params.put("token", SPHelper.get(getApplicationContext(), "token"));

                System.out.println(params.toString());
                HttpUtils.post("/upper/add_room", params, new HttpUtils.Listener() {
                    @Override
                    public void success(byte[] info) {
                        String msg = new String(info);
                        JoinRoomResp resp = new Gson().fromJson(msg, JoinRoomResp.class);
                        if (resp.getState() == 0) {
                            Toast.makeText(getApplicationContext(), resp.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            String remote = resp.getRemote();
                            Intent intent = new Intent(RoomsActivity.this, ChatActivity.class);
                            intent.putExtra("data", resp);
                            intent.putExtra("i", i);
                            SPHelper.sava(getApplicationContext(), "token", resp.getToken());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void failed(byte[] info) {
                        Toast.makeText(RoomsActivity.this, new String(info), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }



    private void initData() {
        Intent intent = getIntent();
        resp = (LoginResp) intent.getSerializableExtra("data");
        rooms.addAll(resp.getRooms());
    }

    private class RoomsAdapter extends BaseAdapter {
        private Context mContext;
        private List<Room> list;
        private LayoutInflater inflater;

        public RoomsAdapter(List<Room> list, Context mContext) {
            this.list = list;
            this.mContext = mContext;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return rooms.size();
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
                view = inflater.inflate(R.layout.roomlist_item, null);
                holder.idTv = view.findViewById(R.id.roomid);
                holder.inTv = view.findViewById(R.id.room_info);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            Room msg = list.get(i);
            holder.idTv.setText("第 "+msg.getHouseId() + " 号房间");
            holder.inTv.setText("人数： "+msg.getNowCount() + "/" + msg.getMaxCount() + "");
            return view;
        }
    }

    private class ViewHolder {
        public TextView idTv;
        public TextView inTv;

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
                startActivity(new Intent(RoomsActivity.this, ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
