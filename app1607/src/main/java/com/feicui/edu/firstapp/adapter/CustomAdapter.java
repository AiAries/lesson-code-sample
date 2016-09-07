package com.feicui.edu.firstapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.edu.firstapp.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class CustomAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private final List<String> data;
    private final int resLayoutId;
    private Context context;

//    class MyDataNullException extends NullPointerException {
//        public MyDataNullException(String detailMessage) {
//            super(detailMessage);
//        }
//    }

    public CustomAdapter(List<String> data , int resLayoutId, Context context) {
        if (data==null) {
            throw new NullPointerException("data can not be null");
        }
        this.data = data;
        this.resLayoutId = resLayoutId;
        this.context = context;
        //将布局的id渲染成一个View
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Toast.makeText(context, "position:"+position, Toast.LENGTH_SHORT).show();
        return  createViewFromResource(mInflater, position, convertView, parent, resLayoutId);
    }

    private View createViewFromResource(LayoutInflater mInflater, int position, View convertView, ViewGroup parent, int resLayoutId) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(resLayoutId, parent, false);
        } else {
            v = convertView;
        }

        //给每个View绑定数据
        bindView(position, v);

        return v;
    }

    private void bindView(int position, View v) {
        TextView textView = (TextView) v.findViewById(R.id.textView);
        textView.setText(data.get(position));//position 从 0开始
    }
}
