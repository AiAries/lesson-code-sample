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

    public static void saveAccount(Context context, String username,String pwd) {
        SharedPreferences preferences = context.getSharedPreferences("Account", Context.MODE_PRIVATE);
        preferences.edit().putString("username", username).putString("pwd",pwd).apply();
    }
    /**
     *
     * @param context 上下文
     * @return string类型的数组，有两个值，index:0-username，index:1-pwd
     */
    public static String[] getAccount(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Account", Context.MODE_PRIVATE);
        String[] account = new String[2];
        account[0] = preferences.getString("username", null);
        account[1] = preferences.getString("pwd", null);
        return account;
    }

}
