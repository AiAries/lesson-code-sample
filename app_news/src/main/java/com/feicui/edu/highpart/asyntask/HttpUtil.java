package com.feicui.edu.highpart.asyntask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class HttpUtil {


    private static final String TAG = "HttpUtil";

    @Nullable
    public static String getJsonString(String urlPath) {

        ByteArrayOutputStream baos = null;
        BufferedInputStream bis = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                 bis = new BufferedInputStream(inputStream);
                baos = new ByteArrayOutputStream();

                int i = 0;
                while ((i = bis.read()) !=-1) {
                    baos.write(i);
                }
                Log.d(TAG, "getString: "+baos.toString());
                return baos.toString();
            } else {
                //正常开发，需要分别判断code
                Log.d(TAG, "getString: "+responseCode);
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "getString: 传入的urlPath有问题"+e.toString());
            //url 有问题，请仔细检查
        } catch (IOException e) {
            Log.d(TAG, "getString: 数据读取失败"+e.toString());
        }
        finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    Log.d(TAG, "getString: 关流失败"+e.toString());
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    //关流失败
                    Log.d(TAG, "getString: 关流失败"+e.toString());
                }
            }
        }
        return null;
    }
    @Nullable
    public static Bitmap getBitmap(String urlPath) {

        try {
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            } else {
                //正常开发，需要分别判断code
                Log.d(TAG, "getBitmap: "+responseCode);
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "getBitmap: 传入的urlPath有问题"+e.toString());
            //url 有问题，请仔细检查
        } catch (IOException e) {
            Log.d(TAG, "getBitmap: 数据读取失败"+e.toString());
        }
        return null;
    }

}