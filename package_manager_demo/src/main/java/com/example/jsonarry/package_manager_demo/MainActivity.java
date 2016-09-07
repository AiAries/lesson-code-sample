package com.example.jsonarry.package_manager_demo;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends ListActivity
{

    private static final String TAG = "MainActivity";
    private MyPackageInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        PackageManager pm = this.getPackageManager();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);

        ListView listView = getListView();
        adapter = new MyPackageInfoAdapter(installedPackages, this);
        listView.setAdapter(adapter);
        String packageCodePath = getPackageCodePath();
        Log.d(TAG, "onCreate: "+packageCodePath);
//        //注册广播
//        IntentFilter fileter = new IntentFilter();
//        fileter.addAction(getPackageName());
//        registerReceiver(new MyReceiver(), fileter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        PackageInfo p = (PackageInfo) l.getItemAtPosition(position);
        String packageName = p.packageName;
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        startActivity(intent);
    }


//    class MyReceiver extends BroadcastReceiver
//    {
//        @Override
//        public void onReceive(Context context, Intent intent)
//        {
//            Toast.makeText(MainActivity.this, "onreceive", Toast.LENGTH_SHORT).show();
//            if (intent.getAction().equals(getPackageName()))
//                adapter.notifyDataSetChanged();
//        }
//    }

}
