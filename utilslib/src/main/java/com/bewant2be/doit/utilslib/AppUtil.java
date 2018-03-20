package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by user on 2/2/18.
 */

public class AppUtil {

    public final static String TAG = AppUtil.class.getSimpleName();

    public static String getAppVersionName( Context context ) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        }catch (Exception e) {
            Log.e(TAG, "getPackageInfo exception: " + e.toString() );
            e.printStackTrace();
        }
        return "Unkown";
    }

    public static int getAppVersionCode( Context context ) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int verCode = packInfo.versionCode;
            return verCode;
        }catch (Exception e) {
            Log.e(TAG, "getPackageInfo exception: " + e.toString() );
            e.printStackTrace();
        }

        return 0;
    }
}
