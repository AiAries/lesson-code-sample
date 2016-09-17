package com.feicui.edu.highpart.asyntask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class HttpAsyncTask extends AsyncTask<String,Integer,String>{
    private static final String TAG ="HttpAsyncTask";
    private LoadCallbackListener<String> listener;
    private Context context;

    public HttpAsyncTask(Context context) {
        this.context = context;
    }

    public LoadCallbackListener getListener() {
        return listener;
    }

    public void setListener(LoadCallbackListener<String> listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        //在后台运行的方法，在子线程中运行的方法，用来做耗时操作，通常网络下载
        Log.d(TAG, "doInBackground: ");
        return HttpUtil.getJsonString(params[0]);
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //当后台doInBackground方法执行完毕，回调这个方法,方法是在UI线程中执行
        if (s == null) {
            //从服务下载数据失败,状态消息
            if (listener != null) {
                listener.onFailed("网络异常...");
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
