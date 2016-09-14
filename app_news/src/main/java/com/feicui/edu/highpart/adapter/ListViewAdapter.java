package com.feicui.edu.highpart.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.asyntask.LoadCallbackListener;
import com.feicui.edu.highpart.asyntask.LoadImage;
import com.feicui.edu.highpart.asyntask.LoadImgAsyncTask;
import com.feicui.edu.highpart.bean.News;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class ListViewAdapter extends BaseAdapter {

    private static final String TAG = "ListViewAdapter";
    private  ArrayList<News> newses;
    private  Context context;
    public final LoadImage loadImage;

    public ListViewAdapter(ArrayList<News> newses, Context context) {
        loadImage = new LoadImage(context);
        this.newses = newses;
        this.context = context;
    }



    @Override
    public int getCount() {
        return newses.size();
    }

    @Override
    public Object getItem(int position) {
        return newses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder v ;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_news, null);
            v = new MyViewHolder(convertView);
            convertView.setTag(v);
        } else {
            v = (MyViewHolder) convertView.getTag();
        }
        v.tv_title.setText(newses.get(position).getTitle());
        v.tv_text.setText(newses.get(position).getSummary());

        v.iv_list_image.setTag(position);
        String iconPath = newses.get(position).getIcon();
        iconPath = iconPath.replace("localhost", "192.168.2.35");
        loadImage.getBitmap(new LoadImage.ImageLoadListener() {
            @Override
            public void imageLoadOk(Bitmap bitmap, int position) {
                v.iv_list_image.setImageBitmap(bitmap);
            }
        }, position,iconPath);
//        asyncLoadImg(position,v.iv_list_image);

        return convertView;
    }

    class MyViewHolder{
        public ImageView iv_list_image;
        public TextView tv_title;
        public TextView tv_text;

        public MyViewHolder(View convertView) {
            iv_list_image = (ImageView) convertView.findViewById(R.id.imageView1);
            tv_text = (TextView) convertView.findViewById(R.id.textView1);
            tv_title = (TextView) convertView.findViewById(R.id.textView2);
        }
    }
    public void asyncLoadImg(final int position, final ImageView imageView) {
        String iconPath = newses.get(position).getIcon();
        iconPath = iconPath.replace("localhost", "192.168.2.35");
            final LoadImgAsyncTask task = new LoadImgAsyncTask();
            Log.d("MyRecycleViewAdapter",iconPath);
            task.setListener(new LoadCallbackListener<Bitmap>() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    notifyDataSetChanged();
                }

                @Override
                public void onFailed(Bitmap bitmap) {
                    Toast.makeText(context, "下载图片失败", Toast.LENGTH_SHORT).show();
                }
            });
            task.execute(iconPath);

    }

    public void setNewses(ArrayList<News> newses) {
        this.newses = newses;
    }
}
