package com.feicui.edu.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        boolean equals = action.equals(Intent.ACTION_BOOT_COMPLETED);
        if (equals) {
            Intent intent1 = new Intent(context,MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
            Toast.makeText(context, "*********", Toast.LENGTH_SHORT).show();
        }
    }
}
