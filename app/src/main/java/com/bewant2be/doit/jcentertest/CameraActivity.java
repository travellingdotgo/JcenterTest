package com.bewant2be.doit.jcentertest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bewant2be.doit.utilslib.CameraRecord;
import com.bewant2be.doit.utilslib.CameraRecord.OpenCallback;
import com.bewant2be.doit.utilslib.ToastUtil;
import com.bewant2be.doit.utilslib.DisplayUtil;
import com.bewant2be.doit.utilslib.BuildConfig;
import com.bewant2be.doit.utilslib.service.NetworkMonitorIntentService;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Semaphore;

public class CameraActivity extends AppCompatActivity {

    private final static String TAG = "CameraActivity";

    private boolean verbose = false;

    public final static int width = 640;
    public final static int height = 480;

    private Context mContext;
    int display_degree;

    private CameraRecord cameraRecord1,cameraRecord2;

    Camera.PreviewCallback previewCallback1 = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            ToastUtil.toastComptible(mContext, "onPreviewFrame Thread: " + Thread.currentThread().getName());
            if( BuildConfig.DEBUG && verbose ) {
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
        public void onPreviewFrame(final byte[] data, Camera camera) {
            ToastUtil.toastComptible(mContext, "onPreviewFrame Thread: " + Thread.currentThread().getName());
            if( BuildConfig.DEBUG && verbose ) {
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
        mContext = getApplicationContext();

        display_degree = DisplayUtil.getRotation(this);
        ToastUtil.toastComptible(mContext, "" + display_degree);
        Log.i(TAG, "display_degree = " + display_degree);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_surfaces);
        if(display_degree % 180==0){
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        }else{
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }

        initUi();
        initCamera();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initViewSize( final View view ){
        LinearLayout.LayoutParams params =  (LinearLayout.LayoutParams)view.getLayoutParams();

        if ( display_degree % 180==0 ){ // 0 180
            params.width=height;
            params.height=width;
        }else{              // 90 270
            params.width=width;
            params.height=height;
        }

        view.setLayoutParams(params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private void initUi(){

    }


    private void initCamera(){

        int count = Camera.getNumberOfCameras();
        ToastUtil.toastComptible(mContext, "getNumberOfCameras() = " + count);

        final Semaphore semaphore = new Semaphore(1);
        CameraRecord.OpenCallback openCallback = new CameraRecord.OpenCallback(){
            @Override
            public void onCallback(int result) {
                semaphore.release();
            }
        };

        try{
            Log.i(TAG, "waiting to open cameras 1");
            semaphore.acquire();
            final SurfaceView surfaceView1 = (SurfaceView)findViewById(R.id.surfaceview1);
            cameraRecord1 = new CameraRecord(display_degree,surfaceView1);
            initViewSize(surfaceView1);
            cameraRecord1.asyncopenCamera(CameraRecord.BACK_CAMERA, width, height, previewCallback1, openCallback);

            Log.i(TAG, "waiting to open cameras 2");
            semaphore.acquire();
            final SurfaceView surfaceView2 = (SurfaceView)findViewById(R.id.surfaceview2);
            cameraRecord2 = new CameraRecord(display_degree,surfaceView2);
            initViewSize(surfaceView2);
            cameraRecord2.asyncopenCamera(CameraRecord.FRONT_CAMERA, width, height, previewCallback2, openCallback);

            Log.i(TAG, "waiting for camera2 opening result");
            semaphore.acquire();
            semaphore.release();
        }catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }



}
