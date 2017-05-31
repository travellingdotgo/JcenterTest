package com.bewant2be.doit.utilslib;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
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




    /**
     * 获得屏幕宽度（像素）
     *
     * @param activity
     * @return
     */
    public static int getDisplayWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
    }

    /**
     * 获得屏幕高度（像素）
     *
     * @param activity
     * @return
     */
    public static int getDisplayHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;     // 屏幕高度（像素）
        return height;
    }


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param context 通过上下文获得显示参数中的屏幕密度
     * @param pxValue 需要转换的px值
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param context  通过上下文获得显示参数中的屏幕密度
     * @param dipValue 需要转换的dp值
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context 通过上下文获得显示参数中的屏幕密度
     * @param pxValue 需要转换的px值
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context 通过上下文获得显示参数中的屏幕密度
     * @param spValue 需要转换的sp值
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
