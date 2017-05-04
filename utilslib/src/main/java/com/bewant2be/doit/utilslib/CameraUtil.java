package com.bewant2be.doit.utilslib;

import android.hardware.Camera;
import android.util.Log;

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


    public static int getBackCameraId() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        final int numberOfCameras = Camera.getNumberOfCameras();

        for (int i = 0; i < numberOfCameras; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                Log.i(TAG, "CAMERA_FACING_BACK found, id=" + i);
                return i;
            }
        }

        Log.e(TAG, "CAMERA_FACING_BACK not found");
        return -1;
    }


    public static int getFrontCameraId() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        final int numberOfCameras = Camera.getNumberOfCameras();

        for (int i = 0; i < numberOfCameras; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.i(TAG, "CAMERA_FACING_FRONT found, id=" + i);
                return i;
            }
        }

        Log.e(TAG, "CAMERA_FACING_FRONT not found");
        return -1;
    }


}
