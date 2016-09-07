package edu.feicui.com.houserkeeper.ui;

import android.app.Application;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(MyApplication.this, "application", Toast.LENGTH_SHORT).show();
    }
}
