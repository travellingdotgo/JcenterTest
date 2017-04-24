package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    public final static void toastView( final Context context, final String msg ){
        ToastUtil.getDefault().toastview(context, msg);
    }


    // - - - - - - - -- - - -- - - - - - -- - - - -- - - - - - -- - - - --

    private void toast( final Context applicationCtx, final String msg ){
        handler.post(new Runnable() {
            @Override
            public void run() {
                showToast(applicationCtx, msg);
            }
        });
    }

    private void toastview( final Context applicationCtx, final String msg ){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(applicationCtx, msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout toastView = (LinearLayout) toast.getView();
                ImageView imageCodeProject = new ImageView(applicationCtx);
                imageCodeProject.setImageResource(R.drawable.tips);
                toastView.addView(imageCodeProject, 0);
                toast.show();
            }
        });
    }

    private static Toast toast = null;
    private static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }

        toast.show();
    }
}
