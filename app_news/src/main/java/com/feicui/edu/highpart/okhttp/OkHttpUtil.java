package com.feicui.edu.highpart.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class OkHttpUtil {


    /**
     * 获取字符串数据
     * @param url 数据的url路径
     * @return  获取成功返回字符串内容，失败返回null
     */
    public static String getJsonString(String url)
    {
        ResponseBody responseBody = getResponseBody(url);
        try {
            assert responseBody != null;
            return responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过url路径获取图片
     * @param url path of picture
     * @return bitmap 类型的图片
     */
    public static Bitmap getBitmap(String url)
    {
        ResponseBody responseBody = getResponseBody(url);
        assert responseBody != null;
        return BitmapFactory.decodeStream(responseBody.byteStream());
    }
    
    private static ResponseBody getResponseBody(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
