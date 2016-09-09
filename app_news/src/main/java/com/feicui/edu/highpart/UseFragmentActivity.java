package com.feicui.edu.highpart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.feicui.edu.highpart.fragment.FavoriteFragment;
import com.feicui.edu.highpart.fragment.NewsFragment;

public class UseFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_fragment);
//        getSupportFragmentManager() ---> android.support.v4.app.Fragment;
//        getFragmentManager(); --->  android.app.Fragment;

//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.container, new NewsFragment());
//        transaction.commit();
        /*
        *  Activity 的布局中需要包含一个作为 Fragment 容器的空 FrameLayout。
        * 官网笔记：https://developer.android.com/training/basics/fragments/fragment-ui.html#Replace
        * */
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,  new NewsFragment()).commit();
    }

    public void replace(View view) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FavoriteFragment())/*.addToBackStack(null)*/.commit();
    }
}
