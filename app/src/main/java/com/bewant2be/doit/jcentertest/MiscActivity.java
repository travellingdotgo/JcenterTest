package com.bewant2be.doit.jcentertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bewant2be.doit.utilslib.ToastUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MiscActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        //testExecutorService();
        testScheduledExecutorService();
        ToastUtil.toastComptible(getApplicationContext(), "onPostResume ending");
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
}
