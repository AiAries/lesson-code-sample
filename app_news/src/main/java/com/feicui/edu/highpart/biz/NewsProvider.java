package com.feicui.edu.highpart.biz;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by Aries on 2016/9/17.
 * encapsulate 封装内容
 *
 * content provider 就是为了数据共享，如果不要数据共享，就用sql lite database 数据库
 * 进行存储数据
 */
public class NewsProvider extends ContentProvider
{

    @Override
    public boolean onCreate()
    {
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        return null;
    }

    @Override
    public String getType(@NonNull Uri uri)
    {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values)
    {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }
}
