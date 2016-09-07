package com.feicui.edu.callback_demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class MyAdapter extends BaseAdapter {

    private LoadMoreListener listener;

    public LoadMoreListener getListener() {
        return listener;
    }

    public void setListener(LoadMoreListener listener) {
        this.listener = listener;
    }

    private final Context context;

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    private  ArrayList<String> data;

    public MyAdapter(Context context, @NonNull ArrayList<String> data) {

        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data==null?null:data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //layoutinflater

        if (data.size()-position<=3)
        {
            //当滑到页面最后不超过三条数据时，触发loadMore方法
            listener.loadMore();
        }
        TextView textView = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                80
        );
        //dp  转 px  或者 px 转dp
        textView.setPadding(16,16,16,16);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER_VERTICAL);

        textView.setText(data.get(position));
        return textView;
    }
}
