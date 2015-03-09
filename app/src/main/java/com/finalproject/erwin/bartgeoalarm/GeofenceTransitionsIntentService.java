package com.finalproject.erwin.bartgeoalarm;

import android.app.DialogFragment;
import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import static com.finalproject.erwin.bartgeoalarm.Constants.ALARM_OFF;
import static com.finalproject.erwin.bartgeoalarm.Constants.ANDROID_BUILDING_ID;
import static com.finalproject.erwin.bartgeoalarm.Constants.TAG;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
//import com.google.android.gms.wearable.Wearable;

public class GeofenceTransitionsIntentService extends IntentService
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;

    public GeofenceTransitionsIntentService() {
        super(GeofenceTransitionsIntentService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();

        mGoogleApiClient.connect();

    }


    /**
     * Handles incoming intents.
     * @param intent The Intent sent by Location Services. This Intent is provided to Location
     * Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        GeofencingEvent geoFenceEvent = GeofencingEvent.fromIntent(intent);
        if (geoFenceEvent.hasError()) {
            int errorCode = geoFenceEvent.getErrorCode();
            Log.e(TAG, "Location Services error: " + errorCode);
        }

        else
        {
            int transitionType = geoFenceEvent.getGeofenceTransition();
            if (Geofence.GEOFENCE_TRANSITION_ENTER == transitionType)
            {
                String triggeredGeoFenceId = geoFenceEvent.getTriggeringGeofences().get(0).getRequestId();
                Log.d(TAG, "Wake up! Entering geofence: " + triggeredGeoFenceId);


                Runnable runnable = new MyRunnable();
                Thread thread1 = new Thread(runnable);
                thread1.start();


            }
            else if (Geofence.GEOFENCE_TRANSITION_EXIT == transitionType)
            {
                Log.d(TAG, "Exiting geofence ");
            }
        }

    }

    @Override
    public void onConnected(Bundle connectionHint) {
    }

    @Override
    public void onConnectionSuspended(int cause) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    }


    public class MyRunnable implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(GeofenceTransitionsIntentService.this,
                    PlayAlarmSoundService.class);
            startService(intent);
        }
    }
}
