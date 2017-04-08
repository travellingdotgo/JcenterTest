package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 1/17/17.
 */
public class CameraRecord implements SurfaceHolder.Callback {
    public final static String TAG = "CameraRecord";

    public static final int BACK_CAMERA = 0;
    public static final int FRONT_CAMERA = 1;

    private int mCameraId=0;
    private int prevWidth,prevHeight;

    private boolean debug = BuildConfig.DEBUG;

    private SurfaceHolder holder;
    private Camera camera;
    private Camera.PreviewCallback previewCallback;

    private Context mContext;
    private int mDisplayDegree;
    public int mCameraDisplayOrientation;

    HandlerThread handlerThread;

    public interface OpenCallback{
        public void onCallback(int result);
    }

    public CameraRecord(int display_degree, SurfaceView surface) {
        mDisplayDegree = display_degree;
        holder = surface.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        Log.i(TAG, "constructor. mDisplayDegree="+mDisplayDegree);
    }

    public void asyncopenCamera(final int cameraId, final int preview_width, final int preview_height, final Camera.PreviewCallback _previewCallback, final OpenCallback openCallback ) {
        handlerThread = new HandlerThread("Thread-Camera-"+cameraId);
        handlerThread.start();

        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    openCamera(cameraId, preview_width, preview_height, _previewCallback);
                    openCallback.onCallback(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void openCamera(int wichCamera, int preview_width,int preview_height, Camera.PreviewCallback _previewCallback )  throws Exception {
        prevWidth=preview_width;
        prevHeight=preview_height;
        previewCallback = _previewCallback;

        if (wichCamera>FRONT_CAMERA || wichCamera<BACK_CAMERA){
            Log.e(TAG, "wichCamera invalid");
            return;
        }

        mCameraId = wichCamera;
        Log.i(TAG, "openCamera cameraId="+mCameraId);

        camera = Camera.open(mCameraId);

        if (camera == null){
            Log.e(TAG, "openCamera camera is null");
            return;
        }

        else{
            Log.i(TAG, "openCamera camera success");
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(mCameraId, cameraInfo);
            Log.i(TAG, "camera: facing=" + cameraInfo.facing + " orientation=" + cameraInfo.orientation  );

            Camera.Parameters parameters = camera.getParameters();
            if (debug){
                List<Camera.Size> lists = parameters.getSupportedPictureSizes();
                for (Camera.Size size: lists){
                    Log.v(TAG, "size: " + size.width + "*" + size.height);
                }
            }

            parameters.setPreviewSize(prevWidth, prevHeight);
            camera.setParameters(parameters);
        }

    }

    private void start() throws IOException {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged");

        if (camera == null){
            Log.e(TAG, "camera is null");
            return ;
        }

        if( previewCallback==null ){
            Log.e(TAG, "previewCallback==null");
            return ;
        }

        try {
            Camera.Parameters parameters = camera.getParameters();
            if(parameters==null){
                Log.e(TAG, "getParameters null");
                return;
            }
            int bufferSize = prevWidth*prevHeight * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
            camera.addCallbackBuffer(new byte[bufferSize]);
            Log.i(TAG, "addCallbackBuffer");
            camera.setPreviewCallbackWithBuffer(previewCallback);

            camera.startPreview();
            camera.setPreviewDisplay(holder);

            mCameraDisplayOrientation = CameraUtil.getSuitableCameraDisplayOrientation(mDisplayDegree,mCameraId);
            camera.setDisplayOrientation(mCameraDisplayOrientation);

            if(debug){
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
        Log.i(TAG, "surfaceDestroyed");

        if(camera!=null) {
            Log.i(TAG, "in surfaceDestroyed(), camera.stopPreview");
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;

            handlerThread.quit();
        }else{
            Log.i(TAG, "in surfaceDestroyed(), camera==null");
        }
    }


}
