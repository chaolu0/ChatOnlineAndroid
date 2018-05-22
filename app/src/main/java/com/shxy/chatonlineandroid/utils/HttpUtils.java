package com.shxy.chatonlineandroid.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;

/**
 * Created by shxy on 2018/5/9.
 */

public class HttpUtils {
    private static final String BASE_URL = "http://183.175.12.154:8080";
    public interface Listener{
        void success(byte[] info);
        void failed(byte[] info);
    }
    public static void get(String url, RequestParams params, final Listener listener){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.success(responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.failed(responseBody);
            }
        });
    }

    public static void post(String url, RequestParams params, final Listener listener){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(BASE_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.success(responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.failed(responseBody);
            }
        });
    }
}
