package com.bewant2be.doit.jcentertest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bewant2be.doit.utilslib.ToastUtil;

public class WebentranceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webentrance);

        Intent intent = getIntent();
        String action = intent.getAction();

        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = intent.getData();
            if(uri != null){
                String name = uri.getQueryParameter("name");
                String age= uri.getQueryParameter("age");

                ToastUtil.toastComptible(getApplicationContext(), "name=" + name + ", age="+age);
            }
        }



    }
}
