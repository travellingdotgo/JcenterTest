package com.bewant2be.doit.jcentertest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bewant2be.doit.utilslib.service.NetworkMonitorIntentService;


public class StatusReceiver extends BroadcastReceiver {
    public static final String TAG = StatusReceiver.class.getSimpleName();

    public StatusReceiver() {
        Log.i(TAG, "StatusReceiver");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, intent.getAction());
        String str = "";
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            long lSuccessCnt = bundle.getLong("SuccessCnt");
            long lFailureCnt = bundle.getLong("FailureCnt");
            int lastStatus = bundle.getInt("lastStatus", -1);

            long lLastPingTimeCost = bundle.getLong("LastPingTimeCost");
            if (lLastPingTimeCost != Long.MAX_VALUE) {
                Log.i(TAG, "NetStatus: " + lastStatus);
                str = " lLastPingTimeCost=";
                str += lLastPingTimeCost;
                if (lastStatus == NetworkMonitorIntentService.NET_STATUS_DISCONNECTED) {
                    Log.i(TAG, "NetworkResume " + lastStatus);
                }
            } else {
                if (lastStatus == NetworkMonitorIntentService.NET_STATUS_CONNECTED)
                    Log.d(TAG, "NetworkBroken  lLastPingTimeCost= " + lLastPingTimeCost);
            }
        }
    }
}
