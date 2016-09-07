package edu.feicui.com.houserkeeper.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.feicui.com.houserkeeper.R;
import edu.feicui.com.houserkeeper.adapter.ShowSoftAdapter;

public class ShowSoftActivity extends BaseActivity {

    private ArrayList<String> myPackageName;
    public final int OK = 1;
    private Handler h = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==OK)
            {
                if (msg.obj instanceof List) {
                    List<PackageInfo> data = (List<PackageInfo>) msg.obj;
                    dialog.dismiss();
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }

            }
        }
    };
    private ShowSoftAdapter adapter;
    private ProgressDialog dialog;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_soft);
        initToolBar();
        initView();
    }

    @Override
    public void initView() {
        //创建广播
        receiver = new MyReceiver();
        //准备过滤器
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        //注册
        registerReceiver(receiver,filter);

        //加载时，让整个界面不可见 ，同时加载对话框
//        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_container);
//        layout.setVisibility(View.INVISIBLE);

        //创建进度对话框
        /*,android.R.style.Widget_DeviceDefault_ProgressBar*/
        dialog = new ProgressDialog(this/*,android.R.style.Widget_DeviceDefault_ProgressBar*/);
        dialog.setMessage("数据加载中...");
//        dialog.setTitle("温馨提示");
//        dialog.setIcon(R.mipmap.ic_launcher);//要有图标，必须设置title属性
//        dialog.setContentView();//自定布局
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        //初始化listview控件
        ListView userSoft = (ListView) findViewById(R.id.listview_soft);
        //先准备适配器需要的数据
        //根据上个界面传来的参数筛选安装的包
        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", -1);
        //用子线程来加载数据
        asyncLoadData(flag);

        List<PackageInfo> data = new ArrayList<>();
        adapter = new ShowSoftAdapter(data,this);
        userSoft.setAdapter(adapter);

        //初始化checkbox,button
        CheckBox checkAll = (CheckBox) findViewById(R.id.checkbox);
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //当checkbox被点击时回调次方法
                //让listview中的条目全部打钩，这个动作具体执行的细节adapter更清楚
                adapter.setAllItemChecked(isChecked);
                adapter.notifyDataSetChanged();
            }
        });

        //初始话卸载按钮
        findViewById(R.id.btn_uninstall).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击回调此方法，卸载所有选中的条目对应的包、
                myPackageName = getMyPackageName(adapter.getIschecks());
                for (String packageName : myPackageName) {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                }
            }
        });
    }

    private void asyncLoadData(final int flag) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<PackageInfo> data =  selectInstalledpackage(flag);
                //数据加载完毕，通过handler发一条信息
                Message m = new Message();
                m.what = OK;
                m.obj = data;
                h.sendMessage(m);
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当界面被销毁时，取消广播注册
        unregisterReceiver(receiver);
    }

    /**
     * 获取选中条目的包名
     * @param ischecks
     */
    private ArrayList<String> getMyPackageName(HashMap<String, Boolean> ischecks) {
        ArrayList<String> packageNames = new ArrayList<>();
        //Map集合不能使用迭代器，需要转化成set集合
        Set<Map.Entry<String, Boolean>> entries = ischecks.entrySet();
        //通过set集合获取对应的迭代器
        Iterator<Map.Entry<String, Boolean>> iterator = entries.iterator();
        //通过迭代器遍历所有的数据
        while (iterator.hasNext()) {
            Map.Entry<String, Boolean> next = iterator.next();
            String key = next.getKey();
            Boolean value = next.getValue();
            if (value) {
                packageNames.add(key);
            }
        }
        return packageNames;
    }

    private List<PackageInfo> selectInstalledpackage(int flag) {
        //获取所有的安装包
        List<PackageInfo> allData = this.getPackageManager().getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES
        );

        if (flag==SoftMgrActivity.ALL_SOFT)
        {
            return allData;
        }

        List<PackageInfo> data = new ArrayList<>();
        Iterator<PackageInfo> iterator = allData.iterator();
        while (iterator.hasNext()) {
            //取出迭代器中当前指针指向的元素
            PackageInfo packageInfo = iterator.next();
            //判断该应用是系统还是用户安装的标识
            int flags = packageInfo.applicationInfo.flags;

            switch (flag) {
                    case SoftMgrActivity.SYSTEM_SOFT:
                        if ((flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                            //是系统应用.靠谱
                            data.add(packageInfo);
                        }
                        break;
                case SoftMgrActivity.USER_SOFT:
                    if ((flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
                        //用户应用
                        data.add(packageInfo);
                    }
                    break;
            }

        }

        return data;
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(ShowSoftActivity.this, "receiver", Toast.LENGTH_SHORT).show();
            //从存放选中条目标记的集合中移除掉已删除的数据
            for (String s : myPackageName) {
                adapter.getIschecks().remove(s);
            }
            //刷新适配器列表
            adapter.notifyDataSetChanged();
            //广播搭建过程：动态创建广播，注册广播，添加过滤器
            //当接受到广播时，回调此方法
        }
    }

}
