package com.bewant2be.doit.utilslib;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by user on 5/11/17.
 */
public class DeviceUtil {
    private final static String TAG = "DeviceUtil";

    public static String getSerialNumber(){
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        }
        catch (Exception ignored) {
            Log.e(TAG, ignored.toString());
        }
        return serial;
    }
}
