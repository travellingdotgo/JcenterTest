package com.bewant2be.doit.jcentertest.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bewant2be.doit.jcentertest.R;

import java.util.List;


public class SqliteActivity extends AppCompatActivity {

    public final static String TAG = "SqliteActivity";

    UserinfoDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        mDbHelper = new UserinfoDbHelper(getApplicationContext());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(true){
            long start = System.currentTimeMillis();
            for(int i=0; i<1000; i++){
                UserInfo userInfo = fackData();
                mDbHelper.insert(   userInfo.getUser(), "time." + userInfo.getTime(),
                                    userInfo.getFeature(), userInfo.getImage() );
            }
            long consume = System.currentTimeMillis()-start;
            Log.e(TAG, "insert consume: " + consume);
        }

        //mDbHelper.query();

        Log.w(TAG, " - - - - - - - - - - - - - - - - - -");

        mDbHelper.queryAll();

        long start = System.currentTimeMillis();
        List<UserInfo> list = mDbHelper.loadAll();
        long consume = System.currentTimeMillis()-start;
        Log.e(TAG, "loadAll consume: " + consume);
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    // . . . . . ..
    private UserInfo fackData(){
        byte[] feature = new byte[2048];
        for (int i=0; i<2048; i++){
            feature[i] = (byte)(i%8);
        }
        byte[] image = new byte[2048];
        for (int i=0; i<2048; i++){
            image[i] = (byte)(i%8);
        }

        String user = "user";
        long time = System.currentTimeMillis();
        UserInfo info = new UserInfo(user,""+time, feature,image);
        return  info;
    }
}
