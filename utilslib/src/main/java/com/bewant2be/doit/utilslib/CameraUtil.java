package com.bewant2be.doit.utilslib;

import android.hardware.Camera;
/**
 * Created by user on 4/6/17.
 */
public class CameraUtil {
    public final static String TAG = "CameraUtil";

    public static int getSuitableCameraDisplayOrientation(int  display_degrees, int cameraId) {

        // get cameraInfo
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        // calculate
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + display_degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - display_degrees + 360) % 360;
        }

        return result;

    }



}
