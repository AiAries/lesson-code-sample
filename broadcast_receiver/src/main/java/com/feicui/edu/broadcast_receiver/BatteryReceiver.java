package com.feicui.edu.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
//1.自定义一个类继承自BroadcastReceiver，实现onReceive方法
public class BatteryReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //当接受到对应的广播时候，会回调此方法
        //context 上下文
        //intent  用来接受发送者传递过来的intent
        //这边做些逻辑处理
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            Bundle extras = intent.getExtras();
            int totalDianLiang = extras.getInt(BatteryManager.EXTRA_SCALE);//电池总量
            int currentDianLiang = extras.getInt(BatteryManager.EXTRA_LEVEL);//电池当前变量
            int temperature = extras.getInt(BatteryManager.EXTRA_TEMPERATURE);//获取电池的温度
            //把这些设置布局文件中....
        }
    }
}
