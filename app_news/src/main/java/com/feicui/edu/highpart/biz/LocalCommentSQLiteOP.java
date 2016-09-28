package com.feicui.edu.highpart.biz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class LocalCommentSQLiteOP extends SQLiteOpenHelper {

    //数据库的版本号
    public static final int DATABASE_VERSION = 1;
    //数据库名
    public static final String DATABASE_NAME = "local_comment.db";

    private static final String TAG = "LocalCommentSQLiteOP";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";


    public LocalCommentSQLiteOP(
            Context context,
            String name,//要创建数据库的名称
            SQLiteDatabase.CursorFactory factory,//游标工厂，用来生产cursor，不给用默认
            int version//创建数据库的版本号
    ) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //当数据库第一被创建的时候，才会回调此方法
        db.execSQL("CREATE TABLE " + NewsContract.LocalCommentEntry.TABLE_NAME +
                " (" +
                NewsContract.LocalCommentEntry._ID + " INTEGER PRIMARY KEY," +
                NewsContract.LocalCommentEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                NewsContract.LocalCommentEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                NewsContract.LocalCommentEntry.COLUMN_NAME_PORTRAIT + TEXT_TYPE + COMMA_SEP +
                NewsContract.LocalCommentEntry.COLUMN_NAME_STAMP + TEXT_TYPE + COMMA_SEP +
                NewsContract.LocalCommentEntry.COLUMN_NAME_UID + TEXT_TYPE +
                ")"
        );
        Log.d(TAG, "onCreate: table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //newVersion 大于 oldVersion 当数据库版本更新的时候，回调此方法
        Log.d(TAG, "onCreate: ");
    }
}
