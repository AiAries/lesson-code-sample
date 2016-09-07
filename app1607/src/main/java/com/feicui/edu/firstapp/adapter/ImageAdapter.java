package com.feicui.edu.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class ImageAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private Context context;
    private int[] data;
    private final int length;

    public ImageAdapter(Context context, int[] data) {

        this.context = context;
        this.data = data;
        length = data.length;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return length*length;
    }

    @Override
    public Object getItem(int position) {
        return data[0];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(120,150);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);
        imageView.setImageResource(
                data[position%length]
        );
        return imageView;
    }
}
