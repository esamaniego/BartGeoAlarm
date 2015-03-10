package com.finalproject.erwin.bartgeoalarm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Handler;

import static com.finalproject.erwin.bartgeoalarm.Constants.INVALID_STRING_VALUE;


public class AlertActivity extends Activity {

    Boolean bool = false;
    private MediaPlayer mp;
    int alertToPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                changeColor();
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        Button button = (Button) findViewById(R.id.id_button_OK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.setLooping(false);
                finish();
            }
        });
        SharedPreferences sp = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String alarmSetting = sp.getString("alarmTone", INVALID_STRING_VALUE);


        if (alarmSetting.equals("Alarm"))
            alertToPlay = R.raw.alarm;
        else if (alarmSetting.equals("Pager Beep"))
            alertToPlay = R.raw.pagerbeeps;
        else
            alertToPlay = R.raw.roostercrow;

        mp = MediaPlayer.create(this, alertToPlay);
        mp.setLooping(true);
        mp.start();

    }

    public void changeColor() {
        if (bool) {
            findViewById(R.id.relative_layout).setBackgroundColor(Color.WHITE);
            bool = false;
        } else {
            //findViewById(R.id.relative_layout).setBackgroundColor(Color.parseColor("#FB8C00"));
            findViewById(R.id.relative_layout).setBackgroundColor(Color.BLACK);
            bool = true;

        }
    }



}
