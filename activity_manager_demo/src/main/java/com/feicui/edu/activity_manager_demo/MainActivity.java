package com.feicui.edu.activity_manager_demo;

import android.app.ActivityManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.feicui.edu.activity_manager_demo.process.AndroidAppProcess;
import com.feicui.edu.activity_manager_demo.process.ProcessManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends ListActivity {

    private ActivityManager activityManager;
    private List<RunningAppInfo> runningAppInfos;
    private MyAppProcessAdapter appAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.item_process);
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        //解决兼容性问题，21以上用第三方写的ProcessManager，21以下用android自带的
        //判断版本号,先获取系统的版本号
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 21) {
            //解决5.0没有的效果
            runningAppInfos= getRunningAppInfo21();
        } else {
            runningAppInfos = getRunningAppInfo();
        }
        appAdapter = new MyAppProcessAdapter(runningAppInfos, this);
        getListView().setAdapter(appAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
//        l.getItemAtPosition(position);
        RunningAppInfo runningAppInfo = runningAppInfos.get(position);
        String packageName = runningAppInfo.getPackageName();
        Toast.makeText(MainActivity.this, packageName, Toast.LENGTH_SHORT).show();
        activityManager.killBackgroundProcesses(packageName);
        runningAppInfos.remove(runningAppInfo);
        appAdapter.notifyDataSetChanged();
    }


    /**
     *  //解决5.0没有的效果
     * @return
     */
    public List<RunningAppInfo> getRunningAppInfo21() {
        List<RunningAppInfo> runningAppInfos = new ArrayList<>();

        List<AndroidAppProcess> processInfos = ProcessManager.getRunningAppProcesses();

        Iterator<AndroidAppProcess> iterator = processInfos.iterator();
        Toast.makeText(MainActivity.this, "" + processInfos.size(), Toast.LENGTH_SHORT).show();
        while (iterator.hasNext()) {
            AndroidAppProcess appProcess = iterator.next();
            int pid = appProcess.pid;//进程唯一标识
            String processName = appProcess.getPackageName();//包名，进程名
//            int importance = appProcess.ge;//获取进程的类别（前台，可见，后台，等等）

            //获取进程所占内存的大小
            Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{pid});
            Debug.MemoryInfo memoryInfo = processMemoryInfo[0];
            long totalPrivateDirty = memoryInfo.getTotalPrivateDirty() * 1024;//dirty 是 文件缩写 返回值的单位kb 转成b *1024
            //把占用内容大小的值，转化成M
            String memorySize = Formatter.formatShortFileSize(this, totalPrivateDirty);

            //通过包名获取该包的应用信息   获取单个包的信息
            try {
                PackageManager pm = getPackageManager();
                PackageInfo packageInfo = pm.
                        getPackageInfo(processName, PackageManager.GET_UNINSTALLED_PACKAGES);
                //得到应用程序的图标，即是手机桌面看到的图标
                Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
                //获取应用程序的标签名，即是手机桌面看到的应用名字
                String label = (String) packageInfo.applicationInfo.loadLabel(pm);
                //判断该进程是用户的还是系统的
                boolean isSysApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1;

                RunningAppInfo runningAppInfo = new RunningAppInfo(label,processName, memorySize, drawable, isSysApp, 0);
                //将该对象保存到集合中
                runningAppInfos.add(runningAppInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return runningAppInfos;
    }

    public List<RunningAppInfo> getRunningAppInfo() {
        List<RunningAppInfo> runningAppInfos = new ArrayList<>();

        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();

        Iterator<ActivityManager.RunningAppProcessInfo> iterator = processInfos.iterator();
        Toast.makeText(MainActivity.this, "" + processInfos.size(), Toast.LENGTH_SHORT).show();
        while (iterator.hasNext()) {
            ActivityManager.RunningAppProcessInfo appProcess = iterator.next();
            int pid = appProcess.pid;//进程唯一标识
            String processName = appProcess.processName;//包名，进程名
//            int importance = appProcess.ge;//获取进程的类别（前台，可见，后台，等等）

            //获取进程所占内存的大小
            Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{pid});
            Debug.MemoryInfo memoryInfo = processMemoryInfo[0];
            long totalPrivateDirty = memoryInfo.getTotalPrivateDirty() * 1024;//dirty 是 文件缩写 返回值的单位kb 转成b *1024
            //把占用内容大小的值，转化成M
            String memorySize = Formatter.formatShortFileSize(this, totalPrivateDirty);

            //通过包名获取该包的应用信息   获取单个包的信息
            try {
                PackageManager pm = getPackageManager();
                PackageInfo packageInfo = pm.
                        getPackageInfo(processName, PackageManager.GET_UNINSTALLED_PACKAGES);
                //得到应用程序的图标，即是手机桌面看到的图标
                Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
                //获取应用程序的标签名，即是手机桌面看到的应用名字
                String label = (String) packageInfo.applicationInfo.loadLabel(pm);
                //判断该进程是用户的还是系统的
                boolean isSysApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1;

                RunningAppInfo runningAppInfo = new RunningAppInfo(label,processName, memorySize, drawable, isSysApp, 0);
                //将该对象保存到集合中
                runningAppInfos.add(runningAppInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return runningAppInfos;
    }
}
