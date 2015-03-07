package com.finalproject.erwin.bartgeoalarm;


import android.net.Uri;

import com.google.android.gms.location.Geofence;

public class Constants {
    private Constants() {
    }

    public static final String TAG = "ExampleGeofencingApp";

    // Request code to attempt to resolve Google Play services connection failures.
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    // Timeout for making a connection to GoogleApiClient (in milliseconds).
    public static final long CONNECTION_TIME_OUT_MS = 100;

    // For the purposes of this demo, the geofences are hard-coded and should not expire.
    // An app with dynamically-created geofences would want to include a reasonable expiration time.
    public static final long GEOFENCE_EXPIRATION_TIME = Geofence.NEVER_EXPIRE;

    // Geofence parameters for the Android building on Google's main campus in Mountain View.
    public static final String ANDROID_BUILDING_ID = "1";
    public static final double ANDROID_BUILDING_LATITUDE = 37.420092;
    public static final double ANDROID_BUILDING_LONGITUDE = -122.083648;
    public static final float ANDROID_BUILDING_RADIUS_METERS = 60.0f;

    // Geofence parameters for fremont bart
    public static final String FREMONT_ID = "3";
    public static final double FREMONT_LATITUDE = 37.557355;
    public static final double FREMONT_LONGITUDE = -121.9764;
    public static final float FREMONT_RADIUS_METERS = 72.0f;


    // Geofence parameters for embarcadero bart
    public static final String EMBARCADERO_ID = "4";
    public static final double EMBARCADERO_LATITUDE = 37.792976;
    public static final double EMBARCADERO_LONGITUDE = -122.396742;
    public static final float EMBARCADERO_RADIUS_METERS = 60.0f;

    // Geofence parameters for civic bart
    public static final String CIVICCENTER_ID = "5";
    public static final double CIVICCENTER_LATITUDE = 37.779528;
    public static final double CIVICCENTER_LONGITUDE = -122.413756;
    public static final float CIVICCENTER_RADIUS_METERS = 60.0f;



    // The constants below are less interesting than those above.

    // Path for the DataItem containing the last geofence id entered.
    public static final String GEOFENCE_DATA_ITEM_PATH = "/geofenceid";
    public static final Uri GEOFENCE_DATA_ITEM_URI =
            new Uri.Builder().scheme("wear").path(GEOFENCE_DATA_ITEM_PATH).build();
    public static final String KEY_GEOFENCE_ID = "geofence_id";

    // Keys for flattened geofences stored in SharedPreferences.
    public static final String KEY_ALARMSTATUS = "com.example.wearable.geofencing.KEY_ALARMSTATUS";
    public static final String KEY_LATITUDE = "com.example.wearable.geofencing.KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "com.example.wearable.geofencing.KEY_LONGITUDE";
    public static final String KEY_RADIUS = "com.example.wearable.geofencing.KEY_RADIUS";
    public static final String KEY_EXPIRATION_DURATION =
            "com.example.wearable.geofencing.KEY_EXPIRATION_DURATION";
    public static final String KEY_TRANSITION_TYPE =
            "com.example.wearable.geofencing.KEY_TRANSITION_TYPE";
    // The prefix for flattened geofence keys.
    public static final String KEY_PREFIX = "com.example.wearable.geofencing.KEY";

    public final static int ALARM_OFF = 0;
    public final static int ALARM_ON = 1;

    // Invalid values, used to test geofence storage when retrieving geofences.
    public static final long INVALID_LONG_VALUE = -999l;
    public static final float INVALID_FLOAT_VALUE = -999.0f;
    public static final int INVALID_INT_VALUE = -999;
}

