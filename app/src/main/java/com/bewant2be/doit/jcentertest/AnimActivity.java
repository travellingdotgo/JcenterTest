package com.bewant2be.doit.jcentertest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class AnimActivity extends AppCompatActivity {
    Animation scaleAnimation;
    TextView tv;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        layout = (LinearLayout)findViewById(R.id.ll_container);

        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scaleanim);
        tv =(TextView)findViewById(R.id.tv);

        ((Button)findViewById(R.id.btn_animation)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tv.startAnimation(scaleAnimation);
            }
        });

        ((Button)findViewById(R.id.btnLayoutPara)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                int tick = (x++) % 10;
                                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50 * tick, 50 * tick);
                                //params.setMargins(5, 5, 5, 5);
                                layout.setLayoutParams(params);
                            }
                        });
                    }
                };
                timer.schedule(timerTask, 0, 30);
            }
        });


    }

    int x = 0;

}