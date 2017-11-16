package com.bewant2be.doit.jcentertest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bewant2be.doit.jcentertest.uiutil.ToastWithTwoText;

public class EnhancedtoastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enhancedtoast);

        ToastWithTwoText.createToastConfig(getApplicationContext()).ToastShow("开心吗", "还行吧");
    }


}
