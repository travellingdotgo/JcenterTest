package com.bewant2be.doit.jcentertest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup;

import com.bewant2be.doit.jcentertest.sqlite.SqliteActivity;
import com.bewant2be.doit.utilslib.DeviceUtil;
import com.bewant2be.doit.utilslib.DiagnoseUtil;
import com.bewant2be.doit.utilslib.PackageUtil;
import com.bewant2be.doit.utilslib.SystemUtil;
import com.bewant2be.doit.utilslib.ThreadUtil;
import com.bewant2be.doit.utilslib.ToastUtil;
import com.bewant2be.doit.utilslib.service.NetworkMonitorIntentService;


import com.bewant2be.doit.utilslib.view.ScrollViewX;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    private final static String TAG = "MainActivity";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dynamicBuildViews();

        context = getApplicationContext();

        // show system info
        int sdk = Build.VERSION.SDK_INT;
        String s = "build sdk: " + sdk + "\n"
                +   "VERSION_CODE: " + BuildConfig.VERSION_CODE;
        Log.i(TAG, s);
        ToastUtil.toastComptible(getApplicationContext(), s);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestPermissions();
        }


        startNetCheck();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        new DiagnoseUtil().checkMainThreadBlock(
                new DiagnoseUtil.Callback() {
                    @Override
                    public void onResult(boolean b) {
                        if (b) {
                            Log.i(TAG, "MainThread working fine ");
                        } else {
                            Log.i(TAG, "MainThread block detected ");
                            String stack = ThreadUtil.getMainThreadStacktrace();
                            Log.e(TAG, "getMainThreadStacktrace:  \n" + stack);
                        }
                    }
                }
        );

        // block the main thread on purpose
        //SystemClock.sleep(1 * 1000);

        SystemUtil.getRunningAppProcessInfo(context);

        String s = DeviceUtil.getSerialNumber();
        ToastUtil.toastComptible(getApplicationContext(), s );

        // for debug
        if(false) {
            Intent intent = new Intent(getApplicationContext(), SqliteActivity.class);
            startActivity(intent);
        }
    }

    ScrollViewX scrollView;
    TextView textView;

    private void dynamicBuildViews(){   // ll_root{  ,scrollView{linearLayout} }
        LinearLayout ll_root = new LinearLayout(this);
        ll_root.setOrientation(LinearLayout.VERTICAL);

        // set Title
        textView = new TextView(this);
        textView.setText("Ver: " + PackageUtil.getVersionName(getApplicationContext()));
        textView.setTextSize(20.00f);
        textView.setBackgroundColor(Color.YELLOW);
        textView.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(     ViewGroup.LayoutParams.FILL_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT );
        textView.setLayoutParams(layoutParams);
        ll_root.addView(textView);

        mTimerHeader.schedule(timerTask,10,10);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        scrollView = new ScrollViewX(this);
        scrollView.setOnScrollListener(onScrollListener);

        List<String> classNames = PackageUtil.getClasses(getApplicationContext(), getPackageName());
        if (classNames != null) {
            for (final String className : classNames) {
                if(className.endsWith("Activity")){
                    int lastDot = className.lastIndexOf(".");
                    final String singlename = className.substring(lastDot+1, className.length());
                    if ("MainActivity".equals(singlename) ){
                            continue;
                    }

                    Log.i(TAG, singlename);
                    Button btn = new Button(this);
                    btn.setId(singlename.hashCode());
                    btn.setText(singlename);
                    btn.setAllCaps(false);
                    btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    btn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Class cls = Class.forName(className);
                                Intent intent = new Intent(getApplicationContext(), cls);
                                startActivity(intent);
                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }
                        }
                    });

                    linearLayout.addView(btn);
                }
            }
        }

        scrollView.addView(linearLayout);
        ll_root.addView(scrollView);
        setContentView(ll_root);
    }

    private void startNetCheck(){
        Intent intent = new Intent(MainActivity.this, NetworkMonitorIntentService.class);
        startService(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions(){
        Log.i(TAG, "requestPermissions");

        int hasPermission = checkSelfPermission(Manifest.permission.CAMERA);
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission not granted");
            ToastUtil.toastComptible(getApplicationContext(),"Permission not granted");
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            return;
        }else{
            Log.i(TAG, "Permission already granted");
        }
    }

    ScrollViewX.OnScrollListener onScrollListener = new ScrollViewX.OnScrollListener(){
        @Override
        public void onScrollChanged(int x, int y, int oldX, int oldY) {
            String s = "x:" + oldX + "->" + x + ", y:" + oldY + "->" + y;
            ToastUtil.toastComptible(getApplicationContext(), s);
        }

        @Override
        public void onScrollStopped() {
            if ( scrollView.isAtTop()) {
                bTop = true;
                ToastUtil.toastComptible(getApplicationContext(), "Stopped at top");
            }else{
                bTop = false;
                if (scrollView.isAtBottom()) {
                    ToastUtil.toastComptible(getApplicationContext(), "Stopped at bottom");
                } else {
                    ToastUtil.toastComptible(getApplicationContext(), "Stopped");
                }
            }
        }

        @Override
        public void onScrolling() {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimerHeader.cancel();
    }

    int header_height = 0;
    boolean bTop = true;
    final static int HEADER_MAX_HEIGHT = 180;
    final static int HEADER_STEP_HEIGHT = 4;


    Timer mTimerHeader = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if(bTop){ // if onTop, increase
                if (header_height < HEADER_MAX_HEIGHT) {
                    header_height+=HEADER_STEP_HEIGHT;
                    setHeaderHeight(header_height);
                    Log.d(TAG, "+ setHeaderHeight " + header_height);
                }
            }else{ // decrease
                if (header_height > 0) {
                    header_height-=HEADER_STEP_HEIGHT;
                    setHeaderHeight(header_height);
                    Log.d(TAG, "- setHeaderHeight " + header_height);
                }
            }
        }
    };

    private void setHeaderHeight(final int height){
        textView.post(new Runnable() {
            @Override
            public void run() {
                    ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                    layoutParams.height = height;
                    textView.setLayoutParams(layoutParams);
            }
        });
    }


}
