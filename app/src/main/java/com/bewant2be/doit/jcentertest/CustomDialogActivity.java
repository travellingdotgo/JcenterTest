package com.bewant2be.doit.jcentertest;

import android.app.Dialog;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bewant2be.doit.jcentertest.uiutil.CustomDialog;

import java.util.Timer;
import java.util.TimerTask;

public class CustomDialogActivity extends AppCompatActivity {

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        showDialog();

        Timer timer  = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                postCloseDialogToMainThread();
            }
        };
        timer.schedule(timerTask,5000);
    }

    private void postCloseDialogToMainThread(){
        findViewById(R.id.activity_custom_dialog).post(new Runnable() {
            @Override
            public void run() {
                closeDialog();
            }
        });
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = CustomDialog.createLoadingDialog(this, "正在加载中...");
            dialog.show();
        }
    }

    private void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
