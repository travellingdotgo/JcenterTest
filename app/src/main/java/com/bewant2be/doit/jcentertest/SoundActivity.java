package com.bewant2be.doit.jcentertest;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bewant2be.doit.utilslib.ToastUtil;

import java.util.HashMap;
import java.util.Map;

public class SoundActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int sound1,sound2;
    private Map<String, Integer> map=new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        init();

        findViewById(R.id.btnPlaySoundPool).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundPool();
            }
        });

        findViewById(R.id.btnRingtone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
    }

    private void playSoundPool(){

        /*
        int play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate[0.5-2.0]
        */
        int soundID = map.get("soundtrack1");
        soundPool.play(soundID, 1.0f,1.0f, 0, 0, 1);
        ToastUtil.toastComptible(getApplicationContext(), "SoundPool");
    }

    private void init(){
        // volume, type, quality
        soundPool=new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        int PRIORITY_1 = 1;
        map.put("soundtrack1", soundPool.load( getApplicationContext(), R.raw.bell, PRIORITY_1));
        map.put("soundtrack2", soundPool.load( getApplicationContext(), R.raw.chat_request, PRIORITY_1));
    }


}
