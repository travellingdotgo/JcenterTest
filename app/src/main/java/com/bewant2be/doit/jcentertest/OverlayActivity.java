package com.bewant2be.doit.jcentertest;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bewant2be.doit.utilslib.ToastUtil;

import java.util.ArrayList;

public class OverlayActivity extends AppCompatActivity {
    private final static String TAG = "OverlayActivity";

    private WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    private static WindowManager windowManager;
    private static ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);

        windowManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            boolean bPermit = Settings.canDrawOverlays(getApplicationContext());
            if(bPermit){
                ToastUtil.toastComptible(getApplicationContext(), "canDrawOverlays: "+bPermit);
                createFloatView();
            }else {
                requestPermissions(null);
            }
        }
    }



    public final int REQ_CODE_REQUEST_SETTING = 0;
    public void requestPermissions(ArrayList<String> needPermissions) {
        //Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        //startActivity(myIntent);

        String packageName = getApplicationContext().getPackageName();
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName)  );
        startActivityForResult(intent, REQ_CODE_REQUEST_SETTING);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQ_CODE_REQUEST_SETTING:
                ToastUtil.toastComptible(getApplicationContext(), "resultCode: "+resultCode);
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void createFloatView()
    {
        if (imageView != null){
            windowManager.removeView(imageView);
        }

        imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.mipmap.ic_launcher);

        // TYPE_SYSTEM_ALERT 允许接收事件
        // TYPE_SYSTEM_OVERLAY 悬浮在系统上
        lp.type =  WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按,不设置这个flag的话，home页的划屏会有问题
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        //悬浮窗默认显示的位置
        lp.gravity = Gravity.LEFT|Gravity.TOP;
        //显示位置与指定位置的相对位置差
        lp.x = 0;
        lp.y = 0;
        //悬浮窗的宽高
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        lp.format = PixelFormat.TRANSPARENT;
        windowManager.addView(imageView,lp);

        //设置悬浮窗监听事件
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private float lastX; //上一次位置的X.Y坐标
            private float lastY;
            private float nowX;  //当前移动位置的X.Y坐标
            private float nowY;
            private float tranX; //悬浮窗移动位置的相对值
            private float tranY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        ret = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        nowX = event.getRawX();
                        nowY = event.getRawY();
                        tranX = nowX - lastX;
                        tranY = nowY - lastY;

                        // 移动悬浮窗
                        lp.x += tranX;
                        lp.y += tranY;

                        windowManager.updateViewLayout(imageView,lp);
                        //记录当前坐标作为下一次计算的上一次移动的位置坐标
                        lastX = nowX;
                        lastY = nowY;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return ret;
            }
        });
    }
}
