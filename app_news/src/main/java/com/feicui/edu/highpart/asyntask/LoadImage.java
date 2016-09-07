package com.feicui.edu.highpart.asyntask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class LoadImage {

    /**
     * 软引用 存内存 只要不关闭该程序 一直存放
     */
//	private static Map<String,SoftReference<Bitmap>> softReferences=new HashMap<String, SoftReference<Bitmap>>();
    private static LruCache<String, Bitmap> cache=new LruCache<String, Bitmap>(1024*1024*3);
    private int mStartLoadLimit = 0;
    private int mStopLoadLimit = 0;
    private Context context;

    Object obj = new Object();
    /**
     * 自定义回调接口 传递 图片 和图片路径
     * @author Administrator
     *
     */
    public interface ImageLoadListener{
        void imageLoadOk(Bitmap bitmap, int position);
    }

    /**
     * 构造方法赋值
     * @param context
     */
    public LoadImage(Context context) {
        super();
        this.context = context;
    }

    /**
     * 保存图片到缓存路径中
     * @param url     图片的原路径 网络 http://aa/t.jpg
     * @param bitmap  来自网络得到图片
     */
    public void saveCacheFile(String url,Bitmap bitmap){
        //http://aa/t.jpg  获取文件名字
        String name=url.substring(url.lastIndexOf("/")+1);
        //返回的路径目录应用程序缓存文件
        File cacheDir=context.getCacheDir();
        Log.d("vivi","缓存路径是"+cacheDir);
        if(!cacheDir.exists()){
            cacheDir.mkdir();
        }
        //建立输出流
        OutputStream outStream=null;
        try {
            outStream=new FileOutputStream(new File(cacheDir,name));
            //存图片到文件
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                if(outStream!=null){
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * 从缓存文件中获取图片
     * @param url 图片路径  com.app.azy.news.cache/xxx.png
     * @return  缓存文件中的图片
     */
    private Bitmap getBitmapFromCache(String url){
        String name=url.substring(url.lastIndexOf("/")+1);
        //获取当前包下的缓存文件路径
        File cacheDir=context.getCacheDir();
        //得到该文件夹下所有文件
        File[] files=cacheDir.listFiles();
        if(files==null){
            return null;
        }
        //图片文件
        File bitFile=null;
        //如有名字和传入的文件名一致的则找到图片
        for (File file : files) {
            if(file.getName().equals(name)){
                bitFile=file;
                break;}
        }
        //如果没有找到，返回空
        if(bitFile==null){
            return null;
        }
        /**
         * 把找的文件 转换为bitmap
         */
        Bitmap bitmap=BitmapFactory.decodeFile(bitFile.getAbsolutePath());
        return bitmap;
    }

    /**
     * 异步加载网络图片
     * @param url 图片在网络中的路径
     *
     */
    private void getBitmapAsync( ImageLoadListener listener , String url , int position){

        //在此处判断当前加载的

        //自定义的异步加载类
        ImageAsyncTask imageAsyncTask=new ImageAsyncTask( listener);
        //执行加载
        imageAsyncTask.execute(url , position + "");


    }

    //判断是否允许加载图片标记为
    private  boolean isLock = false;
    public void lock(){
        isLock = true;
    }
    public void unLock(){
        isLock = false;
    }




    /**
     * 最终调用的方法:先查看内存中有没有，再看缓存文件中 有没有，最后只能向网络请求图片
     * @param url 图片路径
     * @return  图片
     */
    public synchronized void getBitmap(ImageLoadListener listener, int position , String url){


        if(isLock){  //锁住，无法请求
            Log.d("vivi", "警报!!!!===程序锁住，无法请求图片");
            return;
        }


        if(url==null || url.length()<=0){
            return ;
        }

        //先看内存中
        Bitmap	bitmap = cache.get(url);
        if(bitmap !=null){
            Log.d("vivi","得到1内存的图片");
            listener.imageLoadOk(bitmap, position);
            return ;
        }
        //是不是缓存文件的
        bitmap=getBitmapFromCache(url);
        if(bitmap!=null){
            Log.d("vivi","得到2.缓存文件图片");
            listener.imageLoadOk(bitmap, position);
            return ;
        }
        getBitmapAsync(listener,  url , position);
    }




    /**
     * 异步加载类
     * 1.Url 处理的网络路径
     * 2.无 Void  当加载一条时 传递的类型
     * 3.返回的加载后的数据：Bitmap
     */
    private class ImageAsyncTask extends AsyncTask<String, Void, Bitmap>{

        private int position ;
        private String imageUrl;
        ImageLoadListener listener ;
        public ImageAsyncTask(ImageLoadListener listener ){
            this.listener = listener;
        }

        //执行之前 ui线程
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        //后台运行  新线程的代码 不能修改ui
        @Override
        protected Bitmap doInBackground(String... params) {
            //单条加载更新ui
            //publishProgress(values);
            imageUrl=params[0];
            position = Integer.parseInt(params[1]);
            Bitmap bitmap=null;
            try {
                //建立网络连接
                URL url=new URL(imageUrl);
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                //得到输入字节流
                InputStream is=conn.getInputStream();
                //得到图片
                bitmap=BitmapFactory.decodeStream(is);
                Log.d("vivi", "后台请求图片文件--position-"+position +"--url="+url);

                //存入软引用中
                cache.put(imageUrl, bitmap);
                //缓存文件
                saveCacheFile(imageUrl,bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        //如果 新线程中执行了 publishProgress(values);
        //就会执行 此方法 实现一条一条的更新ui
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        //doInBackground 执行return
        //后马上执行 ui线程 并把结果传递给此方法 execute(url)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if(result!=null){
                //线程结束后返回图片和路径
                Log.d("vivi", "onPostExecute--position-"+position +"--url="+imageUrl+"--result="+result);
                listener.imageLoadOk(result, position);
            }
        }

    }

}
