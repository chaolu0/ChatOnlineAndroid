package com.shxy.chatonlineandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by caolu on 2018/5/22.
 */

public class SPHelper {

    private static SharedPreferences preferences = null;
    private static final String PATH = "all_info";

    public static void sava(Context context, String key, String value) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(PATH, Context.MODE_PRIVATE);
        }
        preferences.edit().putString(key, value).apply();
    }

    public static String get(Context context, String key) {
        if (preferences == null) {
            return "";
        } else {
            return preferences.getString(key, "");
        }

    }
}
