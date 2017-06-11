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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimActivity extends AppCompatActivity {
    Animation scaleAnimation;

    @Bind(R.id.tv) TextView tv;
    @Bind(R.id.ll_container) LinearLayout layout;

    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        ButterKnife.bind(this);

        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scaleanim);
    }

    @OnClick(R.id.btnLayoutPara)
    public void btnLayoutParaClick(View view){
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

    @OnClick(R.id.btn_animation)
    public void btn_animationClick(View view){
        tv.startAnimation(scaleAnimation);
    }

}