package com.bewant2be.doit.jcentertest;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bewant2be.doit.utilslib.ToastUtil;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VitamioActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener{
    private VideoView mVideoView;
    private MediaController mMediaController;
    private String PATH_URL1="http://baobab.wdjcdn.com/145076769089714.mp4";
    private String PATH_URL2="http://gslb.miaopai.com/stream/3D~8BM-7CZqjZscVBEYr5g__.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)){
            ToastUtil.toastComptible(getApplication(), "checkVitamioLibs failed" );
            return;
        }

        Vitamio.isInitialized(getApplicationContext());
        setContentView(R.layout.activity_vitamio);
        mVideoView=(VideoView)findViewById(R.id.surface_view); //设置播放地址 mVideoView.setVideoPath(PATH_URL1);//
        mVideoView.setVideoURI(Uri.parse(PATH_URL1)); //实例化控制器

        mMediaController=new MediaController(this); //绑定控制器

        mVideoView.setMediaController(mMediaController);
        //控制器显示9s后自动隐藏 mMediaController.show(9000); //设置播放画质 高画质
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        //mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH,0);
        //取得焦点 mVideoView.requestFocus(); //设置相关的监听
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnErrorListener(this);
        mVideoView.setOnCompletionListener(this);
    }

    //播放准备
    @Override
    public void onPrepared(MediaPlayer mp){
        // optional need Vitamio 4.0 mp.setPlaybackSpeed(1.0f);//开始播放//
        mVideoView.start();
    }

    //播放错误
    @Override
    public boolean onError(
        MediaPlayer mp,int what,int extra){
        return false;
    }

    //播放结束
    @Override
    public void onCompletion(MediaPlayer mp){
    // mVideoView.start();
    }
}
