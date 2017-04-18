package com.bewant2be.doit.jcentertest;

import android.content.Context;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.bewant2be.doit.utilslib.*;

public class CameraviewActivity extends AppCompatActivity {
    public final static String TAG = "CameraviewActivity";

    private  CameraView cameraView1,cameraView2;

    private boolean debug = BuildConfig.DEBUG;
    private boolean verbose = false;

    private Context mContext;
    int display_degree;

    Camera.PreviewCallback previewCallback1 = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            ToastUtil.toastComptible( getApplicationContext(), "onPreviewFrame Thread: " + Thread.currentThread().getName());
            if( debug && verbose ) {
                Camera.Size size = camera.getParameters().getPreviewSize();
                Log.d(TAG, "onPreviewFrame getPreviewSize:  " + size.width + "  " + size.height);
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
        setContentView(R.layout.activity_cameraview);

        mContext = getApplicationContext();
        display_degree = DisplayUtil.getRotation(this);

        cameraView1 = (CameraView)findViewById(R.id.cameraView1);
        cameraView1.init(CameraView.FRONT_CAMERA, display_degree, previewCallback1);

        cameraView2 = (CameraView)findViewById(R.id.cameraView2);
        cameraView2.init(CameraView.BACK_CAMERA, display_degree, previewCallback1);

        ToastUtil.toastComptible(mContext, "" + display_degree);
        Log.i(TAG, "display_degree = " + display_degree);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_surfaces);
        if(display_degree % 180==0){
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }else{
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        }
    }


}
