package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by user on 4/7/17.
 */
public class PackageUtil {
    /**
     * get App versionCode
     * @param context
     * @return versionCode
     */
    public String getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     * @param context
     * @return versionName
     */
    public String getVersionName(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

}
