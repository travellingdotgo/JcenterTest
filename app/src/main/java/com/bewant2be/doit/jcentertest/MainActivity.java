package com.bewant2be.doit.jcentertest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

import com.bewant2be.doit.utilslib.DeviceInfo;
import com.bewant2be.doit.utilslib.DiagnoseUtil;
import com.bewant2be.doit.utilslib.PackageUtil;
import com.bewant2be.doit.utilslib.ShellUtil;
import com.bewant2be.doit.utilslib.ThreadUtil;
import com.bewant2be.doit.utilslib.ToastUtil;
import com.bewant2be.doit.utilslib.service.NetworkMonitorIntentService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import dalvik.system.DexFile;

public class MainActivity extends AppCompatActivity{

    private final static String TAG = "MainActivity";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
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
    }

    private void dynamicBuildViews(){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ScrollView scrollView = new ScrollView(this);

        List<String> classNames = PackageUtil.getClasses(getApplicationContext(), "com.bewant2be.doit.jcentertest");
        if (classNames != null) {
            for (final String className : classNames) {
                if(className.endsWith("Activity")){
                    int lastDot = className.lastIndexOf(".");
                    final String singlename = className.substring(lastDot+1, className.length());
                    Log.i(TAG, singlename);
                    Button btn = new Button(this);
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
        setContentView(scrollView);
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
}
