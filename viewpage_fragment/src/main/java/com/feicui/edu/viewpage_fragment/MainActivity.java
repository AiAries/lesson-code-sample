package com.feicui.edu.viewpage_fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String[] title = new String[]{"新闻","体育","军事"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        final PagerTabStrip pts = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
        pts.setTabIndicatorColor(Color.RED);
        pts.setTextColor(Color.WHITE);

        FragmentManager fm = getSupportFragmentManager();
        MyPagerAdapter adapter = new MyPagerAdapter(fm);
        viewPager.setAdapter(adapter);
        final TextView tv_flip = (TextView) findViewById(R.id.tv1);

        final LinearLayout tv_container = (LinearLayout) findViewById(R.id.tv_container);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
             long duration = 20;
             int moveDistance = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                int count = tv_container.getChildCount();
                for (int i = 0; i < count; i++) {
                    View v = tv_container.getChildAt(i);
                    v.setBackgroundDrawable(getResources().getDrawable(R.mipmap.adware_style_default));
                }
                View v =tv_container.getChildAt(position);
//                ObjectAnimator animation = ObjectAnimator.ofFloat(
//                        tv_flip,
//                        "translationX",
//                        moveDistance,
//                        moveDistance+tv_container.getWidth()/duration
//                );
//                ObjectAnimator animation = new TranslateAnimation(
//                        v.getPivotX(),
//                        v.getPivotX()+(tv_container.getWidth()/3)*positionOffset,
//                        0,
//                        0
//                );
//                animation.setDuration(duration);
//                animation.start();
                v.setBackgroundDrawable(getResources().getDrawable(R.mipmap.adware_style_selected));
            }
        });
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter
    {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("i",position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position]+position;
        }
    }

}
