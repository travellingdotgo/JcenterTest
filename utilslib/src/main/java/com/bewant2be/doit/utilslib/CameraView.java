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
import android.os.Process;

/**
 * Created by user on 4/12/17.
 */
@NotProguard
public class CameraView extends SurfaceView {

    public final static String TAG = "CameraView";

    public static final int BACK_CAMERA = 0;
    public static final int FRONT_CAMERA = 1;


    public final static int DEFAULT_DISPLAY_WIDTH = 480;
    public final static int DEFAULT_DISPLAY_HEIGHT = 270;

    public final static int DEFAULT_PRIVIEW_WIDTH = 1920;
    public final static int DEFAULT_PRIVIEW_HEIGHT = 1080;

    private int mPreviewWidth = 0;
    private int mPreviewHeight = 0;
    private int mDisplayWidth = 0;
    private int mDisplayHeight = 0;
    private int mExtraBuffers = 0;

    private int mCameraId=-1;

    private SurfaceHolder holder;
    private Camera camera;
    private Camera.PreviewCallback previewCallback;

    private int mDisplayDegree;
    public int mCameraDisplayOrientation;

    private SurfaceHolder surfaceHolder;

    private Path mPath = new Path();
    private Paint mPaint = new Paint();

    @NotProguard
    public interface OpenCallback{
        public void onCallback(int result);
    }

    HandlerThread handlerThread;

    public void init( int cameraId, int display_degree, Camera.PreviewCallback  _previewCallback, final int extraBuffers ){
        mCameraId = cameraId;
        mDisplayDegree = display_degree;
        previewCallback = _previewCallback;
        mExtraBuffers = extraBuffers;
    }

    private int mPriority = 0;
    public void setPriority(int priority){
        mPriority=priority;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        String s = "onMeasure. mDisplayDegree:  " + mDisplayDegree + "  ";
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {
                Log.i(TAG, s+"MeasureSpec.UNSPECIFIED");
                break;
            }
            case MeasureSpec.AT_MOST: {
                Log.i(TAG, s+"MeasureSpec.AT_MOST");
                break;
            }
            case MeasureSpec.EXACTLY: {
                Log.i(TAG, s+"MeasureSpec.EXACTLY");
                break;
            }
            default:
                Log.i(TAG, s+"MeasureSpec unkown ");
        }

        int widthSpec,heightSpec;
        if ( mDisplayDegree % 180==0 ){ // 0 180
            widthSpec = MeasureSpec.makeMeasureSpec(mDisplayHeight,mode);
            heightSpec = MeasureSpec.makeMeasureSpec(mDisplayWidth,mode);
        }else{              // 90 270
            widthSpec = MeasureSpec.makeMeasureSpec(mDisplayWidth,mode);
            heightSpec = MeasureSpec.makeMeasureSpec(mDisplayHeight,mode);
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
                    if(mPriority!=0){
                        //Thread.currentThread().setPriority(mPriority);
                        Process.setThreadPriority(mPriority);
                    }
                    openCamera(cameraId, preview_width, preview_height, _previewCallback);
                    semaphore.release();
                    int priority = Process.getThreadPriority((int)Thread.currentThread().getId());
                    Log.i(TAG, "priority: " + priority);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });// end of post

        try{ semaphore.acquire();  semaphore.release(); }catch (Exception e){ }
    }


    private void openCamera(int wichCamera, int preview_width,int preview_height, Camera.PreviewCallback _previewCallback )  throws Exception {
        Log.i(TAG, "openCamera ");
        mPreviewWidth=preview_width;
        mPreviewHeight=preview_height;
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

            parameters.setPreviewSize(mPreviewWidth, mPreviewHeight);
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
            mPreviewWidth = typedArray.getInteger(R.styleable.CameraView_previewwidth, DEFAULT_PRIVIEW_WIDTH);
            mPreviewHeight = typedArray.getInteger(R.styleable.CameraView_previewheight, DEFAULT_PRIVIEW_HEIGHT);
            String s = "TypedArray: mPreviewWidth="+mPreviewWidth + ", mPreviewHeight="+mPreviewHeight;

            mDisplayWidth = typedArray.getInteger(R.styleable.CameraView_displaywidth, DEFAULT_DISPLAY_WIDTH);
            mDisplayHeight = typedArray.getInteger(R.styleable.CameraView_displayheight, DEFAULT_DISPLAY_HEIGHT);
            s += "mDisplayWidth="+mDisplayWidth + ", mDisplayHeight="+mDisplayHeight;

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
            initCamera(mCameraId, mPreviewWidth, mPreviewHeight, previewCallback != null ? previewCallback : null, null);
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
                int bufferSize = mPreviewWidth*mPreviewHeight * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
                camera.addCallbackBuffer(new byte[bufferSize]);
                Log.i(TAG, "addCallbackBuffer");
                camera.setPreviewCallbackWithBuffer(previewCallback);
                Log.i(TAG, "mExtraBuffers=" + mExtraBuffers);
                for (int i=0;i<mExtraBuffers;i++){
                    camera.addCallbackBuffer(new byte[bufferSize]);
                }

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
            } else {
                Log.i(TAG, "in surfaceDestroyed(), camera==null");
            }

        }
    };

}
