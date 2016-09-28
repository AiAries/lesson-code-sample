package com.feicui.edu.highpart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feicui.edu.highpart.R;
import com.feicui.edu.highpart.bean.Comment;
import com.feicui.edu.highpart.common.LogUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class CommentRecycleViewAdapter extends RecyclerView.Adapter<CommentRecycleViewAdapter.MyViewHold> {

    private final LayoutInflater inflater;

    public void setCommentes(ArrayList<Comment> data) {
        this.data = data;
    }
    private ArrayList<Comment> data;

    public ArrayList<Comment> getCommentes() {
        return data;
    }

    private final Context context;

    public CommentRecycleViewAdapter(ArrayList<Comment> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycler_comment, parent,false);
        return new MyViewHold(view);
    }

    @Override
    public int getItemCount() {
        return data ==null?0: data.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHold holder, final int position) {

        Comment comment=data.get(position);
        LogUtil.d(LogUtil.TAG, "position--->"+position +"--- cid="+comment.getCid());
        holder.tv_comment.setText(comment.getContent());
        holder.tv_time.setText(comment.getStamp());
        Glide.with(context).load(comment.getPortrait())
                .centerCrop()//对bitmap像素缩放
                .placeholder(R.drawable.a3)//默认图片
                .crossFade()//动画效果
                .into(holder.iv_list_image);//把下载的图片放到imageview中
    }

    class MyViewHold extends RecyclerView.ViewHolder {
        public ImageView iv_list_image;
        public TextView tv_time;
        public TextView tv_comment;

        public MyViewHold(View view) {
            super(view);
            iv_list_image = (ImageView) view.findViewById(R.id.iv_header);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_comment = (TextView) view.findViewById(R.id.tv_content);
        }
    }
}
