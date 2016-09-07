package com.feicui.edu.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


//    第2种，反射模式
    public void jump(View view) {
//        Intent intent = new Intent(MainActivity.this, LiftcycleActivity.class);
//        startActivity(intent);
        //百度一下。。。
        Toast.makeText(MainActivity.this, "wereredsf", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取数据，是否为空。。。
        int[] a = new int[6];
        for (int i = 0; i < a.length; i++) {
            //System.out.println();以后不要用这个。。不方便筛选日志
            Log.d("MainActivity", "onCreate: 第一次打印log日志,数组的元素是"+a[i]);
        }
        //首先是不是要找到对象，通过唯一表示id值
         Button b  = (Button) findViewById(R.id.jump);
        //万能快捷建，alt+enter建
        if (b != null) {
            //第一种，懒人模式  写一个匿名内部类 实现View.OnClickListener接口
           /* b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LiftcycleActivity.class);
                    startActivity(intent);
                }
            });*/
            //
            b.setOnClickListener(this/*new MyOnClickListenrImp()*/);
        }



    }

    //第四中写法 ，最常用见写法，让当前类，实现View.OnClickListener点击监听接口
    @Override
    public void onClick(View v) {
        //当按钮被点击的时候触发这个方法
        Intent intent = new Intent(MainActivity.this, LiftcycleActivity.class);
        startActivity(intent);
    }

    //第三种方式 自定义一个类实现View.OnClickListener点击监听接口
    class MyOnClickListenrImp implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //就是一个监听器
            //当按钮被点击的时候触发这个方法
            Intent intent = new Intent(MainActivity.this, LiftcycleActivity.class);
            startActivity(intent);
        }
    }


}
