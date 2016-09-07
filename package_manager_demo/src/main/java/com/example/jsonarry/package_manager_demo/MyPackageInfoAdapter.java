package com.example.jsonarry.package_manager_demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aries on 2016/8/11.
 */
public class MyPackageInfoAdapter extends BaseAdapter
{
    List<PackageInfo> installedPackages;
    private final LayoutInflater inflater;
    private final PackageManager pm;

    public MyPackageInfoAdapter(@NonNull List<PackageInfo> installedPackages, Context context)
    {
        this.installedPackages = installedPackages;
        pm = context.getPackageManager();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return installedPackages.size();
    }

    @Override
    public Object getItem(int position)
    {
        return installedPackages.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.activity_main, null);
        }
        PackageInfo packageInfo = installedPackages.get(position);
        int versionCode = packageInfo.versionCode;
        String versionName = packageInfo.versionName;
        String packageName = packageInfo.packageName;
        CharSequence label = packageInfo.applicationInfo.loadLabel(pm);
        Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
        convertView.findViewById(R.id.iv).setBackground(drawable);

        TextView tv1 = (TextView) convertView.findViewById(R.id.tv_application_name);
        tv1.setText(label);

        TextView tv2 = (TextView) convertView.findViewById(R.id.tv_code);
        tv2.setText(versionCode+"");

        TextView tv3 = (TextView) convertView.findViewById(R.id.tv_package_name);
        tv3.setText(packageName);

        TextView tv4 = (TextView) convertView.findViewById(R.id.tv_version_name);
        tv4.setText(versionName);
        return convertView;
    }
}
