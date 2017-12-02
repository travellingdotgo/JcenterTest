package com.bewant2be.doit.jcentertest;

import android.support.v7.app.AppCompatActivity;
import java.io.IOException;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class RtspMediaplayerActivity extends AppCompatActivity implements SurfaceHolder.Callback, OnPreparedListener{

    MediaPlayer mediaPlayer;
    SurfaceHolder surfaceHolder;
    SurfaceView playerSurfaceView;
    String videoSrc = "rtsp://admin:hu123456@192.168.1.66/h264/ch1/main/av_stream";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rtspmediaplayer);
        playerSurfaceView = (SurfaceView)findViewById(R.id.playersurface);

        surfaceHolder = playerSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setDataSource(videoSrc);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }

}
