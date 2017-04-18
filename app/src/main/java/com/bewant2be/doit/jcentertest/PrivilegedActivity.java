package com.bewant2be.doit.jcentertest;

import android.content.Context;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class PrivilegedActivity extends AppCompatActivity {
    private final static String TAG = "PrivilegedActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privileged);

        try{
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            pm.reboot("");
        }catch (Exception e){
            Log.e(TAG, e.toString() );
        }

    }
}
