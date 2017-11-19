package com.bewant2be.doit.jcentertest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class RtspActivity extends AppCompatActivity {

    VideoView videoView ;
    public final static String url = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov"; // works fine by lucent
                                    //rtsp://192.168.1.3/Phantom_4.webm 却不可用,说明mov格式支持, webm格式不支持？
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtsp);

        videoView = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse(url);

        videoView.setMediaController(new MediaController(this));

        videoView.setVideoURI(uri);

        //videoView.start();

        videoView.requestFocus();
        videoView.start();
    }
}
