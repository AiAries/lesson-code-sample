package com.feicui.edu.readdbdata;

import android.app.ListActivity;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        //读取assets中的文件 AssetManager
        AssetManager assets = this.getAssets();
        File file = null;
        try {
            //db/commonnum.db是相对assets目录的相对路径
            InputStream stream = assets.open("db/commonnum.db");
            //把这数据存入到Sd卡中
            //指定数据存放到sdcard中那个文件夹
            File filesDir = getFilesDir();//data/data/包名/files
            file = new File(filesDir,"commonnum.db");
            //Environment.get
            readAssetDBDataToFile(stream,file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String>  names = new ArrayList<>();
        ArrayList<String>  idxs = new ArrayList<>();
        //把sdcard中的后缀为db数据文件读取出来,用到安卓自带的轻量级数据库SQLiteDatabase
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file, null);
        Cursor cursor = database.rawQuery("select * from classlist", null);
        while(cursor.moveToNext())
        {
            //classlist 这张表 有两个字段 name(string)  idx(int)
            String name = cursor.getString(cursor.getColumnIndex("name"));
            //注意手机号长，超出int值的范围
            long idx = cursor.getLong(cursor.getColumnIndex("idx"));
            names.add(name);
//            Toast.makeText(this,idx+"",Toast.LENGTH_LONG).show();
            idxs.add(idx+"");
        }
        cursor.close();

        //将上面的数据展示到listview中
        ListView listView = getListView();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                idxs
        );
        listView.setAdapter(adapter);

    }

    /**
     *
     * @param stream
     * @param file
     */
    private void readAssetDBDataToFile(InputStream stream, File file) {

        BufferedInputStream buffIn = null;
        BufferedOutputStream buffOut = null;

        try {
            buffIn = new BufferedInputStream(stream);
            buffOut = new BufferedOutputStream(new FileOutputStream(file));

            int temp = 0;
            while((temp = buffIn.read())!=-1)
            {
                buffOut.write(temp);
            }

        } catch (IOException e) {
            throw new RuntimeException("文件失败");
        }
        finally {
            if (buffIn!=null)
                try {
                    buffIn.close();
                } catch (IOException e) {
                    throw new RuntimeException("读取流关闭失败");
                }
            if (buffOut!=null)
                try {
                    buffOut.close();
                } catch (IOException e) {
                    throw new RuntimeException("写入流关闭失败");
                }
        }


    }
}
