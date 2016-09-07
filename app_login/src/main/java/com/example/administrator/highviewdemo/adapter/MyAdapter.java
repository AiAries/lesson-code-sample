package com.example.administrator.highviewdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.highviewdemo.R;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class MyAdapter extends BaseAdapter{


    private  Context context;
    private String[] main_str;
    private int item_list_view;

    public MyAdapter(String[] main_str, int item_list_view, Context context) {
        this.main_str = main_str;
        this.item_list_view = item_list_view;
        this.context=context;
    }

    @Override
    public int getCount() {
        //listview要展示数据的条数
        return main_str.length;
    }

    @Override
    public Object getItem(int position) {
        //获取listview指定position处条目数据内容
        return main_str[position];
    }

    @Override
    public long getItemId(int position) {
        //分配给listview每个条目的id
        System.out.println(position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //给listview每个条目准备视图
        //把布局文件xxx.xml,渲染成一个View
        View view = LayoutInflater.from(context).inflate(item_list_view, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(main_str[position]);
        return view;
    }
}
