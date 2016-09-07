package edu.feicui.com.houserkeeper.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class MyAssetManager {


    private static final String TAG = "MyAssetManager";

    public static File  copyAssetsFileToSDFile(Context context,String from,String into)
    {
        //读取assets中的文件 AssetManager
        AssetManager assets = context.getAssets();
        File file = null;
        try {
            //db/commonnum.db是相对assets目录的相对路径
            InputStream stream = assets.open(/*db/commonnum.db*/from);
            //把这数据存入到Sd卡中
            //指定数据存放到sdcard中那个文件夹
            File filesDir = context.getFilesDir();//data/data/包名/files
            file = new File(filesDir,into/*"commonnum.db"*/);
            LogUtil.logD(TAG,file.toString());
            //如果文件存在，就直接返回
            if (file.exists())
                return file;
            //Environment.get
            readAssetDBDataToFile(stream,file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     *
     * @param stream
     * @param file
     */
    private static void readAssetDBDataToFile(InputStream stream, File file) {

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
