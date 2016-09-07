package com.feicui.edu.highpart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feicui.edu.highpart.bean.News;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHold> {

    public void setNewses(ArrayList<News> newses) {
        this.newses = newses;
    }
    private ArrayList<News> newses;

    public ArrayList<News> getNewses() {
        return newses;
    }

    private final Context context;

    public MyRecycleViewAdapter(ArrayList<News> newses, Context context) {
        this.newses = newses;
        this.context = context;
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_news, null);
        return new MyViewHold(view);
    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHold holder, final int position) {
        final News news = newses.get(position);
        String iconPath = news.getIcon();
        iconPath = iconPath.replace("localhost", "192.168.2.35");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", news.getLink());
                context.startActivity(intent);
            }
        });
        Glide.with(context)
                .load(iconPath)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(holder.imageView);

//        //异步加载图片
//        final LoadImgAsyncTask task = new LoadImgAsyncTask();
//        Log.d("MyRecycleViewAdapter",iconPath);
//        task.setListener(new LoadCallbackListener<Bitmap>() {
//            @Override
//            public void onSuccess(Bitmap bitmap) {
//                holder.imageView.setImageBitmap(bitmap);
//                notifyItemChanged(position);
//                task.cancel(true);
//            }
//
//            @Override
//            public void onFailed(Bitmap bitmap) {
//                Toast.makeText(context, "下载图片失败", Toast.LENGTH_SHORT).show();
//                task.cancel(true);
//            }
//        });
//        task.execute(iconPath);

        holder.tv1.setText(news.getTitle());
        holder.tv2.setText(news.getSummary());

    }

    class MyViewHold extends RecyclerView.ViewHolder {

         TextView tv1;
         TextView tv2;
        ImageView imageView;

        public MyViewHold(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView1);
            tv1 = (TextView) itemView.findViewById(R.id.textView1);
            tv2 = (TextView) itemView.findViewById(R.id.textView2);
        }
    }
}
