package com.feicui.edu.mediaplayer_and_service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class LocalService extends Service {
    private static final String TAG = "LocalService";
    private NotificationManager mNM;
    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.local_service_started;
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }
    @Override
    public void onCreate() {
        //当服务第一次被创建的时候调用，只调用一次
        Log.d(TAG,"oncreate");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand Received start id " + startId + ": " + intent);
        stopSelf();//自己结束自己的生命
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);
        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }
    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();
    /**
     * Show a notification while this service is running.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, MainActivity.class),
                0);
        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)  // the status icon
                .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(getText(R.string.app_name))  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();
        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }
}
