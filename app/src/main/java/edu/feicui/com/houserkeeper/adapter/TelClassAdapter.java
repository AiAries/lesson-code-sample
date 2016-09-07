package edu.feicui.com.houserkeeper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.com.houserkeeper.R;
import edu.feicui.com.houserkeeper.entity.TelClassList;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class TelClassAdapter extends MyBaseAdapter<TelClassList>{

    public TelClassAdapter(@NonNull ArrayList<TelClassList> data, Context context) {
        super(data,context);
    }

    @Override
    public View MyGetView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_main_tel_type, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(getData().get(position).getName());
        return view;
    }
}
