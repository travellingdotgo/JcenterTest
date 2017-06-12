package com.bewant2be.doit.jcentertest.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bewant2be.doit.jcentertest.R;


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

        long id = System.currentTimeMillis();

        if(true){
            byte[] feature = new byte[]{0x01,0x02};
            byte[] image = new byte[]{0x03, 0x04};
            String user = "user";

            mDbHelper.insert(user, "time." + id, feature, image);
            mDbHelper.insert(user, "time." + id, feature, image);
        }

        mDbHelper.query();

        Log.w(TAG, " - - - - - - - - - - - - - - - - - -");

        mDbHelper.queryAll();
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
