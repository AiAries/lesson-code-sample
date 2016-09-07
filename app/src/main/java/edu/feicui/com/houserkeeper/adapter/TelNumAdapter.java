package edu.feicui.com.houserkeeper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.feicui.com.houserkeeper.R;
import edu.feicui.com.houserkeeper.entity.TelNumInfo;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class TelNumAdapter extends MyBaseAdapter<TelNumInfo> {


    public TelNumAdapter(@NonNull ArrayList<TelNumInfo> data, @NonNull Context context) {
        super(data, context);
    }

    @Override
    public View MyGetView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_main_tel_num, null);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);

        tv_name.setText(getData().get(position).getName());
        tv_num.setText(getData().get(position).getTelNum()+"");
        return view;
    }
}
