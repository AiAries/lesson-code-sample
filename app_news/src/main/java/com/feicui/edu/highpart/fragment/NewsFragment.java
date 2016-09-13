package com.feicui.edu.highpart.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.edu.highpart.MyRecycleViewAdapter;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.asyntask.HttpAsyncTask;
import com.feicui.edu.highpart.asyntask.LoadCallbackListener;
import com.feicui.edu.highpart.bean.News;
import com.feicui.edu.highpart.util.Const;
import com.feicui.edu.highpart.util.GsonParseUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private MyRecycleViewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rv;
    /*118.244.212.82:9092*/
    private FragmentActivity activity;
    private View container_progress;
    private View container_failed;
    String url = Const.URL+"news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        container_progress = view.findViewById(R.id.container_progress);
        container_failed = view.findViewById(R.id.container_failed);
        TextView tv_load = (TextView) view.findViewById(R.id.tv_load);
        tv_load.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加载失败的布局消失
                        container_failed.setVisibility(View.GONE);
                        //让加载进度的界面可见
                        container_progress.setVisibility(View.VISIBLE);
                        myAsyncLoad(url);
                    }
                }
        );

        refreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorGreen),
                getResources().getColor(R.color.colorYellow));

        refreshLayout.setOnRefreshListener(this);

        ArrayList<News> newses = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            News n = new News();
            n.setIcon("");
            n.setTitle("title" + i);
            n.setSummary("summary" + i);
            newses.add(n);
        }
//        recyclerView
        rv = (RecyclerView) view.findViewById(R.id.rv);
        adapter = new MyRecycleViewAdapter(newses, getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        activity = getActivity();
        myAsyncLoad(url);

        return view;
    }


    //自己写的框架
    public void myAsyncLoad(String url) {
        this.url = url;
        //        //在UI线程，创建AsyncTask子类的对象
        final HttpAsyncTask task = new HttpAsyncTask(getContext());
        //开启异步任务
        task.setListener(new LoadCallbackListener<String>() {
            @Override
            public void onSuccess(String s) {
                ArrayList<News> newses = GsonParseUtil.parseNewJsonString(s);
                Toast.makeText(activity, "成功", Toast.LENGTH_SHORT).show();
                adapter.setNewses(newses);
                adapter.notifyDataSetChanged();
                try {
                    Thread.sleep(500);
                    container_progress.setVisibility(View.GONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                container_failed.setVisibility(View.VISIBLE);
                container_progress.setVisibility(View.GONE);
            }
        });
        task.execute(url);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(activity, "refresh", Toast.LENGTH_SHORT).show();
//                myAsyncLoad();
        refreshLayout.setRefreshing(false);
    }
}
