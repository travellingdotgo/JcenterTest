package com.bewant2be.doit.utilslib;

import android.app.Activity;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by user on 4/6/17.
 */
public class DisplayUtil {
    private final static String TAG = "DisplayUtil";

    public static int getRotation(Activity activity) {

        WindowManager windowManager = activity.getWindowManager();
        int rotation = windowManager.getDefaultDisplay().getRotation();
        int degree = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degree = 0; break;
            case Surface.ROTATION_90: degree = 90; break;
            case Surface.ROTATION_180: degree = 180; break;
            case Surface.ROTATION_270: degree = 270; break;
        }

        return degree;
    }
}
