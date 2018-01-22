package com.bewant2be.doit.utilslib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;


public class NetUtil {

    public final static String TAG = "NetUtil";

    public enum NetState{ NULL,WIFI,ETH }

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

    public static String getAddress( Context applicationContext ){
        ConnectivityManager mConnectivityManager = (ConnectivityManager)applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);//获取系统的连接服务
        //实例化mActiveNetInfo对象
        NetworkInfo mActiveNetInfo = mConnectivityManager.getActiveNetworkInfo();//获取网络连接的信息
        if(mActiveNetInfo==null){
            Log.e(TAG, "ActiveNetInfo==null");
            return null;
        }


        //如果是WIFI网络
        if(mActiveNetInfo.getType()==ConnectivityManager.TYPE_WIFI)
        {
            String s = getLocalIPAddress();
            Log.i(TAG,"ConnectivityManager WIFI:  " + s);
            return s;
        }
        //如果是手机网络
        else if(mActiveNetInfo.getType()==ConnectivityManager.TYPE_MOBILE)
        {
            String s = getLocalIPAddress();
            Log.i(TAG, "ConnectivityManager TYPE_MOBILE:  " + s);
            return s;
        }
        else
        {
            String s = getLocalIPAddress();
            Log.i(TAG, "ConnectivityManager Unknown:  " + s);
            return s;
        }
    }


    public static String getLocalIPAddressFailed()
    {
        try
        {
            for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface.getNetworkInterfaces(); mEnumeration.hasMoreElements();)
            {
                NetworkInterface intf = mEnumeration.nextElement();
                for (Enumeration<InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIPAddr.nextElement();
                    //如果不是回环地址
                    if (!inetAddress.isLoopbackAddress())
                    {
                        //直接返回本地IP地址
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex)
        {
            Log.e("Error", ex.toString());
        }

        return null;
    }



    public static String getLocalIPAddress(){
        try{
            for(Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();en.hasMoreElements();){
                NetworkInterface intf = en.nextElement();
                for(Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();){
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if(!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)){
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
            return "null";

        }
        catch (Exception e){
            return "Exception";
        }
    }

    public static NetState getCurrentNetWorkState( Context context ){
        ConnectivityManager mConnManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mInfo = mConnManager.getActiveNetworkInfo();
        if(null == mInfo){
            return NetState.NULL;
        }

        Log.e(TAG, mInfo.getTypeName());

        if (mInfo.isAvailable() && mInfo.isConnected()&& mInfo.getTypeName().equals("WIFI")){
            return NetState.WIFI;
        }
        else if(mInfo.isAvailable() && mInfo.isConnected()&& mInfo.getTypeName().equals("ETHERNET")){
            return NetState.ETH;
        }

        return NetState.NULL;
    }

}

