package com.feicui.edu.highpart;

import android.app.Application;
import android.util.Log;

import com.feicui.edu.highpart.biz.LocalCommentSQLiteOP;
import com.feicui.edu.highpart.biz.LocalNewsSQLiteOP;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class MyApplication extends Application {

    public LocalNewsSQLiteOP localNewsSQLiteOP;
    public LocalCommentSQLiteOP commentSQLiteOP;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApplication", "onCreate: ");
        //创建数据库帮组类
        localNewsSQLiteOP = new LocalNewsSQLiteOP(
                this,
                LocalNewsSQLiteOP.DATABASE_NAME,
                null,
                LocalNewsSQLiteOP.DATABASE_VERSION
        );
        commentSQLiteOP = new LocalCommentSQLiteOP(
                this,
                LocalCommentSQLiteOP.DATABASE_NAME,
                null,
                LocalCommentSQLiteOP.DATABASE_VERSION
        );

    }
}
