package com.bewant2be.doit.jcentertest;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Looper;
import android.os.SystemClock;
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
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();

        Intent intent = new Intent(MainActivity.this, NetworkMonitorIntentService.class);
        startService(intent);
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
                finish();

                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });


        Button btnWeb = (Button)findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                startActivity(intent);
            }
        });


        Button btnWebjs = (Button)findViewById(R.id.btnWebjs);
        btnWebjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Intent intent = new Intent(MainActivity.this, WebjsActivity.class);
                startActivity(intent);
            }
        });

    }
}
