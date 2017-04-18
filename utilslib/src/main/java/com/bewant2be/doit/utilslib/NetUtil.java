package com.bewant2be.doit.utilslib;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by user on 3/28/17.
 */
public class NetUtil {

    public final static String TAG = "NetUtil";

    public static long shellPing(){
        boolean isReachable = false;
        long timeSpend = Long.MAX_VALUE;
        try{
            long start = System.currentTimeMillis();
            Process p1 = Runtime.getRuntime().exec("ping -c 1 -w 2 www.baidu.com");
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

