package com.bewant2be.doit.jcentertest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Looper;
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
import android.widget.Toast;

import com.bewant2be.doit.utilslib.CameraRecord;
import com.bewant2be.doit.utilslib.DiagnoseUtil;
import com.bewant2be.doit.utilslib.ShellUtil;
import com.bewant2be.doit.utilslib.ThreadUtil;
import com.bewant2be.doit.utilslib.ToastUtil;
import com.bewant2be.doit.utilslib.service.NetworkMonitorIntentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private final static String TAG = "MainActivity";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // show system info
        int sdk = Build.VERSION.SDK_INT;
        Log.i(TAG, "system build sdk: " + sdk);
        ToastUtil.toastComptible(getApplicationContext(), "build sdk: " + sdk);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestPermissions();
        }

        initUi();

        //startNetCheck();
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
        SystemClock.sleep(1 * 1000);
    }

    public void initUi(){
        ((Button)findViewById(R.id.btnCamera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        //btnCamera.callOnClick();

        ((Button)findViewById(R.id.btnWeb)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                startActivity(intent);
            }
        });


        ((Button)findViewById(R.id.btnWebjs)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebjsActivity.class);
                startActivity(intent);
            }
        });


        ((Button)findViewById(R.id.btnOverlay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OverlayActivity.class);
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.btnRecyclerview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerviewActivity.class);
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.btnCameraview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraviewActivity.class);
                startActivity(intent);
            }
        });


        //((Button)findViewById(R.id.btnCameraview)).callOnClick();

        ((Button)findViewById(R.id.btnShellUtil)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = ShellUtil.isRooted();
                ToastUtil.toastComptible(getApplicationContext(), "isRooted: " + b);

                if(b){
                    SystemClock.sleep(1000);

                    String cmd = "ifconfig eth0 down";
                    String str = ShellUtil.executeAsRootUnstable(cmd);
                    ToastUtil.toastComptible(getApplicationContext(), cmd + "  result: \n" + str);
                }
            }
        });
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
