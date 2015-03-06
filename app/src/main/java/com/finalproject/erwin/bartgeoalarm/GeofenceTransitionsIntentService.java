package com.finalproject.erwin.bartgeoalarm;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

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

        //Log.d(TAG, "GeofenceTransistionsIntentService called ");
    }


    /**
     * Handles incoming intents.
     * @param intent The Intent sent by Location Services. This Intent is provided to Location
     * Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        /*GeofencingEvent geoFenceEvent = GeofencingEvent.fromIntent(intent);
        if (geoFenceEvent.hasError()) {
            int errorCode = geoFenceEvent.getErrorCode();
            Log.e(TAG, "Location Services error: " + errorCode);
        } else {

            int transitionType = geoFenceEvent.getGeofenceTransition();
            if (Geofence.GEOFENCE_TRANSITION_ENTER == transitionType) {
                // Connect to the Google Api service in preparation for sending a DataItem.
                mGoogleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                // Get the geofence id triggered. Note that only one geofence can be triggered at a
                // time in this example, but in some cases you might want to consider the full list
                // of geofences triggered.
                String triggeredGeoFenceId = geoFenceEvent.getTriggeringGeofences().get(0)
                        .getRequestId();
                // Create a DataItem with this geofence's id. The wearable can use this to create
                // a notification.
                final PutDataMapRequest putDataMapRequest =
                        PutDataMapRequest.create(GEOFENCE_DATA_ITEM_PATH);
                putDataMapRequest.getDataMap().putString(KEY_GEOFENCE_ID, triggeredGeoFenceId);
                if (mGoogleApiClient.isConnected()) {
                    Wearable.DataApi.putDataItem(
                            mGoogleApiClient, putDataMapRequest.asPutDataRequest()).await();
                } else {
                    Log.e(TAG, "Failed to send data item: " + putDataMapRequest
                            + " - Client disconnected from Google Play Services");
                }
                Toast.makeText(this, getString(R.string.entering_geofence),
                        Toast.LENGTH_SHORT).show();
                mGoogleApiClient.disconnect();
            } else if (Geofence.GEOFENCE_TRANSITION_EXIT == transitionType) {
                // Delete the data item when leaving a geofence region.
                mGoogleApiClient.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                Wearable.DataApi.deleteDataItems(mGoogleApiClient, GEOFENCE_DATA_ITEM_URI).await();
                Toast.makeText(this, getString(R.string.exiting_geofence),
                        Toast.LENGTH_SHORT).show();
                mGoogleApiClient.disconnect();
            }
        }*/




        //start of alternate code
        //Log.d(TAG, "Received intent: " + intent);
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
                //just to test. remove and put in a more appporpriate location/method
                List<String> removeList = new ArrayList<String>();
                removeList.add(ANDROID_BUILDING_ID);
                LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, removeList);
                //end test
            }
            else if (Geofence.GEOFENCE_TRANSITION_EXIT == transitionType)
            {
                Log.d(TAG, "Exiting geofence ");
                //just to test. remove and put in a more appporpriate location/method
//                List<String> removeList = new ArrayList<String>();
//                removeList.add(ANDROID_BUILDING_ID);
//                LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, removeList);
                //end test
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
}
