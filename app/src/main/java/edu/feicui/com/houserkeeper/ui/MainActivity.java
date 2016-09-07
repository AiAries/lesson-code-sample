package edu.feicui.com.houserkeeper.ui;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import edu.feicui.com.houserkeeper.R;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始toolbar，第一，在布局中加上toolbar控件；第二必须在setcontentView之后调用
        initToolBar();
        initView();

        //获取manifest节点meta的数据
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA |
                            PackageManager.GET_SHARED_LIBRARY_FILES |
                            PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Integer password = (Integer) applicationInfo.metaData.get("password");
        showToast("meta"+password);
    }

    @Override
    public void initView() {
        //通过代码设置主题
        //setTheme(R.style.AppTheme);
        //改变导航的图标
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        //button
        findViewById(R.id.btn_file_mgr).setOnClickListener(this);
        findViewById(R.id.btn_phone_mgr).setOnClickListener(this);
        findViewById(R.id.btn_rocket).setOnClickListener(this);
        findViewById(R.id.btn_rubbish_clean).setOnClickListener(this);
        findViewById(R.id.btn_soft_mgr).setOnClickListener(this);
        findViewById(R.id.btn_tel_mgr).setOnClickListener(this);
    }

    //当菜单第一次被创建的时候，会调用，只会调用一次
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //LayoutInflater
        //MenuInflater  把menu的xml文件渲染到Menu中
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //当菜单的item被选中的时候，会回调此方法
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.share:
                showToast("share");
                break;
            case R.id.save:
                Toast.makeText(MainActivity.this, "save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    //update菜单 ，有些菜单功能需要隐藏等等。。
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_file_mgr:

                break;
            case R.id.btn_phone_mgr:

                break;
            case R.id.btn_rocket:
                myStartActivity(CleanProcessActivity.class);
                break;
            case R.id.btn_rubbish_clean:

                break;
            case R.id.btn_soft_mgr:
                myStartActivity(SoftMgrActivity.class);
                break;
            case R.id.btn_tel_mgr:
                myStartActivity(ShowTelTypeActivity.class);
                break;
        }
    }
}
