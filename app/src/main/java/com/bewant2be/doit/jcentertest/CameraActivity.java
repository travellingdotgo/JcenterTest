package com.bewant2be.doit.jcentertest;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
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

    public final static int width = 1920;
    public final static int height = 1080;

    SurfaceView surfaceView1;
    CameraRecord cameraRecord1;

    Camera.PreviewCallback previewCallback0 = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Size size = camera.getParameters().getPreviewSize();
            Log.d(TAG, "previewCallback0 onPreviewFrame " + size.width + "  " + size.height );
            int format = camera.getParameters().getPreviewFormat();

            camera.addCallbackBuffer(data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initUi();

        surfaceView1 = (SurfaceView)findViewById(R.id.surfaceview1);
        cameraRecord1 = new CameraRecord(surfaceView1);
        //initViewSize(surfaceView1);

        try{
            cameraRecord1.openCamera(CameraRecord.BACK_CAMERA, width,height, previewCallback0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    private void initViewSize( final View view ){
        LinearLayout.LayoutParams params =  (LinearLayout.LayoutParams)view.getLayoutParams();
        params.width=width/2;
        params.height=height/2;
        view.setLayoutParams(params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    public void initUi(){

    }
}
