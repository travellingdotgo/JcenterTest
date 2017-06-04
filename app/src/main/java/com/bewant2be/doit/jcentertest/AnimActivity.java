package com.bewant2be.doit.jcentertest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class AnimActivity extends AppCompatActivity {

    Button scaleBtn	;
    Animation scaleAnimation;

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scaleanim);
        scaleBtn = (Button)findViewById(R.id.btn_animation);
        tv =(TextView)findViewById(R.id.tv);

        scaleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tv.startAnimation(scaleAnimation);
            }
        });

    }

}