package com.bewant2be.doit.jcentertest;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private VideoView videoView1;
    private String PATH_URL =  "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
                                // this .264 also supported, by lucent. "rtsp://192.168.1.3:8554/1.264";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        //初始化
        initVideoView();
    }
    private void initVideoView() {
        //设置屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        videoView1 = (VideoView) findViewById(R.id.videoView1);
        //网络地址
        videoView1.setVideoURI(Uri.parse(PATH_URL));
        //开始播放
        videoView1.start();
        //设置相关的监听
        videoView1.setOnPreparedListener(this);
        videoView1.setOnErrorListener(this);
        videoView1.setOnCompletionListener(this);
    }

    //播放错误
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
    //播放准备
    @Override
    public void onPrepared(MediaPlayer mp) {
    }
    //播放结束
    @Override
    public void onCompletion(MediaPlayer mp) {
    }
}