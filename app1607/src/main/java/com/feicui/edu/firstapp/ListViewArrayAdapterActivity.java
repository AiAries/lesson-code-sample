package com.feicui.edu.firstapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewArrayAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);
        List<String> data = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            data.add(i%2==0?"jack":"rose"+i);
        }

        ListView listView = (ListView) findViewById(R.id.lv);
        //准备适配器 两个比较常用的适配器  ArrayAdapter  SimpleAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_list,
                data
        );
        //设置适配器
        listView.setAdapter(adapter);
    }
}
