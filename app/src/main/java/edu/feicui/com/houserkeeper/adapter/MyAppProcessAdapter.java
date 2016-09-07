package edu.feicui.com.houserkeeper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.feicui.com.houserkeeper.R;
import edu.feicui.com.houserkeeper.entity.RunningAppInfo;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class MyAppProcessAdapter extends MyBaseAdapter<RunningAppInfo> {


    private final Context context;

    public MyAppProcessAdapter(@NonNull List<RunningAppInfo> data, @NonNull Context context) {
        super(data, context);
        this.context = context;
    }

    @Override
    public View MyGetView(final int position, View convertView, ViewGroup parent) {
        final VH vh;
        if (convertView == null) {
            //把布局文件渲染成一个View
            convertView = inflater.inflate(R.layout.item_process, null);
            //给view里面的控件绑定数据,先初始化控件，再设置数据
            ImageView icon = (ImageView) convertView.findViewById(R.id.iv);
            TextView appName = (TextView) convertView.findViewById(R.id.tv_application_name);
            TextView memorySize = (TextView) convertView.findViewById(R.id.tv_memory_size);
            TextView appType = (TextView) convertView.findViewById(R.id.tv_app_type);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            vh = new VH(icon, appName, memorySize, appType,checkBox);
            convertView.setTag(vh);
        } else {
            vh = (VH) convertView.getTag();
        }
        //获取对应位置的数据,绑定数据
        final RunningAppInfo runningAppInfo = getData().get(position);
        vh.icon.setImageDrawable(runningAppInfo.getAppIcon());
        vh.appName.setText(runningAppInfo.getLabel());
        vh.memorySize.setText(String.format("内存：%s",runningAppInfo.getMemorySize()));
        vh.appType.setText(runningAppInfo.isSysApp()?"系统进程":"用户进程");

        Toast.makeText(context, "----"+position, Toast.LENGTH_SHORT).show();
        //给当前的checkbox绑定数据对象，保存checkbox勾选的状态
        vh.checkBox.setTag(runningAppInfo);
        //给checkbox设置是否选中状态
        vh.checkBox.setChecked(runningAppInfo.isSelect());
        vh.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                CheckBox cb = (CheckBox) buttonView;
                //获取之前绑定的数据对象，修改就不会出问题，
                RunningAppInfo appInfo = (RunningAppInfo) cb.getTag();
                appInfo.setSelect(isChecked);
            }
        });
        return convertView ;
    }

    class VH{
        ImageView icon;
        TextView appName;
        TextView memorySize;
        TextView appType;
        CheckBox checkBox;

        public VH(ImageView icon, TextView appName, TextView memorySize, TextView appType, CheckBox checkBox) {
            this.icon = icon;
            this.appName = appName;
            this.memorySize = memorySize;
            this.appType = appType;
            this.checkBox = checkBox;
        }
    }
}
