package com.bewant2be.doit.jcentertest;

import android.Manifest;
import android.annotation.TargetApi;
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
import com.bewant2be.doit.utilslib.ToastUtil;
import com.bewant2be.doit.utilslib.service.NetworkMonitorIntentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                            Log.i(TAG, "MainThread working fine");
                        } else {
                            Log.i(TAG, "MainThread working not fine");
                        }
                    }
                }
        );
    }

    private void testToast(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;

                while( true ){
                    i++;
                    SystemClock.sleep( 1*1000 );
                    ToastUtil.toastComptible(getApplicationContext(), "a toast msg from bg i=" + i );
                }
            }
        }).start();
    }

    public void initUi(){
        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        //btnCamera.callOnClick();

        Button btnWeb = (Button)findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                startActivity(intent);
            }
        });


        Button btnWebjs = (Button)findViewById(R.id.btnWebjs);
        btnWebjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebjsActivity.class);
                startActivity(intent);
            }
        });



        Button btnOverlay = (Button)findViewById(R.id.btnOverlay);
        btnOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OverlayActivity.class);
                startActivity(intent);
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
