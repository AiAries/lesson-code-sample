package com.feicui.edu.viewpage_fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class MyFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = new TextView(this.getContext());
        Bundle arguments = getArguments();
        int  i = arguments.getInt("i");
        view.setText("fragment"+i);
        Random r = new Random();
//        view.setBackgroundColor(Color.rgb(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
        view.setBackgroundColor(Color.WHITE);
        return view;
    }
}
