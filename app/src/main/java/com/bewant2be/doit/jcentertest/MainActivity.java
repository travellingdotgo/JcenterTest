package com.bewant2be.doit.jcentertest;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bewant2be.doit.utilslib.CameraRecord;
import com.bewant2be.doit.utilslib.ToastUtil;
import com.bewant2be.doit.utilslib.service.NetworkMonitorIntentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    SurfaceView surfaceView1;
    CameraRecord cameraRecord1;


    Camera.PreviewCallback previewCallback0 = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            //       Log.d(TAG, "previewCallback0 onPreviewFrame " );

            Camera.Size size = camera.getParameters().getPreviewSize();
            int format = camera.getParameters().getPreviewFormat();

            camera.addCallbackBuffer(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, NetworkMonitorIntentService.class);
        startService(intent);


        surfaceView1 = (SurfaceView)findViewById(R.id.surfaceview1);
        cameraRecord1 = new CameraRecord(surfaceView1);
        initViewSize(surfaceView1);


        try{
            cameraRecord1.openCamera(CameraRecord.BACK_CAMERA, Config.width,Config.height, previewCallback0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    private void initViewSize( final View view ){
        LinearLayout.LayoutParams params =  (LinearLayout.LayoutParams)view.getLayoutParams();
        params.width=Config.width/2;
        params.height=Config.height/2;
        view.setLayoutParams(params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        testToast();
    }

    private void testToast(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;

                while( true ){
                    i++;
                    SystemClock.sleep( 1*1000 );
                    ToastUtil.toastComptible(getApplicationContext(), "a toast msg from bg i=" + i );
                }
            }
        }).start();
    }
}
