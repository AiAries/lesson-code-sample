package com.example.administrator.highviewdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Gallery;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;

import com.example.administrator.highviewdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        int[] resId = new int[]{R.mipmap.logo_1,R.mipmap.logo_2,R.mipmap.logo_3,
                R.mipmap.logo_4,R.mipmap.logo_5};

        String[] strFrom = {"image"};
        List<Map<String, Integer>> data = new ArrayList<>();
        for (int aResId : resId) {
            Map<String, Integer> map = new HashMap<>();
            map.put(strFrom[0], aResId);
            data.add(map);
        }
        SpinnerAdapter adapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_gallery_item,
                /*R.layout.item_gallery,*/
                strFrom,
                new int[]{/*R.id.imageView*/android.R.id.text1}
        );

        assert gallery != null;
        gallery.setAdapter(adapter);
    }
}
