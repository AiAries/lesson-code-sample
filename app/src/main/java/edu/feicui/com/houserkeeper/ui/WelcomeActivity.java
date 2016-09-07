package edu.feicui.com.houserkeeper.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import edu.feicui.com.houserkeeper.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void initView() {

//        ImageView imageView = new ImageView(this);
//        ViewGroup.LayoutParams params = imageView.getLayoutParams();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        imageView.setLayoutParams(params);
//        imageView.setBackgroundResource(R.mipmap.androidy);
        setContentView(R.layout.activity_welcome);
        ImageView iv = (ImageView) findViewById(R.id.iv);
//        iv.setTranslationX();
//        iv.setscarex
        //属性动画-透明度动画
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(
                iv,
                "alpha",
                0.3f,
                1.0f
        );
        //旋转动画
        ObjectAnimator rotation = ObjectAnimator.ofFloat(
                iv,
                "rotation",//translationY
                0,
                360
        );
        //x缩放动画
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(
                iv,
                "scaleX",//translationY
                1,
                0.5f
        );
        //y缩放动画
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(
                iv,
                "scaleY",//translationY
                1,
                0.5f
        );
//        AnimatorSet.Builder = AnimatorSet.;
        AnimatorSet set = new AnimatorSet();
        set.playTogether(alphaAnim,rotation);
        set.play(scaleXAnim).after(rotation);
        set.playTogether(scaleXAnim,scaleYAnim);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        //设置监听器
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //当动画执行完毕，进行一个跳转到主界面
                myStartActivity(MainActivity.class);
                //跳转之后，关闭当前界面
                finish();
            }
        });

        set.start();
    }
}
