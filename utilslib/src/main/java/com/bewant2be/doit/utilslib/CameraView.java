package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by user on 4/12/17.
 */
public class CameraView extends SurfaceView {

    public final static String TAG = "CameraView";

    private SurfaceHolder surfaceHolder;
    private Path mPath = new Path();
    private Paint mPaint = new Paint();

    private void initPaint(){
        mPaint.setAntiAlias(false);//设置画笔的锯齿效果
        mPaint.setAlpha(1);//设置画笔的透明度
        mPaint.setColor(Color.RED);//设置画笔的颜色
        mPaint.setTextSize(30);
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔的风格（空心、实心）
        mPaint.setStrokeWidth(2);//设置空心边框的宽度
    }

    public CameraView(Context context) {
        super(context);
        Log.i(TAG, "CameraView(Context) ");
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(surfaceHolderCallback);

        initPaint();

    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "CameraView(Context,AttributeSet)");
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(surfaceHolderCallback);

        initPaint();
    }

    private void draw() {
        Log.i(TAG, "draw(Canvas) ");

        Canvas mCanvas = surfaceHolder.lockCanvas();//得到是上次的canvas

        try {
            mCanvas.drawColor(Color.WHITE);//进行清屏操作(如果没有清屏就会保持上次的操作)
            mCanvas.drawPath(mPath, mPaint);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mCanvas != null){
                surfaceHolder.unlockCanvasAndPost(mCanvas);//对画布的内容进行提交
            }
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

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                SystemClock.sleep(10);
                draw();
            }
        }
    };

    SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback(){
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i(TAG, "surfaceCreated ");
            new Thread(runnable).start();

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.i(TAG, "surfaceChanged ");

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "surfaceDestroyed ");

        }
    };

}
