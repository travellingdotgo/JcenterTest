package com.bewant2be.doit.utilslib;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by user on 4/4/17.
 */

public class ShellUtil {
    private final static String TAG = "ShellUtil";

    public static String execute(String cmd){   // this function blocks sometimes
        String error_str = "";
        String normal_str = "";

        try{
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String error = null;
            while ((error = ie.readLine()) != null ) {
                error_str += error + "\n";
            }
            Log.e( TAG, "error_str: " + error_str);

            String line = null;
            while ((line = in.readLine()) != null ) {
                normal_str += line + "\n";
            }

            Log.e( TAG, "normal_str: " + normal_str);
        }catch (Exception e){
            Log.e( TAG, "execute exception: " + e.toString() );
            return null;
        }

        return "ERROR: " + error_str + "\n" + "RESULT: " + normal_str;
    }

}
