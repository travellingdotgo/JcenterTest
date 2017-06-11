package com.bewant2be.doit.jcentertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.bewant2be.doit.utilslib.ToastUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MiscActivity extends AppCompatActivity {
    public final static String TAG = "MiscActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);


        ClassLoader classLoader = getClassLoader();
        if (classLoader != null){
            while (classLoader.getParent()!=null){
                classLoader = classLoader.getParent();
                Log.i(TAG,"[onCreate] classLoader " + " : " + classLoader.toString());
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        //testExecutorService();
        testScheduledExecutorService();
        ToastUtil.toastComptible(getApplicationContext(), "onPostResume ending");

        testDownload();
    }

    private void testExecutorService(){
        ExecutorService executorService =       Executors.newFixedThreadPool(3);
                                            //  Executors.newCachedThreadPool();        /* flexible */
                                            //  Executors.newSingleThreadExecutor();    /* FIFO */
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("in fixedThreadPool, ThreadId: " + index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    private void testScheduledExecutorService(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("delay 1 seconds, and excute every 3 seconds");
            }
        }, 1, 3, TimeUnit.SECONDS);

        //scheduledThreadPool.shutdown();
    }

    long mBytesDownloaded = 0;
    private void testDownload(){
        String url = "http://mirrors.163.com/ubuntu-releases/14.04/ubuntu-14.04.5-desktop-amd64.iso";
        String dirPath = "/sdcard/";
        String fileName = "ubuntu-14.04.5-desktop-amd64.iso";
        AndroidNetworking.download(url, dirPath, fileName)
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        Log.d(TAG, "bytesDownloaded= " + bytesDownloaded + " / " + totalBytes);
                        if (bytesDownloaded - mBytesDownloaded > 5 * 1000 * 1000) {
                            ToastUtil.toastComptible(getApplicationContext(), "bytesDownloaded= " + bytesDownloaded + " / " + totalBytes);
                            mBytesDownloaded = bytesDownloaded;
                        }
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }
}
