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

//    // Geofence parameters for fremont bart
//    public static final String FREMONT_ID = "3";
//    public static final double FREMONT_LATITUDE = 37.557355;
//    public static final double FREMONT_LONGITUDE = -121.9764;
//    public static final float FREMONT_RADIUS_METERS = 72.0f;
//
//    // Geofence parameters for embarcadero bart
//    public static final String EMBARCADERO_ID = "4";
//    public static final double EMBARCADERO_LATITUDE = 37.792976;
//    public static final double EMBARCADERO_LONGITUDE = -122.396742;
    public static final float EMBARCADERO_RADIUS_METERS = 60.0f;
//
//    // Geofence parameters for civic bart
//    public static final String CIVICCENTER_ID = "5";
//    public static final double CIVICCENTER_LATITUDE = 37.779528;
//    public static final double CIVICCENTER_LONGITUDE = -122.413756;
//    public static final float CIVICCENTER_RADIUS_METERS = 60.0f;

    public static final String TWELFTH_ST_OAKLAND_ID = "Twelfth St Oakland City Center";
    public static final double TWELFTH_ST_OAKLAND_LATITUDE = 37.803664;
    public static final double TWELFTH_ST_OAKLAND_LONGITUDE = -122.271604;

    public static final String SIXTEENTH_ST_MISSION_ID = "Sixteenth St Mission";
    public static final double SIXTEENTH_ST_MISSION_LATITUDE = 37.765062;
    public static final double SIXTEENTH_ST_MISSION_LONGITUDE = -122.419694;

    public static final String NINETEENTH_ST_OAKLAND_ID = "Nineteenth St Oakland";
    public static final double NINETEENTH_ST_OAKLAND_LATITUDE = 37.80787;
    public static final double NINETEENTH_ST_OAKLAND_LONGITUDE = -122.269029;

    public static final String TWENTY_FOURTH_ST_MISSION_ID = "Twenty-fourth St Mission";
    public static final double TWENTY_FOURTH_ST_MISSION_LATITUDE = 37.752254;
    public static final double TWENTY_FOURTH_ST_MISSION_LONGITUDE = -122.418466;

    public static final String ASHBY_ID = "Ashby";
    public static final double ASHBY_LATITUDE = 37.853024;
    public static final double ASHBY_LONGITUDE = -122.26978;

    public static final String BALBOA_PARK_ID = "Balboa Park";
    public static final double BALBOA_PARK_LATITUDE = 37.72198087;
    public static final double BALBOA_PARK_LONGITUDE = -122.4474142;

    public static final String BAY_FAIR_ID = "Bay Fair";
    public static final double BAY_FAIR_LATITUDE = 37.697185;
    public static final double BAY_FAIR_LONGITUDE = -122.126871;

    public static final String CASTRO_VALLEY_ID = "Castro Valley";
    public static final double CASTRO_VALLEY_LATITUDE = 37.690754;
    public static final double CASTRO_VALLEY_LONGITUDE = -122.075567;

    public static final String CIVIC_CENTER_ID = "Civic Center";
    public static final double CIVIC_CENTER_LATITUDE = 37.779528;
    public static final double CIVIC_CENTER_LONGITUDE = -122.413756;

    public static final String COLISEUM_ID = "Coliseum";
    public static final double COLISEUM_LATITUDE = 37.754006;
    public static final double COLISEUM_LONGITUDE = -122.197273;

    public static final String COLMA_ID = "Colma";
    public static final double COLMA_LATITUDE = 37.684638;
    public static final double COLMA_LONGITUDE = -122.466233;

    public static final String CONCORD_ID = "Concord";
    public static final double CONCORD_LATITUDE = 37.973737;
    public static final double CONCORD_LONGITUDE = -122.029095;

    public static final String DALY_CITY_ID = "Daly City";
    public static final double DALY_CITY_LATITUDE = 37.70612055;
    public static final double DALY_CITY_LONGITUDE = -122.4690807;

    public static final String DOWNTOWN_BERKELEY_ID = "Downtown Berkeley";
    public static final double DOWNTOWN_BERKELEY_LATITUDE = 37.869867;
    public static final double DOWNTOWN_BERKELEY_LONGITUDE = -122.268045;

    public static final String DUBLIN_ID = "Dublin";
    public static final double DUBLIN_LATITUDE = 37.701695;
    public static final double DUBLIN_LONGITUDE = -121.900367;

    public static final String EL_CERRITO_DEL_NORTE_ID = "El Cerrito del Norte";
    public static final double EL_CERRITO_DEL_NORTE_LATITUDE = 37.925655;
    public static final double EL_CERRITO_DEL_NORTE_LONGITUDE = -122.317269;

    public static final String EL_CERRITO_PLAZA_ID = "El Cerrito Plaza";
    public static final double EL_CERRITO_PLAZA_LATITUDE = 37.9030588;
    public static final double EL_CERRITO_PLAZA_LONGITUDE = -122.2992715;

    public static final String EMBARCADERO_ID = "Embarcadero";
    public static final double EMBARCADERO_LATITUDE = 37.792976;
    public static final double EMBARCADERO_LONGITUDE = -122.396742;

    public static final String FREMONT_ID = "Fremont";
    public static final double FREMONT_LATITUDE = 37.557355;
    public static final double FREMONT_LONGITUDE = -121.9764;

    public static final String FRUITVALE_ID = "Fruitvale";
    public static final double FRUITVALE_LATITUDE = 37.774963;
    public static final double FRUITVALE_LONGITUDE = -122.224274;

    public static final String GLEN_PARK_ID = "Glen Park";
    public static final double GLEN_PARK_LATITUDE = 37.732921;
    public static final double GLEN_PARK_LONGITUDE = -122.434092;

    public static final String HAYWARD_ID = "Hayward";
    public static final double HAYWARD_LATITUDE = 37.670399;
    public static final double HAYWARD_LONGITUDE = -122.087967;

    public static final String LAFAYETTE_ID = "Lafayette";
    public static final double LAFAYETTE_LATITUDE = 37.893394;
    public static final double LAFAYETTE_LONGITUDE = -122.123801;

    public static final String LAKE_MERRITT_ID = "Lake Merritt";
    public static final double LAKE_MERRITT_LATITUDE = 37.797484;
    public static final double LAKE_MERRITT_LONGITUDE = -122.265609;

    public static final String MACARTHUR_ID = "MacArthur";
    public static final double MACARTHUR_LATITUDE = 37.828415;
    public static final double MACARTHUR_LONGITUDE = -122.267227;

    public static final String MILLBRAE_ID = "Millbrae";
    public static final double MILLBRAE_LATITUDE = 37.599787;
    public static final double MILLBRAE_LONGITUDE = -122.38666;

    public static final String MONTGOMERY_ST_ID = "Montgomery St";
    public static final double MONTGOMERY_ST_LATITUDE = 37.789256;
    public static final double MONTGOMERY_ST_LONGITUDE = -122.401407;

    public static final String NORTH_BERKELEY_ID = "North Berkeley";
    public static final double NORTH_BERKELEY_LATITUDE = 37.87404;
    public static final double NORTH_BERKELEY_LONGITUDE = -122.283451;

    public static final String NORTH_CONCORD_ID = "North Concord";
    public static final double NORTH_CONCORD_LATITUDE = 38.003275;
    public static final double NORTH_CONCORD_LONGITUDE = -122.024597;

    public static final String OAKLAND_INTERNATIONAL_AIRPORT_ID = "Oakland International Airport";
    public static final double OAKLAND_INTERNATIONAL_AIRPORT_LATITUDE = 37.71297174;
    public static final double OAKLAND_INTERNATIONAL_AIRPORT_LONGITUDE = -122.21244024;

    public static final String ORINDA_ID = "Orinda";
    public static final double ORINDA_LATITUDE = 37.87836087;
    public static final double ORINDA_LONGITUDE = -122.1837911;

    public static final String PITTSBURG_ID = "Pittsburg";
    public static final double PITTSBURG_LATITUDE = 38.018914;
    public static final double PITTSBURG_LONGITUDE = -121.945154;

    public static final String PLEASANT_HILL_ID = "Pleasant Hill";
    public static final double PLEASANT_HILL_LATITUDE = 37.928403;
    public static final double PLEASANT_HILL_LONGITUDE = -122.056013;

    public static final String POWELL_ST_ID = "Powell St";
    public static final double POWELL_ST_LATITUDE = 37.784991;
    public static final double POWELL_ST_LONGITUDE = -122.406857;

    public static final String RICHMOND_ID = "Richmond";
    public static final double RICHMOND_LATITUDE = 37.936887;
    public static final double RICHMOND_LONGITUDE = -122.353165;

    public static final String ROCKRIDGE_ID = "Rockridge";
    public static final double ROCKRIDGE_LATITUDE = 37.844601;
    public static final double ROCKRIDGE_LONGITUDE = -122.251793;

    public static final String SAN_BRUNO_ID = "San Bruno";
    public static final double SAN_BRUNO_LATITUDE = 37.637753;
    public static final double SAN_BRUNO_LONGITUDE = -122.416038;

    public static final String SAN_FRANCISCO_AIRPORT_ID = "San Francisco Airport";
    public static final double SAN_FRANCISCO_AIRPORT_LATITUDE = 37.616035;
    public static final double SAN_FRANCISCO_AIRPORT_LONGITUDE = -122.392612;

    public static final String SAN_LEANDRO_ID = "San Leandro";
    public static final double SAN_LEANDRO_LATITUDE = 37.72261921;
    public static final double SAN_LEANDRO_LONGITUDE = -122.1613112;

    public static final String SOUTH_HAYWARD_ID = "South Hayward";
    public static final double SOUTH_HAYWARD_LATITUDE = 37.63479954;
    public static final double SOUTH_HAYWARD_LONGITUDE = -122.0575506;

    public static final String SOUTH_SAN_FRANCISCO_ID = "South San Francisco";
    public static final double SOUTH_SAN_FRANCISCO_LATITUDE = 37.664174;
    public static final double SOUTH_SAN_FRANCISCO_LONGITUDE = -122.444116;

    public static final String UNION_CITY_ID = "Union City";
    public static final double UNION_CITY_LATITUDE = 37.591208;
    public static final double UNION_CITY_LONGITUDE = -122.017867;

    public static final String WALNUT_CREEK_ID = "Walnut Creek";
    public static final double WALNUT_CREEK_LATITUDE = 37.905628;
    public static final double WALNUT_CREEK_LONGITUDE = -122.067423;

    public static final String WEST_DUBLIN_ID = "West Dublin";
    public static final double WEST_DUBLIN_LATITUDE = 37.699759;
    public static final double WEST_DUBLIN_LONGITUDE = -121.928099;

    public static final String WEST_OAKLAND_ID = "West Oakland";
    public static final double WEST_OAKLAND_LATITUDE = 37.80467476;
    public static final double WEST_OAKLAND_LONGITUDE = -122.2945822;

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

