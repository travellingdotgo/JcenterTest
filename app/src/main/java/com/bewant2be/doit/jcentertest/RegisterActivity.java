package com.bewant2be.doit.jcentertest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bewant2be.doit.utilslib.ToastUtil;

public class RegisterActivity extends AppCompatActivity {

    EditText editPhone;
    EditText editSms;
    Button btnGetSms;

    Button btnRegister;

    private String smsSent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnGetSms = (Button)findViewById(R.id.btnGetSms);
        btnGetSms.setEnabled(false);

        editPhone = (EditText)findViewById(R.id.editPhone);

        /*
        editPhone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String s = editPhone.getText().toString();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                if(s.length()==11){
                    btnGetSms.setEnabled(true);
                    //btnGetSms.setTextColor(Color.BLACK);
                    //btnGetSms.setBackgroundColor(Color.WHITE);
                }else{
                    btnGetSms.setEnabled(false);
                    //btnGetSms.setTextColor(Color.WHITE);
                    //btnGetSms.setBackgroundColor(Color.GRAY);
                }

                return false;
            }
        });
        */



        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smsSent.equals(editSms.getText().toString())){
                    Toast.makeText(getApplicationContext(), "验证码匹配", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(), "验证码不匹配", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
