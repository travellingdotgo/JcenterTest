package com.bewant2be.doit.jcentertest;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bewant2be.doit.utilslib.ShellUtil;
import com.bewant2be.doit.utilslib.ToastUtil;

import java.util.List;

public class ShellUtilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shellutil);


        boolean b = ShellUtil.isRooted();
        ToastUtil.toastComptible(getApplicationContext(), "isRooted: " + b);

        String cmd = "whoami";
        List<String> listStr = ShellUtil.execute(cmd);
        ToastUtil.toastComptible(getApplicationContext(), cmd + "\n- - - - - - - - - -\n" + listStr.get(0) + "\n" + listStr.get(1));

        cmd = "ls /system/xbin/su";
        listStr = ShellUtil.execute(cmd);
        ToastUtil.toastComptible(getApplicationContext(), cmd + "\n- - - - - - - - - -\n" + listStr.get(0) + "\n" + listStr.get(1));


        if(b){
            SystemClock.sleep(1000);

            cmd = "ifconfig eth0 down";
            String result = ShellUtil.executeAsRootUnstable(cmd);
            ToastUtil.toastComptible(getApplicationContext(), cmd + "\n" + result);
        }

        ShellUtil.executeAsRoot("reboot");
    }
}
