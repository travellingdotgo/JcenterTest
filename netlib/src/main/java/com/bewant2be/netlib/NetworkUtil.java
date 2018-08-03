package com.bewant2be.netlib;


import android.util.Log;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class NetworkUtil {
    public final static String TAG = "NetworkUtil";
    public final static int INTERNET_OK = 0;
    public final static int INTERNET_NOK_FAILURE = -1;
    public final static int INTERNET_NOK_EXCEPITON = -2;
    public final static int INTERNET_NOK_TIMEOUT = -3;

    public interface CheckInternetCallback{
        void onChange( int x );
    }

    static NetworkUtil defaultInstance;
    CheckInternetCallback cb;

    public static NetworkUtil getDefault() {
        if (defaultInstance == null) {
            synchronized (NetworkUtil.class) {
                if (defaultInstance == null) {
                    defaultInstance = new NetworkUtil();
                }
            }
        }

        return defaultInstance;
    }

    public int checkInternet( final CheckInternetCallback cb ) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

        scheduledThreadPoolExecutor.scheduleAtFixedRate (new Runnable() {
            @Override
            public void run() {
                try {
                    int conn_code = pingTestWithTimeout();
                    cb.onChange(conn_code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1, 10, TimeUnit.SECONDS );

        return 0;
    }



    private int pingTestWithTimeout()  {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<Object> future =
                new FutureTask<Object>(new Callable<Object>() {
                    @Override
                    public Object call() {
                        return (Object) pingTest();
                    }
                });
        executor.execute(future);

        //int result;
        try {
            Object obj = future.get(5000, TimeUnit.MILLISECONDS);
            return (int)obj;
        } catch (InterruptedException e) {
            future.cancel(true);
        } catch (ExecutionException e) {
            future.cancel(true);
        } catch (TimeoutException e) {
            future.cancel(true);
            return INTERNET_NOK_TIMEOUT;
        } finally {
            executor.shutdown();
        }

        return 0;
    }

    // 0. internetOK, -1.internet NOK
    private int pingTest(){

        String[] hosts = {
                "www.taobao.com",
                "www.baidu.com",
                "www.qq.com",
                "www.163.com",
                "www.360.com",
                "www.jd.com",
                "www.we.com",
                "www.csdn.net",
                "www.mi.com",
                //
        };

        int hosts_size = hosts.length;

        int i=0;
        try{
            for(;i<hosts_size;i++){
                long time = shellPing(hosts[i]);
                if(Long.MAX_VALUE!=time){
                    Log.i(TAG, "ShellPing Success " + hosts[i] );
                    return INTERNET_OK;
                }else{
                    Log.i(TAG, "ShellPing Failure " + hosts[i] );
                }
            }

            return INTERNET_NOK_FAILURE;  // -1.internet NOK
        }catch (Exception e){
            e.printStackTrace();
            Log.i(TAG, "ShellPing Failure " + hosts[i] + e.toString() );
            return INTERNET_NOK_EXCEPITON;  // -2.internet NOK
        }

    }

    public static long shellPing(String host){
        boolean isReachable = false;
        long timeSpend = Long.MAX_VALUE;
        try{
            long start = System.currentTimeMillis();
            Process p1 = Runtime.getRuntime().exec("ping -c 1 -w 2 " + host);
            int returnVal = p1.waitFor();
            timeSpend = System.currentTimeMillis() - start;
            isReachable = (returnVal==0);
        }catch (UnknownHostException e) {
            e.printStackTrace();
            isReachable = false;
        }catch (IOException e) {
            e.printStackTrace();
            isReachable = false;
        }catch (Exception e) {
            e.printStackTrace();
            isReachable = false;
        }

        if (!isReachable){
            return Long.MAX_VALUE;
        }
        return timeSpend;
    }



}