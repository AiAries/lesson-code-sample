package com.feicui.edu.firstapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.feicui.edu.firstapp.adapter.ImageAdapter;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
//手写的需要自己去清单文件注册
//<activity android:name=".GridActivity" />
public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        int[] data = new int[]{
                R.drawable.a0,R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4
        };
        gridview.setAdapter(new ImageAdapter(this,data));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //当条目被点击，会触发这个方法onItemClick
                Toast.makeText(GridActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
