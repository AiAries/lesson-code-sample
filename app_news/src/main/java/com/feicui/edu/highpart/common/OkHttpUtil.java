package com.feicui.edu.highpart.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class OkHttpUtil {


    /**
     * 获取字符串数据
     *
     * @param url 数据的url路径
     * @return 获取成功返回字符串内容，失败返回null
     */
    public static String getString(String url) {
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
     *
     * @param url path of picture
     * @return bitmap 类型的图片
     */
    public static Bitmap getBitmap(String url) {
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


    public static final MediaType FILE
            = MediaType.parse("application/octet-stream; charset=utf-8");
    public static String postFile(String url, File file, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("token", token);

        RequestBody body = RequestBody.create(FILE, file);
        builder.addFormDataPart("portrait", file.getName(), body);

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String postString(String url, Map<String,String> map) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            builder.addFormDataPart(key, value);
        }

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
