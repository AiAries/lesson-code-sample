package com.feicui.edu.highpart.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feicui.edu.highpart.MainActivity;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.bean.NewsGroup;
import com.feicui.edu.highpart.util.Const;
import com.feicui.edu.highpart.util.HttpUtil;
import com.feicui.edu.highpart.util.UrlParameterUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class NewsGroupFragment extends Fragment {

    private static final String TAG = "NewsGroupFragment";
    public Toolbar toolbar;
    private MainActivity activity;
    private List<String> titles = new ArrayList<>();
    private List<Integer> subids = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_group, null);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        final ViewPager vp = (ViewPager) view.findViewById(R.id.news_viewpager);
        final TabLayout tab = (TabLayout) view.findViewById(R.id.news_tabs);
        //设置tablayout滚动
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);


        //异步加载新闻标题数据
        new Thread(){
            @Override
            public void run() {
                super.run();
                parseNewsGroupJsonString();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //给viewpager设置适配器
                        final ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
                        for (int i = 0; i < titles.size(); i++) {
                            adapter.addFrag(new NewsFragment(),titles.get(i));
                        }
                        vp.setAdapter(adapter);

                        //把tablayout和viewpager进行绑定
                        tab.setupWithViewPager(vp);
                        //默认给第一个fragment加载数据，
                        NewsFragment fragment = (NewsFragment) adapter.mFragmentList.get(0);
                        String url = Const.URL_NEW_LIST+"ver=1&subid="+subids.get(0) +"&dir=1&nid=1&stamp=20140321&cnt=20";
                        fragment.myAsyncLoad(url);
                        //处理tablayout点击事件
                        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp){
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                super.onTabSelected(tab);
                                //viewpager滑动和tablayout的点击同时触发这个方法一次
                                Toast.makeText(activity, tab.getText(), Toast.LENGTH_SHORT).show();
                                //让选中的fragment加载新闻数据
                                int position = tab.getPosition();
                                NewsFragment fragment = (NewsFragment) adapter.mFragmentList.get(position);
//                              "ver=1&subid="+subids.get(position) +"&dir=1&nid=1&stamp=20140321&cnt=20"
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("ver", "1");
                                map.put("subid", subids.get(position)+"");
                                map.put("dir", "1");
                                map.put("nid", "1");
                                map.put("stamp", "20140321");
                                map.put("cnt", "20");
                                String url = Const.URL_NEW_LIST+ UrlParameterUtil.parameter(map);
                                fragment.myAsyncLoad(url);
                            }
                        });
                    }
                });
            }
        }.start();

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_share) {
            Toast.makeText(activity, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof MainActivity) {
            activity = (MainActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            activity.setUpNavDrawer(toolbar);
        }
    }
    static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            Log.d("ViewPagerAdapter", "getCount: "+mFragmentList.size());
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    //解析json数据
    public void parseNewsGroupJsonString() {
        String url = "http://118.244.212.82:9092/newsClient/news_sort?ver=1&imei=1";
        String data = HttpUtil.getJsonString(url);
        if (data == null) {
            Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "onCreate: " + data);

        Gson gson = new Gson();
        Type type = new TypeToken<NewsGroup<List<NewsGroup.DataBean<List<NewsGroup.DataBean.SubgrpBean>>>>>() {
        }.getType();

        NewsGroup newsGroup = gson.fromJson(data, type);
        Log.d(TAG, "parseNewsGroupJsonString: " + newsGroup.getMessage());
        List<NewsGroup.DataBean> data1 = (List<NewsGroup.DataBean>) newsGroup.getData();
        for (NewsGroup.DataBean dataBean : data1) {
            String group = dataBean.getGroup();
            Log.d(TAG, "group: " + group);
            List<NewsGroup.DataBean.SubgrpBean> subgrp = (List<NewsGroup.DataBean.SubgrpBean>) dataBean.getSubgrp();
            for (NewsGroup.DataBean.SubgrpBean subgrpBean : subgrp) {
                Log.d(TAG, "sub grp: "+subgrpBean.getSubid());
                titles.add(subgrpBean.getSubgroup());
                subids.add(subgrpBean.getSubid());
            }
        }

    }
}
