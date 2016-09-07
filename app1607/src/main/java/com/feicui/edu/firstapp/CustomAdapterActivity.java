package com.feicui.edu.firstapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.feicui.edu.firstapp.adapter.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter);

        ListView listView = (ListView) findViewById(R.id.lv);
        List<String> data = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            data.add("rose is a boy At position"+i);
        }
        CustomAdapter adapter = new CustomAdapter(data,R.layout.item_custom_adapter,this);
        listView.setAdapter(adapter);
    }
}
