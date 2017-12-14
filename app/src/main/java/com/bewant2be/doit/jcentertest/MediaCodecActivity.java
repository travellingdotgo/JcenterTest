package com.bewant2be.doit.jcentertest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.bewant2be.doit.jcentertest.multimedia.VideoDecoderThread;
import com.bewant2be.doit.utilslib.ToastUtil;

public class MediaCodecActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private VideoDecoderThread mVideoDecoder;

    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/sintel.mp4";
    //private static final String FILE_PATH = "rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SurfaceView surfaceView = new SurfaceView(MediaCodecActivity.this);
        surfaceView.getHolder().addCallback(this);
        setContentView(surfaceView);

        Toast.makeText(getApplicationContext(), "Path: " + FILE_PATH, Toast.LENGTH_LONG).show();

        mVideoDecoder = new VideoDecoderThread();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,    int height) {
        if (mVideoDecoder != null) {
            if (mVideoDecoder.init(holder.getSurface(), FILE_PATH)) {
                mVideoDecoder.start();
            } else {
                mVideoDecoder = null;
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mVideoDecoder != null) {
            mVideoDecoder.close();
        }
    }
}