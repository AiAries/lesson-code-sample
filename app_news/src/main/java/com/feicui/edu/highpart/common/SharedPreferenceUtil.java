package com.feicui.edu.highpart.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class SharedPreferenceUtil {

    public static void saveToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        preferences.edit().putString("token", token).apply();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        return preferences.getString("token","");
    }

}
