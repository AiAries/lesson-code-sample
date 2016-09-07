package com.example.jsonarry.mdedia_player_and_service;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener
{


    private static final String TAG = "MainActivity";
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click1(View v)
    {
//        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.rongma);
//        if (!mediaPlayer.isPlaying())
//        {
//            //mediaPlayer.setLooping(true);
//            mediaPlayer.start();
//        }
    Intent intent = new Intent(this,MusicService.class);
    startService(intent);
    }

    public void click2(View v) throws IOException
    {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // query failed, handle error.
            Toast.makeText(MainActivity.this, "qurey failed", Toast.LENGTH_SHORT).show();
        } else if (!cursor.moveToFirst()) {
            // no media on the device
            Toast.makeText(MainActivity.this, "no media", Toast.LENGTH_SHORT).show();
        } else {
            int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
//            do {
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                // ...process entry...
//            } while (cursor.moveToNext());
            Toast.makeText(MainActivity.this, cursor.getCount()+""+thisTitle, Toast.LENGTH_SHORT).show();
            Uri contentUri = ContentUris.withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, thisId);
            MediaPlayer  mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(getApplicationContext(), contentUri);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }
        assert cursor != null;
        cursor.close();
    }

    public void click3(View v)
    {
        try
        {
            mediaPlayer = new MediaPlayer();
//            MediaFormat mediaFormat = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AC3
//                ,100,6
//            );
//            mediaFormat.
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            String path1 = getFilesDir().getPath();
            Toast.makeText(MainActivity.this, path1, Toast.LENGTH_SHORT).show();
            //播放本地文件
//            String path = path1+"/rongma.mp3";
            //播放网络图片 ,用自己的tomcat服务器模拟
            String path = "http://192.168.1.12:8080/m.mp3";
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
//            mediaPlayer.start();
        }

        catch (IOException e)
        {
            Toast.makeText(MainActivity.this, "path有错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        if (!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }
}
