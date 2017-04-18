package com.bewant2be.doit.utilslib;

/**
 * Created by user on 3/29/17.
 */
public class DebugUtil {
    private final static String TAG = "DebugUtil";

    public final static String byte2hex(byte [] buffer){
        String h = "";

        for(int i = 0; i < buffer.length; i++){
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if(temp.length() == 1){
                temp = "0" + temp;
            }
            h = h + " "+ temp;
        }

        return h;
    }
}
