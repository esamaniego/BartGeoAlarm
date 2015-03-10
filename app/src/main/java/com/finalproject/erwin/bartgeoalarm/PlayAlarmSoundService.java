package com.finalproject.erwin.bartgeoalarm;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


        if (alarmSetting.equals("Alarm"))
            alertToPlay = R.raw.alarm;
        else
            alertToPlay = R.raw.roostercrow;

        //int alertToPlay = R.raw.roostercrow;
        mp = MediaPlayer.create(this, alertToPlay);
        //mp.setLooping(true);
        mp.start();

//        ActionDialogFragment actionFragment = new ActionDialogFragment();
//        actionFragment.show(getFragmentManager(), "question");


        //Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);

        int id = 12345;
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Bart Geo Alarm")
                .setTicker("Your are approaching your destination")
                .setContentText("You are arriving at your destination")
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);




        Intent i = new Intent(PlayAlarmSoundService.this, AlertActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);




        //mp.setLooping(false);



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
