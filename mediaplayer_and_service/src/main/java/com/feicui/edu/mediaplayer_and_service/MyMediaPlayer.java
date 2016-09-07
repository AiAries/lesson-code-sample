package com.feicui.edu.mediaplayer_and_service;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class MyMediaPlayer<T> {
    private MediaPlayer player;


    /**
     * 初始化播放的数据,和MediaPlayer对象
     * //播放assets中的文件
     * @param c 上下文
     * @param assetFileDescriptor 通过assets目录下的获取到的对象
     */
    public void initDataAndPlayer(Context c, AssetFileDescriptor assetFileDescriptor)
    {
        try {
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            //处于idle闲置状态
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(fileDescriptor);//初始化数据，进入初始化状态
        } catch (IOException e) {
            Toast.makeText(c, "数据初始化失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void play() {
        try {
            if (!player.isPlaying())
            {
                player.prepare();
                player.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pause() {
        if (player.isPlaying())
            player.pause();
    }
    public void stop()
    {
        player.stop();
    }

    public void release() {
        player.stop();
        //释放资源
        player.release();
    }
}
