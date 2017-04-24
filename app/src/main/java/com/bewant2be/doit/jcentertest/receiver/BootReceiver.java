package com.bewant2be.doit.jcentertest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bewant2be.doit.jcentertest.R;
import com.bewant2be.doit.utilslib.DebugTool;
import com.bewant2be.doit.utilslib.NetUtil;
import com.bewant2be.doit.utilslib.ToastUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 4/24/17.
 */
public class BootReceiver extends BroadcastReceiver {
    public static final String TAG = BootReceiver.class.getSimpleName();
    Context mContext;

    @Override
    public void onReceive(final Context context, Intent intent) {
        mContext = context;
        ToastUtil.toastComptible(context, "BootReceiver" + intent.getAction());

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                String s = NetUtil.getCurrentNetWorkState(context) + ": " + NetUtil.getLocalIPAddress();
                DebugTool.makeFloatTextview(context, s);
            }
        };
        timer.schedule(timerTask, 15*1000);
    }


}
