package com.feicui.edu.common_dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void show() {
        super.show();

    }
}
