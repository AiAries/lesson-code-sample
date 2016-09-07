package com.feicui.edu.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewSimpleAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_simple_adapter);
        ListView listView = (ListView) findViewById(R.id.lv);

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("png",i%2==0? R.mipmap.a23:R.mipmap.a20);
//            map.put("txt",i%2==0?"rose is a girl":"jim is a boy");
            map.put("txt",i%2==0?this.getString(R.string.iv_description):"jim is a boy");
            data.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.item_simple_adapter,
                new String[]{"png","txt"},//from,数据的来源，值得是data数据中的key
                new int[]{R.id.imageView,R.id.textView}//to 布局文件要展示数据的id值
        );

        listView.setAdapter(adapter);

    }
}
