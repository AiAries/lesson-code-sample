package com.feicui.edu.highpart.asyntask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.feicui.edu.highpart.util.HttpUtil;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class LoadImgAsyncTask extends AsyncTask<String,Integer,Bitmap>{
    private static final String TAG ="HttpAsyncTask";
    private LoadCallbackListener<Bitmap> listener;

    public LoadCallbackListener getListener() {
        return listener;
    }

    public void setListener(LoadCallbackListener<Bitmap> listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        //在后台运行的方法，在子线程中运行的方法，用来做耗时操作，通常网络下载
        Log.d(TAG, "doInBackground: ");
        return HttpUtil.getBitmap(params[0]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //方法是在UI线程中执行
        //通常呢，在这是弹出进度加载框
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //方法是在UI线程中执行
        //更新进度的操作
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        super.onPostExecute(s);
        //当后台doInBackground方法执行完毕，回调这个方法,方法是在UI线程中执行
        if (s == null) {
            //从服务下载数据失败,状态消息
            if (listener != null) {
                listener.onFailed(null);
                Log.d(TAG, "onPostExecute: 从服务下载数据失败");
            }
        } else {
            //从服务器下载成功
            if (listener != null) {
                listener.onSuccess(s);
            }
        }
        Log.d(TAG, "onPostExecute: "+s);
    }
}
