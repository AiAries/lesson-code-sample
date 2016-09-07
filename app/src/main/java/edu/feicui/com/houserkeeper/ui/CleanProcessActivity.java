package edu.feicui.com.houserkeeper.ui;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.feicui.com.houserkeeper.R;
import edu.feicui.com.houserkeeper.adapter.MyAppProcessAdapter;
import edu.feicui.com.houserkeeper.entity.RunningAppInfo;
import edu.feicui.com.houserkeeper.process.AndroidAppProcess;
import edu.feicui.com.houserkeeper.process.ProcessManager;

public class CleanProcessActivity extends BaseActivity implements View.OnClickListener {

    private ActivityManager activityManager;
    private MyAppProcessAdapter appAdapter;
    private List<RunningAppInfo> runningAppInfos;
    private TextView tv_memeory;
    private ProgressBar pb_memory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_process);
        initToolBar();
        initView();
    }

    @Override
    public void initView() {
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        //找到控件对象
        TextView tv_brand = (TextView) findViewById(R.id.tv_brand);
        TextView tv_phone_type = (TextView) findViewById(R.id.tv_phone_type);
        tv_memeory = (TextView) findViewById(R.id.tv_memeory);
        Button btn_show_process = (Button) findViewById(R.id.btn_show_process);
        ListView listView = (ListView) findViewById(R.id.listview_process);
        ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
        pb_memory = (ProgressBar) findViewById(R.id.pb_phone_memory_use_rate);

        //初始事件
        btn_show_process.setOnClickListener(this);
        findViewById(R.id.btn_clean_process).setOnClickListener(this);
        ((CheckBox) findViewById(R.id.checkbox)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //当checkbox被点击的时候回调此方法
                        //让所有的条目勾选中
                        //得到适配器中所有的数据
                        List<RunningAppInfo> data = appAdapter.getData();
                        for (RunningAppInfo runningAppInfo : data) {
                            //把所有条目是否选中的属性，改成trueOrfalse
                            runningAppInfo.setSelect(isChecked);
                        }
                        appAdapter.notifyDataSetChanged();
                    }
                }
        );

        //初始化 listview要展示的数据
        //解决兼容性问题，21以上用第三方写的ProcessManager，21以下用android自带的
        //判断版本号,先获取系统的版本号
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 21) {
            //解决5.0没有的效果
            runningAppInfos = getRunningAppInfo21();
        } else {
            runningAppInfos = getRunningAppInfo();
        }

        appAdapter = new MyAppProcessAdapter(runningAppInfos, this);
        listView.setAdapter(appAdapter);

        //品牌，手机型号
        String brand = Build.BRAND;
        String type = Build.MODEL + getPhoneSystemVersion();
        tv_brand.setText(brand);
        tv_phone_type.setText(type);

        //设置内存显示信息
        setMemoryInfo();
    }

    private void setMemoryInfo() {
        //手机总内存，使用掉的内存
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        //版本低于16没有效果，这里不考虑
        long totalMem = memoryInfo.totalMem;
        long availMem = memoryInfo.availMem;
        String strTotalMem = Formatter.formatFileSize(this, totalMem);
        String strUseMem = Formatter.formatFileSize(this, totalMem - availMem);
        tv_memeory.setText(strUseMem + "/" + strTotalMem);
        pb_memory.setMax((int) (totalMem));
        pb_memory.setProgress((int) ((totalMem - availMem)));
    }

    private String getPhoneSystemVersion() {
        return /*Build.VERSION.CODENAME+*/
                Build.VERSION.RELEASE;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clean_process:
                //存放杀死的进程
                List<RunningAppInfo> killedProcess = new ArrayList<>();
                if (runningAppInfos != null) {
                    for (int i = 0; i < runningAppInfos.size(); i++) {
                        RunningAppInfo runningAppInfo = runningAppInfos.get(i);
                        boolean select = runningAppInfo.isSelect();
                        if (select) {
                            activityManager.killBackgroundProcesses(runningAppInfo.getPackageName());
                            killedProcess.add(runningAppInfo);
                        }
                    }
                }
                //杀死进程后，把被杀死的进程从集合中移除
                runningAppInfos.removeAll(killedProcess);
                //当数据发生变化时，可以刷新listview列表
                appAdapter.notifyDataSetChanged();
                //更新进度条还有文字
                setMemoryInfo();
                break;
            case R.id.btn_show_process:
                //获取按钮上的文件
                Button b = (Button) v;
                String text = (String) b.getText();

                List<RunningAppInfo> process = new ArrayList<>();
                boolean isShowSysApp = text.equals(getString(R.string.speedup_show_sysapp));
                //isShowSysApp 为true存放系统进程，否则存放用户进程
                if (isShowSysApp) {
                    for (RunningAppInfo runningAppInfo : runningAppInfos) {
                        boolean isSysApp = runningAppInfo.isSysApp();
                        if (isSysApp) {
                            //是系统的进程
                            process.add(runningAppInfo);
                        }
                    }
                } else if (!isShowSysApp){
                    for (RunningAppInfo runningAppInfo : runningAppInfos) {
                        boolean isSysApp = runningAppInfo.isSysApp();
                        if (!isSysApp) {
                            //是用户的进程
                            process.add(runningAppInfo);
                        }
                    }
                }
                //改变按钮文字
                b.setText(isShowSysApp?R.string.speedup_show_sysapp
                                    :R.string.speedup_show_userapp);
                //修改数据
                appAdapter.setData(process);
                //刷新list列表
                appAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * //解决5.0没有的效果
     *
     * @return
     */
    public List<RunningAppInfo> getRunningAppInfo21() {
        List<RunningAppInfo> runningAppInfos = new ArrayList<>();

        List<AndroidAppProcess> processInfos = ProcessManager.getRunningAppProcesses();

        Iterator<AndroidAppProcess> iterator = processInfos.iterator();
        while (iterator.hasNext()) {
            AndroidAppProcess appProcess = iterator.next();
            int pid = appProcess.pid;//进程唯一标识
            String processName = appProcess.getPackageName();//包名，进程名
//            int importance = appProcess.i;//获取进程的类别（前台，可见，后台，等等）

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

                RunningAppInfo runningAppInfo = new RunningAppInfo(label, processName, memorySize, drawable, isSysApp, 0);
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
        while (iterator.hasNext()) {
            ActivityManager.RunningAppProcessInfo appProcess = iterator.next();
            int pid = appProcess.pid;//进程唯一标识
            String processName = appProcess.processName;//包名，进程名
            int importance = appProcess.importance;//获取进程的类别（前台，可见，后台，等等）
            if (importance < ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE)
                continue;
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

                RunningAppInfo runningAppInfo = new RunningAppInfo(label, processName, memorySize, drawable, isSysApp, importance);
                //将该对象保存到集合中
                runningAppInfos.add(runningAppInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return runningAppInfos;
    }
}
