package com.bewant2be.doit.jcentertest;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bewant2be.doit.utilslib.CameraRecord;
import com.bewant2be.doit.utilslib.DiagnoseUtil;
import com.bewant2be.doit.utilslib.ToastUtil;
import com.bewant2be.doit.utilslib.service.NetworkMonitorIntentService;

public class CameraActivity extends AppCompatActivity {

    private final static String TAG = "CameraActivity";

    private boolean debug = BuildConfig.DEBUG;
    private boolean verbose = false;

    public final static int width = 640;
    public final static int height = 480;

    Camera.PreviewCallback previewCallback1 = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            ToastUtil.toastComptible(getApplicationContext(), "onPreviewFrame Thread: " + Thread.currentThread().getName());
            if( debug && verbose ) {
                Camera.Size size = camera.getParameters().getPreviewSize();
                Log.d(TAG, "onPreviewFrame getPreviewSize:  " + size.width + "  " + size.height );
                int format = camera.getParameters().getPreviewFormat();
                Log.d(TAG, "onPreviewFrame getPreviewFormat: " + format );
            }

            // add data process here


            // don't delete the code below
            camera.addCallbackBuffer(data);
        }
    };


    Camera.PreviewCallback previewCallback2 = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if( debug && verbose ) {
                Camera.Size size = camera.getParameters().getPreviewSize();
                Log.d(TAG, "onPreviewFrame getPreviewSize:  " + size.width + "  " + size.height );
                int format = camera.getParameters().getPreviewFormat();
                Log.d(TAG, "onPreviewFrame getPreviewFormat: " + format );
            }

            // add data process here


            // don't delete the code below
            camera.addCallbackBuffer(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initUi();

        final Context context = getApplicationContext();

        int count = Camera.getNumberOfCameras();
        ToastUtil.toastComptible(getApplicationContext(), "getNumberOfCameras() = " + count);

        final SurfaceView surfaceView1 = (SurfaceView)findViewById(R.id.surfaceview1);
        final CameraRecord cameraRecord1 = new CameraRecord(surfaceView1);
        //final SurfaceView surfaceView2 = (SurfaceView)findViewById(R.id.surfaceview2);
        //final CameraRecord cameraRecord2 = new CameraRecord(surfaceView2);

        initViewSize(surfaceView1);
        //initViewSize(surfaceView2);

        HandlerThread handlerThread = new HandlerThread("camera");
        handlerThread.start();

        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    ToastUtil.toastComptible(context, "OpenCamera Thread: " + Thread.currentThread().getName());
                    cameraRecord1.openCamera(CameraRecord.FRONT_CAMERA, width, height, previewCallback1);
                    //cameraRecord2.openCamera(CameraRecord.BACK_CAMERA, width, height, previewCallback2);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

                SystemClock.sleep(1000);
    }

    private void initViewSize( final View view ){
        LinearLayout.LayoutParams params =  (LinearLayout.LayoutParams)view.getLayoutParams();
        params.width=width;
        params.height=height;
        view.setLayoutParams(params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    public void initUi(){

    }
}
