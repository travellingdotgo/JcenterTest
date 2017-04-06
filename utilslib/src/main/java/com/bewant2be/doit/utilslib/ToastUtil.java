package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;
import android.os.Handler;

/**
 * Created by user on 3/29/17.
 */
public class ToastUtil {

    public final static void toastComptible( final Context applicationCtx, final String msg ){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText( applicationCtx, msg, Toast.LENGTH_LONG).show();
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
