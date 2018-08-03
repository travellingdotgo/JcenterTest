package com.bewant2be.doit.jcentertest;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bewant2be.netlib.NetworkUtil;

public class NetutilActivity extends AppCompatActivity {
    TextView tv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netutil);

        tv  = (TextView)findViewById(R.id.tvNetwork);
        NetworkUtil.getDefault().checkInternet(new NetworkUtil.CheckInternetCallback() {
            @Override
            public void onChange(int x) {
                if (x== NetworkUtil.INTERNET_OK){
                    updateTv( "OK" );
                }else{
                    updateTv( "N OK" );
                }
            }
        });
    }

    private void updateTv( final String s ){
        tv.post(new Runnable() {
            @Override
            public void run() {
                tv.setText( s );
            }
        });
    }


}
