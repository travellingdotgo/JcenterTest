package com.bewant2be.doit.jcentertest;

import android.content.Context;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.bewant2be.doit.utilslib.*;

import java.util.Timer;
import java.util.TimerTask;

public class CameraviewActivity extends AppCompatActivity {
    public final static String TAG = "CameraviewActivity";

    private  CameraView cameraView1,cameraView2;

    private boolean debug = BuildConfig.DEBUG;
    private boolean verbose = true;

    private Context mContext;
    private int display_degree;

    private int cnt1 = 0;
    private int cnt2 = 0;
    private Timer timer;

    Camera.PreviewCallback previewCallback1 = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            cnt1 ++;
            //ToastUtil.toastComptible( getApplicationContext(), "onPreviewFrame Thread: " + Thread.currentThread().getName());
            if( debug && verbose ) {
                Camera.Size size = camera.getParameters().getPreviewSize();
                int format = camera.getParameters().getPreviewFormat();
                AbbrLog.d(TAG, "onPreviewFrame getPreviewSize=" + size.width + "*" + size.height + "  getPreviewFormat="+format);
            }

            // add data process here


            // don't delete the code below
            camera.addCallbackBuffer(data);
        }
    };


    Camera.PreviewCallback previewCallback2 = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            cnt2 ++;
            //ToastUtil.toastComptible( getApplicationContext(), "onPreviewFrame Thread: " + Thread.currentThread().getName());
            if( debug && verbose ) {
                Camera.Size size = camera.getParameters().getPreviewSize();
                int format = camera.getParameters().getPreviewFormat();
                AbbrLog.d(TAG, "onPreviewFrame getPreviewSize=" + size.width + "*" + size.height + "  getPreviewFormat="+format);
            }

            // add data process here


            // don't delete the code below
            camera.addCallbackBuffer(data);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameraview);

        mContext = getApplicationContext();
        display_degree = DisplayUtil.getRotation(this);

        cameraView1 = (CameraView)findViewById(R.id.cameraView1);
        cameraView1.init(CameraUtil.getFrontCameraId(), display_degree, previewCallback1, 3);

        cameraView2 = (CameraView)findViewById(R.id.cameraView2);
        cameraView2.init(CameraUtil.getBackCameraId(), display_degree, previewCallback2, 3);

        ToastUtil.toastComptible(mContext, "display_degree=" + display_degree);
        Log.i(TAG, "display_degree = " + display_degree);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_surfaces);
        if(display_degree % 180==0){
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }else{
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        }

        final int seconds = 3;
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String s = "fps1=" + cnt1/seconds + ", fps2=" + cnt2/seconds;
                Log.i(TAG, s);
                ToastUtil.toastComptible(getApplicationContext(), s);
                cnt1 = 0;cnt2 = 0;
            }
        };
        timer.schedule( timerTask, seconds*1000, seconds*1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
