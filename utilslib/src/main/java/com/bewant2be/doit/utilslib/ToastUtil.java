package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;

/**
 * Created by user on 3/29/17.
 */
public class ToastUtil {
    private final static String TAG = "ToastUtil";

    private static Handler handler;

    private static ToastUtil defaultInstance;
    public static ToastUtil getDefault() {
        if (defaultInstance == null) {
            synchronized (ToastUtil.class) {
                if (defaultInstance == null) {
                    defaultInstance = new ToastUtil();
                    HandlerThread handlerThread = new HandlerThread("Thread-ToastUtil");
                    handlerThread.start();
                    handler = new Handler(handlerThread.getLooper());
                }
            } // endof sync
        } // endof first null check

        return defaultInstance;
    }

    public final static void toastComptible( final Context context, final String msg ){
        ToastUtil.getDefault().toast(context, msg);
    }


    private void toast( final Context applicationCtx, final String msg ){
        handler.post(new Runnable() {
            @Override
            public void run() {
                showToast(applicationCtx, msg);
            }
        });
    }


    private static Toast toast = null;
    private static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }

        toast.show();
    }
}
