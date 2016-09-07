package com.example.jsonarry.mdedia_player_and_service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Aries on 2016/8/9.
 */
public class MusicService extends Service
{
    private static final String TAG = "MusicService";
    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "onStartCommand");
        play();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        //通过读取assets文件目录下的文件
        try
        {
            AssetManager assets = getAssets();
            AssetFileDescriptor assetFileDescriptor = assets.openFd("rongma.mp3");
            player = new MediaPlayer();
            player.setDataSource(
                    assetFileDescriptor.getFileDescriptor()
            );
            player.prepare();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.d(TAG,"rongma.mp3 read failed");
        }
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy: ");
        player.stop();
        player.release();
        super.onDestroy();
    }
    public void play()
    {
        if (!player.isPlaying())
            player.start();
    }
}
