package com.feicui.edu.highpart.util;

import android.util.Log;

import com.feicui.edu.highpart.bean.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class GsonParseUtil {
    private static final String TAG = "GsonParseUtil";

    /*
          {
                "summary": "4月18日，海蝶音乐创始人许环良正式宣布自己辞去CEO的职务，离开了亲手创办的唱片公司，就在国内资本蠢蠢欲动、外资巨鳄觊觎内地市场的时节，唱片公司老板转身的原因是什么？",
                "nid": 6,
                "icon": "http://localhost:8080/Images/20140509075810c679d.png",
                "link": "http://tech.163.com/14/0509/06/9RPKCLTM00094ODU.html",
                "stamp": "2016-08-29 11:43:16.0",
                "title": "音乐生态乱象 是互联网的错么？",
                "type": 1
            }
         */
    public static ArrayList<News> parseNewJsonString(String jsonString) {
        ArrayList<News> data = null;
        try {
            data = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject o = (JSONObject) jsonArray.get(i);
                Type type = new TypeToken<News>(){}.getType();
                News news = gson.fromJson(o.toString(),type/* News.class*/);
                data.add(news);
            }
            return data;
        } catch (JSONException e) {
            Log.e(TAG, "JsonString: "+e.toString());
        }
       return null;
    }

}
