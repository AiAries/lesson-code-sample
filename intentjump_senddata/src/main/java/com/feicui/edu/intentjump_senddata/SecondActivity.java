package com.feicui.edu.intentjump_senddata;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class SecondActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置视图布局
        setContentView(R.layout.activity_second);
        TextView textView = (TextView) findViewById(R.id.tv);
        //获取intent对象
        Intent intent = getIntent();
        if (intent != null) {
            Person syq = (Person) intent.getSerializableExtra("syq");
            Toast.makeText(SecondActivity.this, ""+syq.getHobby()+syq.getName(), Toast.LENGTH_SHORT).show();
//            int id = intent.getIntExtra("id", 0);
//            String name = intent.getStringExtra("name");
//            Toast.makeText(SecondActivity.this, ""+id, Toast.LENGTH_SHORT).show();
//            textView.setText(/*R.string.app_name*/name);
        }
    }
}
