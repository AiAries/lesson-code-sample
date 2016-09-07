package com.feicui.edu.viewpagerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class IntroduceActivity extends AppCompatActivity {

    public int[] resIds = {R.mipmap.adware_style_applist,
            R.mipmap.adware_style_banner,
            R.mipmap.adware_style_creditswall};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        initView();
    }


    public void initView() {
        //2.
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        //3.准备适配器 自定义适配器继承FragmentStatePagerAdapter
        MyFragmentStateAdapter adapter = new MyFragmentStateAdapter(getSupportFragmentManager());
        //4.绑定适配器
        viewPager.setAdapter(adapter);
    }

    //内部类的适配器
    class MyFragmentStateAdapter extends FragmentStatePagerAdapter
    {

        public MyFragmentStateAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //position 是从0到getcount-1
            IntroduceFragment fragment = new IntroduceFragment();
            //fragment 展示页面内容
            //怎么给fragment传递参数
            Bundle bundle = new Bundle();
            bundle.putInt("i",position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            //滑动页面的数量
            return resIds.length;
        }
    }
}