package com.feicui.edu.frameanim;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        //帧动画FrameAnimation  AnimationDrawable
        assert iv != null;
        animationDrawable = (AnimationDrawable) iv.getBackground();
        //帧动画开始执行
        //animationDrawable.start();
        animationDrawable.selectDrawable(3);
    }


    public void start(View view)
    {
        if (!animationDrawable.isRunning())
        {
            animationDrawable.start();
        }
    }
    public void stop(View view)
    {
        if (animationDrawable.isRunning())
             animationDrawable.stop();
    }

}
