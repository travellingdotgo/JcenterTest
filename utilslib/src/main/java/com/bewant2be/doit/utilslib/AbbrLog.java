package com.bewant2be.doit.utilslib;

import android.util.Log;

/**
 * Created by user on 4/21/17.
 */
public class AbbrLog {
    public final static String TAG = "AbbrLog";
    public final static int DEFAULT_OMIT = 200;

    private static int cnt = 0;
    public static int omit = DEFAULT_OMIT;

    public static void d(String tag, String val){
        if (cnt++ % omit == 0){
            Log.d(tag, val);
        }
    }

    public static void i(String tag, String val){
        if (cnt++ % omit == 0){
            Log.i(tag, val);
        }
    }

    public static void v(String tag, String val){
        if (cnt++ % omit == 0){
            Log.i(tag, val);
        }
    }

}
