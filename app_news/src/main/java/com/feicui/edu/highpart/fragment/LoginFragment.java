package com.feicui.edu.highpart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.edu.highpart.R;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class LoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_login, null);
        view.findViewById(R.id.register).setOnClickListener(

                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_login, new RegisterFragment()).commit();

            }
        });
        return view;
    }
}
