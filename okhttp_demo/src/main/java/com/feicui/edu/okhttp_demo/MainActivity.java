package com.feicui.edu.okhttp_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient client;
    String url  = "http://www.3dmgame.com/UploadFiles/201212/Medium_20121217143325603.jpg";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
        client = new OkHttpClient();

        new LoadImgAsyncTask()
        .execute();

    }
    InputStream run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().byteStream();
    }
    class LoadImgAsyncTask extends AsyncTask<String,Integer,Bitmap> {
        private static final String TAG ="HttpAsyncTask";

        @Override
        protected Bitmap doInBackground(String... params) {
            //在后台运行的方法，在子线程中运行的方法，用来做耗时操作，通常网络下载
            Log.d(TAG, "doInBackground: ");
            try {
                InputStream run = run(url);
                return BitmapFactory.decodeStream(run);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            //当后台doInBackground方法执行完毕，回调这个方法,方法是在UI线程中执行
            if (s == null) {
                //从服务下载数据失败,状态消息
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            } else {
                iv.setImageBitmap(s);
            }
            Log.d(TAG, "onPostExecute: "+s);
        }
    }


}
