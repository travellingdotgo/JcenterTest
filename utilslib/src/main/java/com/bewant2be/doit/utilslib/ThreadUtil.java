package com.bewant2be.doit.utilslib;

import android.os.Looper;
import android.util.Log;

import java.util.Map;

/**
 * Created by user on 4/10/17.
 */
public class ThreadUtil {

    private final static String TAG = "ThreadUtil";


    public static String getStatus(){
        String str = "";
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();

        Thread[] keys = map.keySet().toArray(new Thread[0]);
        for (Thread key : keys) {
            str += "\n\nthread : " + key.getName();
            StackTraceElement[] traces = map.get(key);
            for (StackTraceElement trace : traces)
                str += ">> trace : " + trace.toString() + "\n";
        }

        return str;
    }


    public static String getMainThreadStacktrace(){
        String str = "";

        StackTraceElement[] stackTraceElements = Looper.getMainLooper().getThread().getStackTrace();
        for (StackTraceElement trace : stackTraceElements){
            String sStrace = ">> trace : " + trace.toString() + "\n";
            str += sStrace;
        }

        return  str;
    }
}
