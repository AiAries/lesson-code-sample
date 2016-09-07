package com.feicui.edu.intentjump_senddata;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void jump(View v) {
        //显示跳转
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        //携带参数传递
        Person p = new Person(18,"syq","sleep");
        intent.putExtra("syq", p);
        startActivity(intent);
//        intent.putExtra("id", 110);
//        intent.putExtra("name", "zhangsan");
    }
    public void jump1(View v) {
        //隐士跳转
        Intent intent = new Intent();
        //ACTION_CALL电话的过滤
        intent.setAction(Intent.ACTION_DIAL);/*"android.intent.action.MAIN"*/
        //default 默认就会自动添加
        //intent.addCategory(Intent.CATEGORY_DEFAULT);//"android.intent.category.LAUNCHER"
        String tel = "tel:110";
        String url = "http://www.baidu.com";
        intent.setData(Uri.parse(tel));
//        intent.putExtra(Intent.EXTRA_TEXT, "你有病，一个都要选择");

//        intent.setType("text/plain");

        Intent chooser = Intent.createChooser(intent, "一定会弹出选择对话框");
        //Pack
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (resolveInfos.size()>0) {
            for (ResolveInfo resolveInfo : resolveInfos) {
                CharSequence charSequence = resolveInfo.loadLabel(packageManager);
                Toast.makeText(MainActivity.this, ""+charSequence, Toast.LENGTH_SHORT).show();
            }
            this.startActivity(chooser);
        }
    }
}
