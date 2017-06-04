package com.bewant2be.doit.utilslib;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by user on 4/4/17.
 */

public class ShellUtil {
    private final static String TAG = "ShellUtil";

    public static List<String> execute(String cmd){   // this function blocks sometimes
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

        List<String> listStr = new LinkedList<String>();
        listStr.add(error_str);
        listStr.add(normal_str);
        return listStr;
    }


    public static String executeAsRootUnstable(String cmd) {
        String error_str = "";
        String normal_str = "";
        String error_str2 = "";
        String normal_str2 = "";
        int iWaitFor = 0;

        String exception_str = "";

        try{
            //  . . . .   . . . .  step 1 . . . .  . . . .
            Process p = Runtime.getRuntime().exec("su");
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String error = null;
            while ((error = ie.readLine()) != null ) {
                error_str += error + "\n";
            }
            Log.e(TAG, "su_error_str: " + error_str);

            String line = null;
            while ((line = in.readLine()) != null ) {
                normal_str += line + "\n";
            }

            Log.e( TAG, "su_normal_str: " + normal_str);

            //  . . . .   . . . .  step 2 . . . .  . . . . . . . . .
            OutputStream out = p.getOutputStream();
            out.write((cmd + "\n").getBytes());
            out.flush();
            out.close();

            BufferedReader ie2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String error2 = null;
            while ((error2 = ie2.readLine()) != null ) {
                error_str2 += error2 + "\n";
            }
            Log.e( TAG, "error_str2: " + error_str2);

            String line2 = null;
            while ((line2 = in2.readLine()) != null ) {
                normal_str2 += line2 + "\n";
            }

            Log.e(TAG, "normal_str2: " + normal_str2);
            iWaitFor = p.waitFor();
        }catch (Exception e){
            exception_str = e.toString();
            Log.e( TAG, "execute exception: " + exception_str );
            return null;
        }

        return  "ERROR: " + error_str + "\n" + "RESULT: " + normal_str +
                "ERROR2: " + error_str2 + "\n" + "RESULT2: " + normal_str2 +
                "iWaitFor: " + iWaitFor + "\n" +
                "exception_str: " + exception_str + "\n";
    }

    public static boolean executeAsRoot(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            OutputStream out = p.getOutputStream();
            out.write((cmd + "\n").getBytes());
            out.flush();
            out.close();
            if (p.waitFor() == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e("TAG", "Exception:  " + e.toString());
            return false;
        }
    }

    public static boolean isRooted() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        if (new File(binPath).exists() && isExecutable(binPath)){
            Log.v(TAG, "binPath matches");
            return true;
        }
        if (new File(xBinPath).exists() && isExecutable(xBinPath)){
            Log.v(TAG, "xBinPath matches");
            return true;
        }

        Log.v(TAG, "not binPath nor xBinPath");
        return false;
    }

    //  . . . .   . . . .  . . . .  . . . . . . . . . . .  . . . .

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String str = in.readLine();
            Log.i(TAG, str==null?"null":str);
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(p!=null){
                p.destroy();
            }
        }
        return false;
    }

}
