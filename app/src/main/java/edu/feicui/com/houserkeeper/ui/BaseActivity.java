package edu.feicui.com.houserkeeper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import edu.feicui.com.houserkeeper.R;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public abstract class BaseActivity extends AppCompatActivity{

    private final boolean isOpenTestToast = true;
     Toolbar toolbar;
     ActionBar actionBar;

    /**
     * 用来测试用的吐司
     * @param msg
     */
    public void showTestToast(String msg)
    {
        if (isOpenTestToast)
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 项目真实需要用到的吐司方法
     * @param msg
     */
    public void showToast(String msg)
    {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initToolBar()
    {
        //初始化我们的toolbar
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        //设置支持toolbar
        setSupportActionBar(toolbar);
        //支持图标导航
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    //v7包的toolbar回调此方法
    @Override
    public boolean onSupportNavigateUp() {
        //结束当前activity的生命
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     *不带参数的跳转
     * @param clazz 需要跳到哪个界面
     */
    public void myStartActivity(Class clazz)
    {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
    /**
     *带参数的跳转
     * @param clazz 需要跳到哪个界面
     * @param  bundle 携带的数据包
     */
    public void myStartActivity(Class clazz,Bundle bundle)
    {
        Intent intent = new Intent(this,clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
