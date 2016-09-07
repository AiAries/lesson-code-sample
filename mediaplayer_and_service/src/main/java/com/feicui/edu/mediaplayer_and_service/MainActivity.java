package com.feicui.edu.mediaplayer_and_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MyMediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd("door_of_moon.mp3");
            player =  new MyMediaPlayer();
            player.initDataAndPlayer(this,assetFileDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(View view) {

      player.play();
        //播放音乐或视频需要用到MediaPlayer这个类
//        MediaPlayer player = MediaPlayer.create(this,R.raw.m);
//        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        player.start();
        //读取assets中的文件
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    public void pause(View view) {
            player.pause();
    }
    public void stop(View view) {
        player.stop();
    }

    public void start_service(View view) {
      //四大组件，之间通讯需要intent
        Intent i = new Intent(this,LocalService.class);
        //启动服务,必须在清单文件manifest注册
        startService(i);//不能主动销毁服务
//        mIsBound = true;
//        this.bindService(i,mConnection, Context.BIND_AUTO_CREATE);
    }
    public void stop_service(View view) {
        //解绑服务
        if (mIsBound) {
            this.unbindService(mConnection);
            mIsBound = false;
        }
    }
    boolean mIsBound;
    private ServiceConnection mConnection = new ServiceConnection() {
        public LocalService mBoundService;

        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            mBoundService = ((LocalService.LocalBinder) service).getService();
            // Tell the user about this for our demo.
            Toast.makeText(getApplicationContext(), "服务链接",
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
            mBoundService = null;
            Toast.makeText(getApplicationContext(), "服务断开",
                    Toast.LENGTH_SHORT).show();
        }
    };

}
