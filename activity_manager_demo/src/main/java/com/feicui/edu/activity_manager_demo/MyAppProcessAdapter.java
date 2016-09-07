package com.feicui.edu.activity_manager_demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class MyAppProcessAdapter extends BaseAdapter {

    private final List<RunningAppInfo> runningAppInfos;
    private final Context context;

    public MyAppProcessAdapter(@NonNull List<RunningAppInfo> runningAppInfos, Context context) {

        this.runningAppInfos = runningAppInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return runningAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return runningAppInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //把布局文件渲染成一个View
        View view = inflater.inflate(R.layout.item_process, null);
        //给view里面的控件绑定数据,先初始化控件，再设置数据
        ImageView icon = (ImageView) view.findViewById(R.id.iv);
        TextView appName = (TextView) view.findViewById(R.id.tv_application_name);
        TextView memorySize = (TextView) view.findViewById(R.id.tv_memory_size);
        TextView appType = (TextView) view.findViewById(R.id.tv_app_type);

        //获取对应位置的数据
        RunningAppInfo runningAppInfo = runningAppInfos.get(position);
        icon.setImageDrawable(runningAppInfo.appIcon);
        appName.setText(runningAppInfo.label);
        memorySize.setText(String.format("内存：%s",runningAppInfo.memorySize));
        appType.setText(runningAppInfo.isSysApp?"系统进程":"用户进程");
        return view ;
    }
}
