package com.shxy.chatonlineandroid.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.shxy.chatonlineandroid.R;
import com.shxy.chatonlineandroid.bean.NormalResp;
import com.shxy.chatonlineandroid.utils.HttpUtils;
import com.shxy.chatonlineandroid.utils.SPHelper;

/**
 * Created by shxy on 2018/5/22.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button cNickName = (Button) findViewById(R.id.change_nick_name);
        Button cPassword = (Button) findViewById(R.id.change_password);

        cNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNickName();
            }
        });

        cPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

    }

    private void changePassword() {

        final View view = getLayoutInflater().inflate(R.layout.double_edit,null);
        new AlertDialog.Builder(this).setTitle("修改密码")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        RequestParams params = new RequestParams();
                        System.out.println("token = " + SPHelper.get(getApplicationContext(),"token"));
                        params.put("username",SPHelper.get(getApplicationContext(),"username"));
                        params.put("token", SPHelper.get(getApplicationContext(),"token"));
                        params.put("new_password",((EditText)view.findViewById(R.id.ed1)).getText().toString());
                        params.put("old_password",((EditText)view.findViewById(R.id.ed1)).getText().toString());
                        HttpUtils.post("/upper/change_password", params, new HttpUtils.Listener() {
                            @Override
                            public void success(byte[] info) {
                                String msg = new String(info);
                                NormalResp resp = new Gson().fromJson(msg,NormalResp.class);
                                Toast.makeText(ProfileActivity.this, resp.getMsg(), Toast.LENGTH_LONG).show();
                                SPHelper.sava(getApplicationContext(),"token",resp.getToken());
                            }

                            @Override
                            public void failed(byte[] info) {
                                Toast.makeText(ProfileActivity.this, new String(info), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消",null).show();
    }

    private void changeNickName() {
        final EditText edt = new EditText(this);
        new AlertDialog.Builder(this).setTitle("修改昵称")
                .setView(edt)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nick = edt.getText().toString();
                        RequestParams params = new RequestParams();
                        System.out.println("token = " + SPHelper.get(getApplicationContext(),"token"));
                        params.put("username",SPHelper.get(getApplicationContext(),"username"));
                        params.put("token", SPHelper.get(getApplicationContext(),"token"));
                        params.put("new_name",nick);
                        HttpUtils.post("/upper/change_name", params, new HttpUtils.Listener() {
                            @Override
                            public void success(byte[] info) {
                                String msg = new String(info);
                                NormalResp resp = new Gson().fromJson(msg,NormalResp.class);
                                Toast.makeText(ProfileActivity.this, resp.getMsg(), Toast.LENGTH_LONG).show();
                                SPHelper.sava(getApplicationContext(),"token",resp.getToken());
                            }

                            @Override
                            public void failed(byte[] info) {
                                Toast.makeText(ProfileActivity.this, new String(info), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消",null).show();
    }
}
