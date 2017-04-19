package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by user on 4/12/17.
 */
public class CameraView extends SurfaceView {

    public final static String TAG = "CameraView";

    public static final int BACK_CAMERA = 0;
    public static final int FRONT_CAMERA = 1;

    public final static int DEFAULT_CAMERA_WIDTH = 800;
    public final static int DEFAULT_CAMERA_HEIGHT = 600;

    private int mWidth = 0;
    private int mHeight = 0;

    private int mCameraId=-1;
    private int prevWidth,prevHeight;

    private SurfaceHolder holder;
    private Camera camera;
    private Camera.PreviewCallback previewCallback;

    private int mDisplayDegree;
    public int mCameraDisplayOrientation;

    private SurfaceHolder surfaceHolder;

    private Path mPath = new Path();
    private Paint mPaint = new Paint();

    public interface OpenCallback{
        public void onCallback(int result);
    }

    HandlerThread handlerThread;

    public void init( int cameraId, int display_degree, Camera.PreviewCallback  _previewCallback ){
        mCameraId = cameraId;
        mDisplayDegree = display_degree;
        previewCallback = _previewCallback;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure. mDisplayDegree:  " + mDisplayDegree);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {
                break;
            }
            case MeasureSpec.AT_MOST: {
                break;
            }
            case MeasureSpec.EXACTLY: {
                break;
            }
        }

        int widthSpec,heightSpec;
        if ( mDisplayDegree % 180==0 ){ // 0 180
            widthSpec = MeasureSpec.makeMeasureSpec(mHeight,mode);
            heightSpec = MeasureSpec.makeMeasureSpec(mWidth,mode);
        }else{              // 90 270
            widthSpec = MeasureSpec.makeMeasureSpec(mWidth,mode);
            heightSpec = MeasureSpec.makeMeasureSpec(mHeight,mode);
        }

        super.onMeasure(widthSpec, heightSpec);
    }

    private void initCamera(final int cameraId, final int preview_width, final int preview_height, final Camera.PreviewCallback _previewCallback, final OpenCallback openCallback ) {
        Log.i(TAG, "initCamera ");
        final Semaphore semaphore = new Semaphore(1);
        try{ semaphore.acquire(); }catch (Exception e){ }

        handlerThread = new HandlerThread("CameraView-"+cameraId);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    openCamera(cameraId, preview_width, preview_height, _previewCallback);
                    semaphore.release();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });// end of post

        try{ semaphore.acquire();  semaphore.release(); }catch (Exception e){ }
    }


    private void openCamera(int wichCamera, int preview_width,int preview_height, Camera.PreviewCallback _previewCallback )  throws Exception {
        Log.i(TAG, "openCamera ");
        prevWidth=preview_width;
        prevHeight=preview_height;
        previewCallback = _previewCallback;

        if (wichCamera>FRONT_CAMERA || wichCamera<BACK_CAMERA){
            Log.e(TAG, "wichCamera invalid");
            return;
        }

        mCameraId = wichCamera;
        Log.i(TAG, "openCamera cameraId=" + mCameraId);

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
            if (BuildConfig.DEBUG){
                List<Camera.Size> lists = parameters.getSupportedPictureSizes();
                for (Camera.Size size: lists){
                    Log.v(TAG, "size: " + size.width + "*" + size.height);
                }
            }

            parameters.setPreviewSize(prevWidth, prevHeight);
            camera.setParameters(parameters);
        }

    }

    private void initHolder(){
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(surfaceHolderCallback);
    }

    public CameraView(Context context) {
        super(context);
        Log.i(TAG, "CameraView(Context) ");

        initHolder();

    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "CameraView(Context,AttributeSet)");

        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CameraView);
            mWidth = typedArray.getInteger(R.styleable.CameraView_prevwidth, DEFAULT_CAMERA_WIDTH);
            mHeight = typedArray.getInteger(R.styleable.CameraView_prevheight, DEFAULT_CAMERA_HEIGHT);
            String s = "TypedArray: mWidth="+mWidth + ", mHeight="+mHeight;
            ToastUtil.toastComptible(getContext(), s);
            Log.i(TAG, s);
            initHolder();
            typedArray.recycle();
        }
        catch (Exception e){
            Log.e(TAG, "Exception: " + e.toString() );
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent ");
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback(){
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i(TAG, "surfaceCreated ");

            Log.i(TAG, "mCameraId=" + mCameraId);
            long start = System.currentTimeMillis();
            initCamera(mCameraId, DEFAULT_CAMERA_WIDTH, DEFAULT_CAMERA_HEIGHT, previewCallback != null ? previewCallback : null, null);
            long timeMillis = System.currentTimeMillis() - start;
            //ToastUtil.toastComptible(getContext(), "timeMillis: "+timeMillis);
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

                if(BuildConfig.DEBUG){
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
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "surfaceDestroyed ");

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
    };

}
