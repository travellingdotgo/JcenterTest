package com.bewant2be.doit.utilslib;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

import java.util.List;

/**
 * Created by user on 4/26/17.
 */
public class SystemUtil {
    public static void getRunningAppProcessInfo( Context context ) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        //获得系统里正在运行的所有进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            // 进程ID号
            int pid = runningAppProcessInfo.pid;
            // 用户ID
            int uid = runningAppProcessInfo.uid;
            // 进程名
            String processName = runningAppProcessInfo.processName;
            // 占用的内存
            int[] pids = new int[] {pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(pids);
            int memorySize = memoryInfo[0].dalvikPrivateDirty;

            String s = "processName="+processName+"\npid="+pid+"\nuid="+uid+"\nmemorySize="+memorySize+"kb";
            if (BuildConfig.DEBUG){
                ToastUtil.toastComptible(context, s);
            }
        }
    }
}
