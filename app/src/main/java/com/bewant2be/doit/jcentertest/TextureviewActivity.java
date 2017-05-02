package com.bewant2be.doit.jcentertest;

import android.graphics.SurfaceTexture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.widget.FrameLayout;

import com.bewant2be.doit.utilslib.CameraUtil;
import com.bewant2be.doit.utilslib.DisplayUtil;
import com.bewant2be.doit.utilslib.ToastUtil;

public class TextureviewActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    public final static String TAG = "TextureviewActivity";

    private TextureView myTexture;
    private Camera mCamera;
    private int cameraId = CameraInfo.CAMERA_FACING_BACK;

    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Log.d(TAG, "onPreviewFrame");
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textureview);

        myTexture = new TextureView(this);
        myTexture.setSurfaceTextureListener(this);
        setContentView(myTexture);
        //myTexture.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mCamera = Camera.open(cameraId);
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        myTexture.setLayoutParams(new FrameLayout.LayoutParams(previewSize.width, previewSize.height, Gravity.CENTER));
        try {
            mCamera.setPreviewTexture(surface);
            mCamera.setPreviewCallback(previewCallback);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mCamera.startPreview();
        myTexture.setAlpha(0.8f);
        int display_degree = DisplayUtil.getRotation(this);
        int cameraDisplayOrientation = CameraUtil.getSuitableCameraDisplayOrientation(display_degree, cameraId);
        String s = "display_degree="+display_degree + ", cameraDisplayOrientation="+cameraDisplayOrientation;
        ToastUtil.toastComptible(getApplicationContext(), s);
        Log.i(TAG, s);
        myTexture.setRotation(cameraDisplayOrientation);
    }
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // TODO Auto-generated method stub
    }
}
