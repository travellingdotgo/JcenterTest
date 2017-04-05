package com.bewant2be.doit.utilslib;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by haidong.chen on 1/17/17.
 */
public class CameraRecord implements SurfaceHolder.Callback {
    public final static String TAG = "CameraRecord";

    public static final int BACK_CAMERA = 1;
    public static final int FRONT_CAMERA = 2;

    private int id=0;
    private int prevWidth,prevHeight;

    private boolean debug = false;

    private SurfaceHolder holder;
    private Camera camera;
    private Camera.PreviewCallback previewCallback;

    public CameraRecord(SurfaceView surface) {
        holder = surface.getHolder();
        //holder.setFixedSize(300, 300);
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        Log.i(TAG, "constructor");
    }

    private void initPara( Camera camera ){
        Camera.Parameters parameters = camera.getParameters();
        if (debug){
            List<Camera.Size> lists = parameters.getSupportedPictureSizes();
            for (Camera.Size size: lists){
                Log.v(TAG, "size: " + size.width + "*" + size.height);
            }
        }

        parameters.setPreviewSize(prevWidth, prevHeight);
        camera.setParameters(parameters);
        //camera.setDisplayOrientation(90);
    }


    public void openCamera(int wichCamera, int preview_width,int preview_height, Camera.PreviewCallback _previewCallback )  throws Exception {
        prevWidth=preview_width;
        prevHeight=preview_height;
        previewCallback = _previewCallback;

        id = (wichCamera==BACK_CAMERA)?0:1;

        camera = Camera.open(id);

        if (camera == null)
            Log.e(TAG, "openCamera camera is null");
        else{
            Log.i(TAG, "openCamera camera success");
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(id, cameraInfo);
            Log.i(TAG, "camera: " + cameraInfo.facing + " " + cameraInfo.orientation  );
            camera.setDisplayOrientation(0);// 0.rk  90.nexus 6P
            initPara(camera);
        }

    }

    private void start() throws IOException {
        Log.i(TAG, "start()");

        if(camera!=null) {
            if( previewCallback!=null ){
                int bufferSize = prevWidth*prevHeight * ImageFormat.getBitsPerPixel(camera.getParameters().getPreviewFormat()) / 8;
                camera.addCallbackBuffer(new byte[bufferSize]);
                Log.i(TAG, "addCallbackBuffer");
                camera.setPreviewCallbackWithBuffer( previewCallback );
            }
            camera.startPreview();
            camera.setPreviewDisplay(holder);
        }
    }

    public void stop() {
        Log.i(TAG, "stop()");

        if(camera!=null) {
            camera.stopPreview();
        }
    }

    public void release() {
        if(camera!=null) {
            camera.release();
        }
        camera = null;
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged");

        if (camera == null){
            Log.e(TAG, "camera is null");
            return ;
        }

        try {
            start();
            Camera.Parameters parameters = camera.getParameters();
            if(parameters!=null){
                Camera.Size size = parameters.getPreviewSize();
                Log.i(TAG, "parameters.getPreviewSize: " + size.width + "*" + size.height);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("CameraRecord", "surfaceDestroyed");
        stop();
    //    release();
    }
}