package com.finalproject.erwin.bartgeoalarm;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

import java.net.URI;

import static com.finalproject.erwin.bartgeoalarm.Constants.INVALID_STRING_VALUE;
import static com.finalproject.erwin.bartgeoalarm.Constants.TAG;

public class PlayAlarmSoundService extends IntentService {

    private MediaPlayer mp;
    int alertToPlay;



    public PlayAlarmSoundService() {
        super("PlayAlarmSoundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences sp = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String alarmSetting = sp.getString("alarmTone", INVALID_STRING_VALUE);

        Log.d(TAG, "alarmSetting: " + alarmSetting);

        if (alarmSetting.equals("Alarm"))
            alertToPlay = R.raw.alarm;
        else
            alertToPlay = R.raw.pagerbeeps;

        //int alertToPlay = R.raw.roostercrow;
        mp = MediaPlayer.create(this, alertToPlay);
        //mp.setLooping(true);
        mp.start();

//        ActionDialogFragment actionFragment = new ActionDialogFragment();
//        actionFragment.show(getFragmentManager(), "question");







    }

//    @Override
//    public void onDialogPositiveClick(DialogFragment dialog) {
//        mp.setLooping(false);
//    }

//    @Override
//    public void onDialogNegativeClick(DialogFragment dialog) {
//
//    }
}
