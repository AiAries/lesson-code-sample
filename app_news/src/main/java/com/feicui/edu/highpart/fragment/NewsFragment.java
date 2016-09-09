package com.feicui.edu.highpart.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feicui.edu.highpart.MyRecycleViewAdapter;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.asyntask.HttpAsyncTask;
import com.feicui.edu.highpart.asyntask.LoadCallbackListener;
import com.feicui.edu.highpart.bean.News;
import com.feicui.edu.highpart.util.GsonParseUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private MyRecycleViewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rv;
    String url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_view, null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorGreen),
                getResources().getColor(R.color.colorYellow));

        refreshLayout.setOnRefreshListener(this);


        ArrayList<News> newses = new ArrayList<>();

//        recyclerView
        rv = (RecyclerView) view.findViewById(R.id.rv);
        adapter = new MyRecycleViewAdapter(newses, getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(adapter);

        myAsyncLoad();
        return view;
    }
    //自己写的框架
    private void myAsyncLoad() {
        //        //在UI线程，创建AsyncTask子类的对象
        final HttpAsyncTask task = new HttpAsyncTask(getContext());
        //开启异步任务
        task.setListener(new LoadCallbackListener<String>() {
            @Override
            public void onSuccess(String s) {
                ArrayList<News> newses = GsonParseUtil.parseNewJsonString(s);
                Toast.makeText(getContext(), "成功", Toast.LENGTH_SHORT).show();
                adapter.setNewses(newses);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
        task.execute(url);
    }
    @Override
    public void onRefresh() {
        Toast.makeText(getContext(), "refresh", Toast.LENGTH_SHORT).show();
//                myAsyncLoad();
        refreshLayout.setRefreshing(false);
    }
}
