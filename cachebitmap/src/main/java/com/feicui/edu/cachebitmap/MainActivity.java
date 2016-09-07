package com.feicui.edu.cachebitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Bitmap> datas;
    int[] pics = new int[]{
            R.mipmap.a0, R.mipmap.a1, R.mipmap.a2, R.mipmap.a3, R.mipmap.a4,
            R.mipmap.a5, R.mipmap.a6, R.mipmap.a7, R.mipmap.a8, R.mipmap.a9,
            R.mipmap.a10, R.mipmap.a11, R.mipmap.a12, R.mipmap.a13, R.mipmap.a14,
            R.mipmap.a15, R.mipmap.a16, R.mipmap.a17, R.mipmap.a18, R.mipmap.a19,
            R.mipmap.a20, R.mipmap.a21, R.mipmap.a22, R.mipmap.a23, R.mipmap.a24,R.mipmap.a25,
    };
    private LayoutInflater inflater;
    private long totalMemory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        float density = getResources().getDisplayMetrics().density;
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        Log.d("onCreate", "onCreate: "+ widthPixels/density+"density");
        Toast.makeText(MainActivity.this, widthPixels/density+"density", Toast.LENGTH_SHORT).show();

        totalMemory = Runtime.getRuntime().totalMemory();
        Toast.makeText(this, Formatter.formatFileSize(this,totalMemory), Toast.LENGTH_SHORT).show();
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListView listView = (ListView) findViewById(R.id.list);

        initData();
//        SimpleAdapter adapter = new SimpleAdapter(
//                this,
//                datas,
//                R.layout.item_iamge,
//                new String[]{"key"},
//                new int[]{R.id.iv}
//        );

        listView.setAdapter(new MyAdapter());
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            //从res/mipmap中取图片，转化成Bitmap类型
            //Bitmap都是由BitmapFactory工厂类加载完成
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), pics[i]);
            datas.add(bitmap);
        }
    }

    class MyAdapter extends BaseAdapter {

        LruCache<Integer,Bitmap> lruCache = new LruCache<Integer,Bitmap>((int) (totalMemory/8))
        {
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                return value.getByteCount();
            }
        };

        public void putBitmapToLruCache(int id, Bitmap bitmap) {
            lruCache.put(id, bitmap);
        }
        public Bitmap getBitmapFromLruCache(int id) {
            return lruCache.get(id);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = inflater.inflate(R.layout.item_iamge, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);

           /* //先判断缓冲中是否有图片
            Bitmap bitmap = lruCache.get(pics[position]);
//            Bitmap bitmap = getBitmapFromLruCache(pics[position]);
            if (bitmap == null) {
                //模拟网上
                bitmap = datas.get(position);
                //缓冲中是没有这张图片，添加到缓存
                lruCache.put(pics[position], bitmap);
//                putBitmapToLruCache(pics[position], bitmap);
                Toast.makeText(MainActivity.this, "来源于网络", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "来源于缓存", Toast.LENGTH_SHORT).show();
            }*/

            Bitmap bitmap = null;
            //首先从sdcard读取图片，如果没有就从网上下载
            try {
                FileInputStream inputStream = MainActivity.this.openFileInput(pics[position] + "");
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, pics[position]+"文件不存在", Toast.LENGTH_SHORT).show();
            }

            if (bitmap == null) {
                //说明sdcard中没有缓存图片
                //模拟网上
                bitmap = datas.get(position);
                //将图片存放到Sdcard中
                OutputStream stream = null;
                try {
                    stream = MainActivity.this.openFileOutput(pics[position] + "", Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Toast.makeText(MainActivity.this, "来源于网络", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MainActivity.this, "来源于sdcard", Toast.LENGTH_SHORT).show();
            }

//            Bitmap bitmap = datas.get(position);
//            BitmapDrawable bd= new BitmapDrawable(MainActivity.this.getResources(), bitmap);
//            //因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
            iv.setImageBitmap(bitmap);
//            iv.setBackgroundDrawable(bd);
            return view;
        }
    }
}
