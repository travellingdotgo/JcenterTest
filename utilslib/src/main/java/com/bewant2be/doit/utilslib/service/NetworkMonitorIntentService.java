package com.bewant2be.doit.utilslib.service;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.bewant2be.doit.utilslib.NetUtil;


public class NetworkMonitorIntentService extends IntentService {
    public static final String TAG = "NetworkMonitorIS";
    public static final String NOTIFY_ACTION = "com.bewant2be.ntf.NETWORK_STATUS_CHANGE";

    public static final int NET_STATUS_CONNECTED = 0;
    public static final int NET_STATUS_DISCONNECTED = 1;

    private long mlServiceStartedTick = 0;
    private long mlFirstPingSuccdTick = 0;

    private boolean mbWorking = true;

    private long mlSuccessCnt = 0;
    private long mlFailureCnt = 0;

    private long mlLastPingTimeCost = 0;

    private int lastStatus = -1;        //  网络上一个状态

    private int PING_INTERVAL_MS = 10 * 1000;// milli sec

    private void notifyNetStatus() {
        Intent intent = new Intent(NOTIFY_ACTION);
        intent.putExtra("SuccessCnt", mlSuccessCnt);
        intent.putExtra("FailureCnt", mlFailureCnt);
        intent.putExtra("LastPingTimeCost", mlLastPingTimeCost);
        intent.putExtra("lastStatus",lastStatus);

        if (mlSuccessCnt > 0)
            intent.putExtra("FirstSuccessTook", mlFirstPingSuccdTick - mlServiceStartedTick);

        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        sendBroadcast(intent);
    }


    public NetworkMonitorIntentService() {
        super("NetworkMonitorIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mlServiceStartedTick = System.currentTimeMillis();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            while (mbWorking) {
                try {
                    long time = NetUtil.shellPing();

                    if (time == Long.MAX_VALUE) {
                        mlFailureCnt++;
                        mlLastPingTimeCost = time;
                        notifyNetStatus();
                        lastStatus = NET_STATUS_DISCONNECTED;

                        if (mlFirstPingSuccdTick != 0) {
                            Log.v(TAG, "mlSuccessCnt=" + mlSuccessCnt + ", mlFailureCnt=" + mlFailureCnt + ", FirstSuccessTook: " + "null" + ",  LastTry:  timeout!");
                        } else {
                            Log.v(TAG, "mlSuccessCnt=" + mlSuccessCnt + ", mlFailureCnt=" + mlFailureCnt + ", FirstSuccessTook: " + (mlFirstPingSuccdTick - mlServiceStartedTick) + ",  LastTry:  timeout!");
                        }

                        Thread.sleep(PING_INTERVAL_MS);
                    } else {
                        mlSuccessCnt++;
                        mlLastPingTimeCost = time;
                        if (mlSuccessCnt == 1) {
                            mlFirstPingSuccdTick = System.currentTimeMillis();
                        }
                        notifyNetStatus();
                        lastStatus = NET_STATUS_CONNECTED;

                        Log.v(TAG, "mlSuccessCnt=" + mlSuccessCnt + ", mlFailureCnt=" + mlFailureCnt + ", FirstSuccessTook: " + (mlFirstPingSuccdTick - mlServiceStartedTick) + ",  LastTry: time= " + time);

                        Thread.sleep(PING_INTERVAL_MS);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e);
                }
            } // end of while
        }
    }// end of onHandleIntent

}
