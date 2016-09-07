package com.example.jsonarry.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int numMessages;
    boolean flag;
    private NotificationManager manager;
    private PendingIntent pi;
    int requestCode;
    private int notifyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     *对于版本较低的系统，Notification必须通过setContentIntent()方法,设置PendingIntent
     * 否则报错，contentIntent required：
     * @param view
     */
    public void update(View view) {
//        PendingIntent pendingIntent = PendingIntent.getActivity()
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Sets an ID for the notification, so it can be updated
        int notifyID = 119;
        android.support.v4.app.NotificationCompat.Builder mNotifyBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("New Message")
                        .setContentText("You've received new messages.")
                        .setSmallIcon(R.mipmap.ic_launcher);
//        numMessages = 0;
// Start of a loop that processes data and then notifies the user
//        ...
//        mNotifyBuilder.setContentText("currentText"+numMessages)
//                .setNumber(++numMessages);
        // Because the ID remains unchanged, the existing notification is
        // updated.
        mNotificationManager.notify(
                notifyID,
                mNotifyBuilder.build());
    }

    public void notify1(View view) {
//        //发送普通通知
//        //创建通知的构造者
//        android.support.v4.app.NotificationCompat.Builder builder =
//         new  android.support.v4.app.NotificationCompat.Builder(this);
//        //设置必要的内容
//        builder.setTicker("您有一条新消息")
//                .setContentTitle("奥运赛事")
//                .setContentText("中国得到九块金牌")
//                .setSmallIcon(R.mipmap.ic_launcher);
//
//        //设置震动属性和声音
//        //builder.setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND);
//
//        //生成通知对象
//        Notification notification = builder.build();
        //兼容低版本
        Notification notification = new Notification();
        RemoteViews views = new RemoteViews(
                getPackageName(),
                android.R.layout.simple_list_item_1
        );
        views.setTextViewText(android.R.id.text1, "我是内容");
        notification.tickerText = "fsf";
        notification.contentView = views;
        notification.icon = R.mipmap.ic_launcher;
        //兼容低版本

        //发送通知、
        //通知都需要有唯一的id值
        notifyId = 120;
        manager.notify(notifyId, notification);
    }

    public void notify2(View view) {
        android.support.v4.app.NotificationCompat.Builder builder =
                new android.support.v4.app.NotificationCompat.Builder(this);
        String number;
        if (flag) {
            number = "tel:" + "1868888888";
        } else {
            number = "tel:" + "110";
        }
        flag = !flag;
        //设置必要的内容
        builder.setTicker("您有一条新消息")
                .setContentTitle("未接电话")
                .setContentText(number)
                .setSmallIcon(R.mipmap.ic_launcher);

        //设置当点击通知的时候可以自动取消
        //builder.setAutoCancel(true);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(number));
        pi = PendingIntent.getActivity(
                this,
                requestCode++,//请求码
                intent,
                //PendingIntent.FLAG_NO_CREATE//不创建Pendingintent对象，返回null
                //PendingIntent.FLAG_IMMUTABLE//6.0出来的
                PendingIntent.FLAG_UPDATE_CURRENT//如果通知pendingintent存在带更新intent数据
                //PendingIntent.FLAG_CANCEL_CURRENT//如果通知pendingintent存在，替换之前的
                //PendingIntent.FLAG_ONE_SHOT//通知只对第一次点击有效
        );
//        try {
//            pi.send(this,0,intent);//等价于点击通知条目
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }
//        Intent intent = new Intent(this,Main2Activity.class);
        //PendingIntent.FLAG_NO_CREATE//不创建Pendingintent对象，返回null
// PendingIntent.FLAG_IMMUTABLE//6.0出来的
//PendingIntent.FLAG_UPDATE_CURRENT//如果通知pendingintent存在带更新intent数据
//
//PendingIntent.FLAG_ONE_SHOT//通知只对第一次点击有效

        Toast.makeText(MainActivity.this, pi.toString(), Toast.LENGTH_SHORT).show();
        builder.setContentIntent(pi);

        //生成通知对象
        Notification notification = builder.build();
        //发送通知
        //通知都需要有唯一的id值
        int notifyId = 10086;
        manager.notify(notifyId, notification);
    }

    public void cancel(View v) {
        //取消你发送指定id的通知
        manager.cancel(notifyId);
    }

    public void cancel_all(View v) {
        //取消所有你发送的通知
        manager.cancelAll();
    }
    /**
     * To set up a PendingIntent that starts a direct entry Activity, follow these steps:
     * 1.Define your application's Activity hierarchy in the manifest.
     *
         Add support for Android 4.0.3 and earlier. To do this, specify the parent of the Activity you're starting by adding a <meta-data> element as the child of the <activity>.

         For this element, set android:name="android.support.PARENT_ACTIVITY". Set android:value="<parent_activity_name>" where <parent_activity_name> is the value of android:name for the parent <activity> element. See the following XML for an example.
         Also add support for Android 4.1 and later. To do this, add the android:parentActivityName attribute to the <activity> element of the Activity you're starting.

     * 2.Create a back stack based on the Intent that starts the Activity:
     *
     * @param v
     */
    public void start_regular(View v) {
        Intent resultIntent = new Intent(this, Main2Activity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack
        stackBuilder.addParentStack(Main2Activity.class);
// Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
// Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //...
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(resultPendingIntent);
        builder.setTicker("您有一条新消息")
                .setContentTitle("未接电话")
                .setContentText("110")
                .setSmallIcon(R.mipmap.ic_launcher);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notifyId, builder.build());
    }

}
