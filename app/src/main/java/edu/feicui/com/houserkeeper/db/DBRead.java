package edu.feicui.com.houserkeeper.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;

import edu.feicui.com.houserkeeper.entity.TelClassList;
import edu.feicui.com.houserkeeper.entity.TelNumInfo;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class DBRead {

    /**
     * 从指定数据库文件读取数据
     * @param file
     * @return
     */
    public static ArrayList<TelClassList> readTelClassList(File file)
    {
        ArrayList<TelClassList> data = new ArrayList<>();
        //把sdcard中的后缀为db数据文件读取出来,用到安卓自带的轻量级数据库SQLiteDatabase
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file, null);
        Cursor cursor = database.rawQuery("select * from classlist", null);
        while(cursor.moveToNext())
        {
            //classlist 这张表 有两个字段 name(string)  idx(int)
            String name = cursor.getString(cursor.getColumnIndex("name"));
            //注意手机号长，超出int值的范围
            int idx = cursor.getInt(cursor.getColumnIndex("idx"));
            TelClassList tel = new TelClassList(name,idx);
            data.add(tel);
        }
        cursor.close();
        return data;
    }

    public static ArrayList<TelNumInfo> readTelNum(File file,int idx)
    {
        ArrayList<TelNumInfo> data = new ArrayList<>();
        //把sdcard中的后缀为db数据文件读取出来,用到安卓自带的轻量级数据库SQLiteDatabase
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file, null);
        Cursor cursor = database.rawQuery("select * from table"+idx, null);
        while(cursor.moveToNext())
        {
            //classlist 这张表 有两个字段 name(string)  idx(int)
            String name = cursor.getString(cursor.getColumnIndex("name"));
            //注意手机号长，超出int值的范围
            long num = cursor.getLong(cursor.getColumnIndex("number"));
            TelNumInfo tel = new TelNumInfo(name,num);
            data.add(tel);
        }
        cursor.close();
        return data;
    }
}
