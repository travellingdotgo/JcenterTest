package com.bewant2be.doit.utilslib;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by user on 4/21/17.
 */
public class AbbrLog {
    public final static String TAG = "AbbrLog";
    public final static int DEFAULT_OMIT = 200;

    private static int cnt = 0;
    public static int omit = DEFAULT_OMIT;

    Map<String , Object> mMapOmit = new HashMap<String , Object>();
    public Object getOmit(){
        String stack = Thread.getAllStackTraces().toString();
        if(mMapOmit.containsKey(stack)){
            return mMapOmit.get(stack);
        }else{
            return null;
        }
    }

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
