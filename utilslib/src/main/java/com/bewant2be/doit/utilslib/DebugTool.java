package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 4/24/17.
 */
public class DebugTool {

    public static void makeFloatTextview(final Context context, final String str){
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            @Override
            public void run() {
                makeFloatTextviewInner(context, str);
            }
        });
    }

    // - - - - - - - -- - - - - - - -- - - - - - - - - - - - - -- - - - - - -

    private static void makeFloatTextviewInner(final Context context, String str)
    {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        ImageView imageView;
        View view;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            boolean bPermit = Settings.canDrawOverlays(context);
            if(bPermit){
                ToastUtil.toastComptible(context, "canDrawOverlays: "+bPermit);
            }else {
                ToastUtil.toastComptible(context, "noPerm");
                return;
            }
        }

        TextView textView = new TextView(context);
        textView.setText(str);

        lp.type =  WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        lp.gravity = Gravity.LEFT|Gravity.TOP;

        lp.x = 0;
        lp.y = 0;

        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        lp.format = PixelFormat.TRANSPARENT;
        windowManager.addView(textView, lp);
    }
}
