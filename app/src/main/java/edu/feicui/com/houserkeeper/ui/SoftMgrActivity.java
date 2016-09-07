package edu.feicui.com.houserkeeper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import edu.feicui.com.houserkeeper.R;
import edu.feicui.com.houserkeeper.biz.MemoryManager;

public class SoftMgrActivity extends BaseActivity implements View.OnClickListener {

    public static final int  USER_SOFT = 0;
    public static final int  SYSTEM_SOFT = 1;
    public static final int  ALL_SOFT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_mgr);
        initToolBar();
        initView();
    }

    @Override
    public void initView() {
        findViewById(R.id.btn_all_soft).setOnClickListener(this);
        findViewById(R.id.btn_user_soft).setOnClickListener(this);
        findViewById(R.id.btn_sys_soft).setOnClickListener(this);
        ProgressBar pb_phone_extral_space = (ProgressBar) findViewById(R.id.pb_phone_extral_space);
        ProgressBar pb_phone_internal_space = (ProgressBar) findViewById(R.id.pb_phone_internal_space);
        //获取外部sd卡存储空间的总值
        long outSDCardSize = MemoryManager.getPhoneOutSDCardSize(this);
        //给progressBar设置最大值
        pb_phone_extral_space.setMax((int) outSDCardSize);
        //获取手机外部sd卡可以用存储空间  byte
        long outSDCardFreeSize = MemoryManager.getPhoneOutSDCardFreeSize(this);
        //内置Sdcard 使用掉的内存
        int useSize = (int) (outSDCardSize-outSDCardFreeSize);
        pb_phone_extral_space.setProgress(useSize);

        String totalSize = Formatter.formatFileSize(this, outSDCardSize);
        String use = Formatter.formatFileSize(this, useSize);
        TextView tv_out = (TextView) findViewById(R.id.tv_out);
        tv_out.setText(use+"/"+totalSize);


//        //获取内置sd卡的数据
//        long selfSDCardSize = MemoryManager.getPhoneSelfSDCardSize();
//        long selfSDCardFreeSize = MemoryManager.getPhoneSelfSDCardFreeSize();
        //机身的总存储空间
        long phoneSelfTotalSize =  getPhoneSelfTotalSize();
        //手机已经使用掉的存储空间
        long phoneSelfUsedSize =  getPhoneSelfUsedSize();

        pb_phone_internal_space.setMax((int) phoneSelfTotalSize);
        int progress = (int) phoneSelfUsedSize;
        pb_phone_internal_space.setProgress(progress);

        String interSdSize = Formatter.formatFileSize(this, phoneSelfTotalSize);
        String interSdUseSize = Formatter.formatFileSize(this, phoneSelfUsedSize);
        TextView tv_internal = (TextView) findViewById(R.id.tv_internal);
        tv_internal.setText(interSdUseSize+"/"+interSdSize);

    }

    private long getPhoneSelfTotalSize() {
        File rootDirectory = Environment.getRootDirectory();//system目录
        File dataDirectory = Environment.getDataDirectory();//assest  db 文件
        File downloadCacheDirectory = Environment.getDownloadCacheDirectory();//下载缓存目录
        return rootDirectory.getTotalSpace()+dataDirectory.getTotalSpace()+downloadCacheDirectory.getTotalSpace();
    }
    private long getPhoneSelfUsedSize() {
        File rootDirectory = Environment.getRootDirectory();//system目录
        File dataDirectory = Environment.getDataDirectory();//assest  db 文件
        File downloadCacheDirectory = Environment.getDownloadCacheDirectory();//下载缓存目录
        return rootDirectory.getUsableSpace()+dataDirectory.getUsableSpace()+downloadCacheDirectory.getUsableSpace();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.btn_all_soft:
                intent.setClass(this,ShowSoftActivity.class);
                intent.putExtra("flag",ALL_SOFT);
                break;
            case R.id.btn_user_soft:
                intent.setClass(this,ShowSoftActivity.class);
                intent.putExtra("flag",USER_SOFT);
                break;
            case R.id.btn_sys_soft:
                intent.setClass(this,ShowSoftActivity.class);
                intent.putExtra("flag",SYSTEM_SOFT);
                break;
        }
        startActivity(intent);
    }
}
