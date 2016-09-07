package com.feicui.edu.viewanimationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv);
        //将xml写好的动画加载进来
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.my_set);
//        ScaleAnimation animation = new ScaleAnimation(1.0f,2.0f,1.0f,5.0f,0.5f,0.5f);
//        animation.set
        animation.setDuration(5000);
        //animation.setRepeatCount(Animation.INFINITE);
        assert tv != null;
        tv.startAnimation(animation);
//        animation.start();
    }
}
