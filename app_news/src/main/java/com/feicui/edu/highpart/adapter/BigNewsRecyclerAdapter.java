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
import com.feicui.edu.highpart.bean.BigNews;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class BigNewsRecyclerAdapter extends RecyclerView.Adapter<BigNewsRecyclerAdapter.MyViewHolder> {

    private final Context context;
    private final List<BigNews> bigNewses;

    public BigNewsRecyclerAdapter(Context context, List<BigNews> bigNewses) {

        this.context = context;
        this.bigNewses = bigNewses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //确定展示条目的布局
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_list_pic, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //给itemView绑定数据
        BigNews bigNews = bigNewses.get(position);
        Glide.with(context)
                .load(bigNews.getImage()).centerCrop()
                .animate(R.anim.my_scale).into(holder.iv);

        holder.tvIntro.setText(bigNews.getIntroduct());
    }

    @Override
    public int getItemCount() {
        return bigNewses == null ? 0 : bigNewses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv)
        ImageView iv;
        @Bind(R.id.tv_intro)
        TextView tvIntro;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
