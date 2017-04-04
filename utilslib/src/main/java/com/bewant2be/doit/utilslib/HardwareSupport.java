package com.bewant2be.doit.utilslib;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * Created by user on 4/4/17.
 */
public class HardwareSupport {
    public final static String TAG = "HardwareSupport";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static String getSupportedABIS(){
        String strABIS = "";
        String[] abis =  Build.SUPPORTED_ABIS;
        for ( int i=0; i<abis.length; i++ ){
            strABIS += abis[i] + "  ";
        }

        return strABIS;
    }

}
