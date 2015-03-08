package com.finalproject.erwin.bartgeoalarm;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;

import java.net.URI;

public class PlayAlarmSoundService extends IntentService {

    private MediaPlayer mp;



    public PlayAlarmSoundService() {
        super("PlayAlarmSoundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int alertToPlay = R.raw.roostercrow;
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
