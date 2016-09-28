package com.feicui.edu.highpart.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.edu.highpart.MainActivity;
import com.feicui.edu.highpart.MyApplication;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.adapter.CommentRecycleViewAdapter;
import com.feicui.edu.highpart.bean.Comment;
import com.feicui.edu.highpart.biz.LocalCommentDBManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class CommentFragment extends Fragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv)
    RecyclerView rv;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当activity创建的时候，回调
        if (getActivity() instanceof MainActivity) {
            toolbar.setTitle("我的跟帖");
            MainActivity activity = (MainActivity) getActivity();
            activity.setSupportActionBar(toolbar);//设置支持toolbar
            activity.backToMainActivity(toolbar);//给toolbar设置点击事件
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, null);
        ButterKnife.bind(this, view);

        final ArrayList<Comment> data = new ArrayList<>();
        final FragmentActivity activity = getActivity();
        final CommentRecycleViewAdapter adapter = new CommentRecycleViewAdapter(data, getContext());
        rv.setLayoutManager(new LinearLayoutManager(activity));
        rv.setAdapter(adapter);
        new Thread() {
            @Override
            public void run() {
                super.run();

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyApplication application = (MyApplication) activity.getApplication();
                        final ArrayList<Comment> data = LocalCommentDBManager.query(application.commentSQLiteOP);
                        adapter.setCommentes(data);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
