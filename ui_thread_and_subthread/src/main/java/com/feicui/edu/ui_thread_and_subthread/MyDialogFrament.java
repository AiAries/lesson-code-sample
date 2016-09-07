package com.feicui.edu.ui_thread_and_subthread;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class MyDialogFrament extends DialogFragment {


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setTitle("开始下载中");
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setCanceledOnTouchOutside(true);
        return progressDialog;
    }

}
