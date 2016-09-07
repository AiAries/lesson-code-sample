package com.feicui.edu.broadcast_receiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BatteryReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动态注册广播接受者
        //2.创建广播对象
        receiver = new BatteryReceiver();

        //3.注册广播接受者
        /*Intent.ACTION_BATTERY_CHANGED  电量发生改变 不能进行静态注册，只能通过Context.registerReceiver()来注册
        You cannot receive this through components declared in manifests,
        only by explicitly registering for it with Context.registerReceiver().
         */
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        filter.addAction();
        this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //4.取消广播注册
        this.unregisterReceiver(receiver);
    }
}
