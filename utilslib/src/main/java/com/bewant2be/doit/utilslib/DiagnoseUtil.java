package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 3/29/17.
 */
public class DiagnoseUtil {
    private final static String TAG = "DiagnoseUtil";

    private final static int VALUE_TARGET = 88;
    private final static int TIMEOUT_MS = 100;

    final private int[] value = {0};

    public interface Callback{
        public void onResult( boolean bMainThreadOK );
    }

    public boolean checkMainThreadBlock( final Callback callback ){
        if (callback==null){
            Log.e(TAG, "callback==null");
            return false;
        }

        new Thread(){
            @Override
            public void run() {
                super.run();

                Handler handler = new Handler(Looper.getMainLooper());

                value[0] = 0;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        value[0] = VALUE_TARGET;
                    }
                });

                SystemClock.sleep(TIMEOUT_MS);

                if ( value[0]==VALUE_TARGET ){
                    callback.onResult( true );
                }else{
                    callback.onResult( false );
                }
            }
        }.start();

        return true;
    }
}
