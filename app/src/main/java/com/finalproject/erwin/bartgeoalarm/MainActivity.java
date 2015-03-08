package com.finalproject.erwin.bartgeoalarm;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import static com.finalproject.erwin.bartgeoalarm.Constants.FREMONT_RADIUS_METERS;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.finalproject.erwin.bartgeoalarm.Constants.*;

//public class MainActivity extends ActionBarActivity implements OnMapReadyCallback,GooglePlayServicesClient.ConnectionCallbacks,
//GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks , LocationListener, GoogleMap.OnMarkerClickListener,
//        AdapterView.OnItemSelectedListener {
public class MainActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks , LocationListener, GoogleMap.OnMarkerClickListener,
        AdapterView.OnItemSelectedListener {

    private GoogleMap googleMap;
    FlyOutContainer root;
    private Spinner spinner;
    private Spinner spinner1, spinner2, spinner3;

    //private String[] stations = {"SELECT STATION", "12th St. Oakland City Center" , "16th St. Mission" , "19th St. Oakland" , "24th St. Mission" , "Ashby" , "Balboa Park" , "Bay Fair" , ”Castro Valley” , ”Civic Center/UN Plaza” , ”Colma” , ”Concord” , ”Daly City” , ”Downtown Berkeley” , ”Dublin/Pleasanton” , ”El Cerrito del Norte” , ”El Cerrito Plaza” , ”Embarcadero” , ”Fremont” , ”Fruitvale” , ”Glen Park” , ”Hayward” , ”Lafayette” , ”Lake Merritt” , ”MacArthur” , ”Millbrae” , ”Montgomery St.” , ”North Berkeley” , ”North Concord/Martinez” , ”Oakland Int'l Airport” , ”Orinda” , ”Pittsburg/Bay Point” , ”Pleasant Hill/Contra Costa Centre” , ”Powell St.” , ”Richmond” , ”Rockridge” , ”San Bruno” , ”San Francisco Int'l Airport” , ”San Leandro” , ”South Hayward” , ”South San Francisco" , "Union City" , "Walnut Creek" , "West Dublin/Pleasanton" , "West Oakland""};
    private String[] stations = {"SELECT STATION", "Twelfth St Oakland City Center", "Sixteenth St Mission" , "Nineteenth St Oakland" , "Twenty-fourth St Mission" , "Ashby" , "Balboa Park" , "Bay Fair" , "Castro Valley" , "Civic Center" , "Colma" , "Coliseum" , "Concord" , "Daly City" , "Downtown Berkeley" , "Dublin" , "El Cerrito del Norte" , "El Cerrito Plaza" , "Embarcadero" , "Fremont" , "Fruitvale" , "Glen Park" , "Hayward" , "Lafayette" , "Lake Merritt" , "MacArthur" , "Millbrae" , "Montgomery St" , "North Berkeley" , "North Concord" , "Oakland International Airport" , "Orinda" , "Pittsburg" , "Pleasant Hill" , "Powell St" , "Richmond" , "Rockridge" , "San Bruno" , "San Francisco Airport" , "San Leandro" , "South Hayward" , "South San Francisco" , "Union City" , "Walnut Creek" , "West Dublin" , "West Oakland"};
    private String[] alarmSounds = {"SELECT ALARM TONE", "Alarm", "Pager Beep"};

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapterAlarm;

    // Internal List of Geofence objects. In a real app, these might be provided by an API based on
    // locations within the user's proximity.
    List<Geofence> mGeofenceList;

    // These will store hard-coded geofences .
    private SimpleGeofence mAndroidBuildingGeofence;
    private SimpleGeofence mYerbaBuenaGeofence;
    //private SimpleGeofence mFremontGeofence;
    //private SimpleGeofence mEmbarcaderoGeofence;
    //private SimpleGeofence mCivicCenterGeofence;

    private SimpleGeofence mTwelfthStOaklandGeofence;
    private SimpleGeofence mSixteenthStMissionGeofence;
    private SimpleGeofence mNineteenthStOaklandGeofence;
    private SimpleGeofence mTwentyFourthStMissionGeofence;
    private SimpleGeofence mAshbyGeofence;
    private SimpleGeofence mBalboaParkGeofence;
    private SimpleGeofence mBayFairGeofence;
    private SimpleGeofence mCastroValleyGeofence;
    private SimpleGeofence mCivicCenterGeofence;
    private SimpleGeofence mColiseumGeofence;
    private SimpleGeofence mColmaGeofence;
    private SimpleGeofence mConcordGeofence;
    private SimpleGeofence mDalyCityGeofence;
    private SimpleGeofence mDowntownBerkeleyGeofence;
    private SimpleGeofence mDublinGeofence;
    private SimpleGeofence mElCerritoDelNorteGeofence;
    private SimpleGeofence mElCerritoPlazaGeofence;
    private SimpleGeofence mEmbarcaderoGeofence;
    private SimpleGeofence mFremontGeofence;
    private SimpleGeofence mFruitvaleGeofence;
    private SimpleGeofence mGlenParkGeofence;
    private SimpleGeofence mHaywardGeofence;
    private SimpleGeofence mLafayetteGeofence;
    private SimpleGeofence mLakeMerrittGeofence;
    private SimpleGeofence mMacArthurGeofence;
    private SimpleGeofence mMillbraeGeofence;
    private SimpleGeofence mMontgomeryStGeofence;
    private SimpleGeofence mNorthBerkeleyGeofence;
    private SimpleGeofence mNorthConcordGeofence;
    private SimpleGeofence mOaklandInternationalAirportGeofence;
    private SimpleGeofence mOrindaGeofence;
    private SimpleGeofence mPittsburgGeofence;
    private SimpleGeofence mPleasantHillGeofence;
    private SimpleGeofence mPowellStGeofence;
    private SimpleGeofence mRichmondGeofence;
    private SimpleGeofence mRockridgeGeofence;
    private SimpleGeofence mSanBrunoGeofence;
    private SimpleGeofence mSanFranciscoAirportGeofence;
    private SimpleGeofence mSanLeandroGeofence;
    private SimpleGeofence mSouthHaywardGeofence;
    private SimpleGeofence mSouthSanFranciscoGeofence;
    private SimpleGeofence mUnionCityGeofence;
    private SimpleGeofence mWalnutCreekGeofence;
    private SimpleGeofence mWestDublinGeofence;
    private SimpleGeofence mWestOaklandGeofence;





    // Persistent storage for geofences.
    private SimpleGeofenceStore mGeofenceStorage;

    private LocationServices mLocationService;
    // Stores the PendingIntent used to request geofence monitoring.
    private PendingIntent mGeofenceRequestIntent;
    private GoogleApiClient mApiClient;

    private MediaPlayer mp;



    // Defines the allowable request types (in this example, we only add geofences).
    private enum REQUEST_TYPE {ADD}
    private REQUEST_TYPE mRequestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.root = (FlyOutContainer) this.getLayoutInflater().inflate(R.layout.activity_main, null);
        this.setContentView(root);




        if (!isGooglePlayServicesAvailable()) {
            Log.e(TAG, "Google Play services unavailable.");
            finish();
            return;
        }

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        // Instantiate a new geofence storage area.
        mGeofenceStorage = new SimpleGeofenceStore(this);
        // Instantiate the current List of geofences.
        mGeofenceList = new ArrayList<Geofence>();
        createGeofences();



        googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();


        initialMapDisplay();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stations);
        adapterAlarm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alarmSounds);

        spinner = (Spinner) findViewById(R.id.id_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner1 = (Spinner) findViewById(R.id.id_spinner1);
        spinner1.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.id_spinner2);
        spinner2.setAdapter(adapter);

        spinner3 = (Spinner) findViewById(R.id.id_spinner3);
        spinner3.setAdapter(adapterAlarm);

        setSettings();

    }



    @Override
    protected void onStart() {
        super.onStart();
        mApiClient.connect();
    }


    public void setSettings()
    {
        SharedPreferences sp = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String alarmSetting = sp.getString("alarmTone", INVALID_STRING_VALUE);
        String homeStationSetting = sp.getString("homeStation", INVALID_STRING_VALUE);
        String awayStationSetting = sp.getString("awayStation", INVALID_STRING_VALUE);
        if (alarmSetting != INVALID_STRING_VALUE) {
            //int index = list.indexOf(name);
            int index = adapterAlarm.getPosition(alarmSetting);
            Log.d(TAG, "index for alarm: " + index);
            spinner3.setSelection(index);
        }

        if (homeStationSetting != INVALID_STRING_VALUE) {
            int index = adapter.getPosition(homeStationSetting);
            spinner1.setSelection(index);
        }

        if (awayStationSetting != INVALID_STRING_VALUE) {
            int index = adapter.getPosition(awayStationSetting);
            spinner2.setSelection(index);
        }
    }



    public void initialMapDisplay() {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(POWELL_ST_LATITUDE, POWELL_ST_LONGITUDE));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(TWELFTH_ST_OAKLAND_LATITUDE, TWELFTH_ST_OAKLAND_LONGITUDE)).title(TWELFTH_ST_OAKLAND_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(SIXTEENTH_ST_MISSION_LATITUDE, SIXTEENTH_ST_MISSION_LONGITUDE)).title(SIXTEENTH_ST_MISSION_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(NINETEENTH_ST_OAKLAND_LATITUDE, NINETEENTH_ST_OAKLAND_LONGITUDE)).title(NINETEENTH_ST_OAKLAND_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(TWENTY_FOURTH_ST_MISSION_LATITUDE, TWENTY_FOURTH_ST_MISSION_LONGITUDE)).title(TWENTY_FOURTH_ST_MISSION_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(ASHBY_LATITUDE, ASHBY_LONGITUDE)).title(ASHBY_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(BALBOA_PARK_LATITUDE, BALBOA_PARK_LONGITUDE)).title(BALBOA_PARK_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(BAY_FAIR_LATITUDE, BAY_FAIR_LONGITUDE)).title(BAY_FAIR_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(CASTRO_VALLEY_LATITUDE, CASTRO_VALLEY_LONGITUDE)).title(CASTRO_VALLEY_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(CIVIC_CENTER_LATITUDE, CIVIC_CENTER_LONGITUDE)).title(CIVIC_CENTER_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(COLISEUM_LATITUDE, COLISEUM_LONGITUDE)).title(COLISEUM_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(COLMA_LATITUDE, COLMA_LONGITUDE)).title(COLMA_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(CONCORD_LATITUDE, CONCORD_LONGITUDE)).title(CONCORD_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(DALY_CITY_LATITUDE, DALY_CITY_LONGITUDE)).title(DALY_CITY_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(DOWNTOWN_BERKELEY_LATITUDE, DOWNTOWN_BERKELEY_LONGITUDE)).title(DOWNTOWN_BERKELEY_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(DUBLIN_LATITUDE, DUBLIN_LONGITUDE)).title(DUBLIN_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(EL_CERRITO_DEL_NORTE_LATITUDE, EL_CERRITO_DEL_NORTE_LONGITUDE)).title(EL_CERRITO_DEL_NORTE_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(EL_CERRITO_PLAZA_LATITUDE, EL_CERRITO_PLAZA_LONGITUDE)).title(EL_CERRITO_PLAZA_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(EMBARCADERO_LATITUDE, EMBARCADERO_LONGITUDE)).title(EMBARCADERO_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(FREMONT_LATITUDE, FREMONT_LONGITUDE)).title(FREMONT_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(FRUITVALE_LATITUDE, FRUITVALE_LONGITUDE)).title(FRUITVALE_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(GLEN_PARK_LATITUDE, GLEN_PARK_LONGITUDE)).title(GLEN_PARK_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(HAYWARD_LATITUDE, HAYWARD_LONGITUDE)).title(HAYWARD_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(LAFAYETTE_LATITUDE, LAFAYETTE_LONGITUDE)).title(LAFAYETTE_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(LAKE_MERRITT_LATITUDE, LAKE_MERRITT_LONGITUDE)).title(LAKE_MERRITT_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(MACARTHUR_LATITUDE, MACARTHUR_LONGITUDE)).title(MACARTHUR_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(MILLBRAE_LATITUDE, MILLBRAE_LONGITUDE)).title(MILLBRAE_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(MONTGOMERY_ST_LATITUDE, MONTGOMERY_ST_LONGITUDE)).title(MONTGOMERY_ST_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(NORTH_BERKELEY_LATITUDE, NORTH_BERKELEY_LONGITUDE)).title(NORTH_BERKELEY_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(NORTH_CONCORD_LATITUDE, NORTH_CONCORD_LONGITUDE)).title(NORTH_CONCORD_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(OAKLAND_INTERNATIONAL_AIRPORT_LATITUDE, OAKLAND_INTERNATIONAL_AIRPORT_LONGITUDE)).title(OAKLAND_INTERNATIONAL_AIRPORT_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(ORINDA_LATITUDE, ORINDA_LONGITUDE)).title(ORINDA_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(PITTSBURG_LATITUDE, PITTSBURG_LONGITUDE)).title(PITTSBURG_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(PLEASANT_HILL_LATITUDE, PLEASANT_HILL_LONGITUDE)).title(PLEASANT_HILL_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(POWELL_ST_LATITUDE, POWELL_ST_LONGITUDE)).title(POWELL_ST_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(RICHMOND_LATITUDE, RICHMOND_LONGITUDE)).title(RICHMOND_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(ROCKRIDGE_LATITUDE, ROCKRIDGE_LONGITUDE)).title(ROCKRIDGE_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(SAN_BRUNO_LATITUDE, SAN_BRUNO_LONGITUDE)).title(SAN_BRUNO_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(SAN_FRANCISCO_AIRPORT_LATITUDE, SAN_FRANCISCO_AIRPORT_LONGITUDE)).title(SAN_FRANCISCO_AIRPORT_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(SAN_LEANDRO_LATITUDE, SAN_LEANDRO_LONGITUDE)).title(SAN_LEANDRO_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(SOUTH_HAYWARD_LATITUDE, SOUTH_HAYWARD_LONGITUDE)).title(SOUTH_HAYWARD_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(SOUTH_SAN_FRANCISCO_LATITUDE, SOUTH_SAN_FRANCISCO_LONGITUDE)).title(SOUTH_SAN_FRANCISCO_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(UNION_CITY_LATITUDE, UNION_CITY_LONGITUDE)).title(UNION_CITY_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(WALNUT_CREEK_LATITUDE, WALNUT_CREEK_LONGITUDE)).title(WALNUT_CREEK_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(WEST_DUBLIN_LATITUDE, WEST_DUBLIN_LONGITUDE)).title(WEST_DUBLIN_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(WEST_OAKLAND_LATITUDE, WEST_OAKLAND_LONGITUDE)).title(WEST_OAKLAND_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        googleMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        LatLng point = marker.getPosition();
        String title = marker.getTitle();


        SimpleGeofence localSimpleGeofence;


        Log.d(TAG, "onMarkerClick LatLng: " + point);
        double markerLat = point.latitude;
        double markerLong = point.longitude;


        String geoIDFromLatLong = mGeofenceStorage.getIDFromLatLong(Double.toString(point.latitude) + Double.toString(point.longitude));
        Log.d(TAG, "geoIDfromLatLong: " + geoIDFromLatLong);

        SimpleGeofence clickedSimpleGeofence = mGeofenceStorage.getGeofence(geoIDFromLatLong);
        int currentAlarmStatus = clickedSimpleGeofence.getAlarmStatus();


        float hue;
        String msg;


        if (currentAlarmStatus == 0){
            hue = BitmapDescriptorFactory.HUE_GREEN;
            clickedSimpleGeofence.setAlarmStatus(ALARM_ON);
            msg = " alarm is enabled";

            localSimpleGeofence = new SimpleGeofence(geoIDFromLatLong, markerLat, markerLong, EMBARCADERO_RADIUS_METERS, GEOFENCE_EXPIRATION_TIME, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT, ALARM_ON);
            mGeofenceStorage.setGeofence(geoIDFromLatLong, localSimpleGeofence);  //update storage
            mGeofenceList.add(localSimpleGeofence.toGeofence());


            // Get the PendingIntent for the geofence monitoring request.
            // Send a request to add the current geofences.
            mGeofenceRequestIntent = getGeofenceTransitionPendingIntent();
            LocationServices.GeofencingApi.addGeofences(mApiClient, mGeofenceList,
                    mGeofenceRequestIntent);

        }
        else {
            hue = BitmapDescriptorFactory.HUE_RED;
            clickedSimpleGeofence.setAlarmStatus(ALARM_OFF);
            msg = " alarm has been turned off";

            localSimpleGeofence = new SimpleGeofence(geoIDFromLatLong, markerLat, markerLong, EMBARCADERO_RADIUS_METERS, GEOFENCE_EXPIRATION_TIME, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT, ALARM_OFF);
            mGeofenceStorage.setGeofence(geoIDFromLatLong, localSimpleGeofence);   //update storage
            mGeofenceList.remove(localSimpleGeofence.toGeofence());

            List<String> removedList = new ArrayList<String >();
            removedList.add(geoIDFromLatLong);

            LocationServices.GeofencingApi.removeGeofences(mApiClient, removedList);
            removedList.clear();
        }

        marker.remove();



        googleMap.addMarker(new MarkerOptions()
                .position(point)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(hue)));


        Toast.makeText(this, title + msg, Toast.LENGTH_LONG).show();
        return false;
    }


    /**
     * In this sample, the geofences are predetermined and are hard-coded here. A real app might
     * dynamically create geofences based on the user's location.
     */
    public void createGeofences() {
        // Create internal "flattened" objects containing the geofence data.
        mAndroidBuildingGeofence = new SimpleGeofence(
                ANDROID_BUILDING_ID,                // geofenceId.
                ANDROID_BUILDING_LATITUDE,
                ANDROID_BUILDING_LONGITUDE,
                ANDROID_BUILDING_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

//        mEmbarcaderoGeofence = new SimpleGeofence(
//                EMBARCADERO_ID,                // geofenceId
//                EMBARCADERO_LATITUDE,
//                EMBARCADERO_LONGITUDE,
//                EMBARCADERO_RADIUS_METERS,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
//                ALARM_OFF
//        );
//
//        mCivicCenterGeofence = new SimpleGeofence(
//                CIVICCENTER_ID,                // geofenceId
//                CIVICCENTER_LATITUDE,
//                CIVICCENTER_LONGITUDE,
//                CIVICCENTER_RADIUS_METERS,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
//                ALARM_OFF
//        );


        mTwelfthStOaklandGeofence = new SimpleGeofence(
                TWELFTH_ST_OAKLAND_ID,
                TWELFTH_ST_OAKLAND_LATITUDE,
                TWELFTH_ST_OAKLAND_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mSixteenthStMissionGeofence = new SimpleGeofence(
                SIXTEENTH_ST_MISSION_ID,
                SIXTEENTH_ST_MISSION_LATITUDE,
                SIXTEENTH_ST_MISSION_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mNineteenthStOaklandGeofence = new SimpleGeofence(
                NINETEENTH_ST_OAKLAND_ID,
                NINETEENTH_ST_OAKLAND_LATITUDE,
                NINETEENTH_ST_OAKLAND_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mTwentyFourthStMissionGeofence = new SimpleGeofence(
                TWENTY_FOURTH_ST_MISSION_ID,
                TWENTY_FOURTH_ST_MISSION_LATITUDE,
                TWENTY_FOURTH_ST_MISSION_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mAshbyGeofence = new SimpleGeofence(
                ASHBY_ID,
                ASHBY_LATITUDE,
                ASHBY_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mBalboaParkGeofence = new SimpleGeofence(
                BALBOA_PARK_ID,
                BALBOA_PARK_LATITUDE,
                BALBOA_PARK_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mBayFairGeofence = new SimpleGeofence(
                BAY_FAIR_ID,
                BAY_FAIR_LATITUDE,
                BAY_FAIR_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mCastroValleyGeofence = new SimpleGeofence(
                CASTRO_VALLEY_ID,
                CASTRO_VALLEY_LATITUDE,
                CASTRO_VALLEY_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mCivicCenterGeofence = new SimpleGeofence(
                CIVIC_CENTER_ID,
                CIVIC_CENTER_LATITUDE,
                CIVIC_CENTER_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mColiseumGeofence = new SimpleGeofence(
                COLISEUM_ID,
                COLISEUM_LATITUDE,
                COLISEUM_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mColmaGeofence = new SimpleGeofence(
                COLMA_ID,
                COLMA_LATITUDE,
                COLMA_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mConcordGeofence = new SimpleGeofence(
                CONCORD_ID,
                CONCORD_LATITUDE,
                CONCORD_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mDalyCityGeofence = new SimpleGeofence(
                DALY_CITY_ID,
                DALY_CITY_LATITUDE,
                DALY_CITY_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mDowntownBerkeleyGeofence = new SimpleGeofence(
                DOWNTOWN_BERKELEY_ID,
                DOWNTOWN_BERKELEY_LATITUDE,
                DOWNTOWN_BERKELEY_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mDublinGeofence = new SimpleGeofence(
                DUBLIN_ID,
                DUBLIN_LATITUDE,
                DUBLIN_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mElCerritoDelNorteGeofence = new SimpleGeofence(
                EL_CERRITO_DEL_NORTE_ID,
                EL_CERRITO_DEL_NORTE_LATITUDE,
                EL_CERRITO_DEL_NORTE_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mElCerritoPlazaGeofence = new SimpleGeofence(
                EL_CERRITO_PLAZA_ID,
                EL_CERRITO_PLAZA_LATITUDE,
                EL_CERRITO_PLAZA_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mEmbarcaderoGeofence = new SimpleGeofence(
                EMBARCADERO_ID,
                EMBARCADERO_LATITUDE,
                EMBARCADERO_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mFremontGeofence = new SimpleGeofence(
                FREMONT_ID,
                FREMONT_LATITUDE,
                FREMONT_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mFruitvaleGeofence = new SimpleGeofence(
                FRUITVALE_ID,
                FRUITVALE_LATITUDE,
                FRUITVALE_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mGlenParkGeofence = new SimpleGeofence(
                GLEN_PARK_ID,
                GLEN_PARK_LATITUDE,
                GLEN_PARK_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mHaywardGeofence = new SimpleGeofence(
                HAYWARD_ID,
                HAYWARD_LATITUDE,
                HAYWARD_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mLafayetteGeofence = new SimpleGeofence(
                LAFAYETTE_ID,
                LAFAYETTE_LATITUDE,
                LAFAYETTE_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mLakeMerrittGeofence = new SimpleGeofence(
                LAKE_MERRITT_ID,
                LAKE_MERRITT_LATITUDE,
                LAKE_MERRITT_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mMacArthurGeofence = new SimpleGeofence(
                MACARTHUR_ID,
                MACARTHUR_LATITUDE,
                MACARTHUR_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mMillbraeGeofence = new SimpleGeofence(
                MILLBRAE_ID,
                MILLBRAE_LATITUDE,
                MILLBRAE_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mMontgomeryStGeofence = new SimpleGeofence(
                MONTGOMERY_ST_ID,
                MONTGOMERY_ST_LATITUDE,
                MONTGOMERY_ST_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mNorthBerkeleyGeofence = new SimpleGeofence(
                NORTH_BERKELEY_ID,
                NORTH_BERKELEY_LATITUDE,
                NORTH_BERKELEY_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mNorthConcordGeofence = new SimpleGeofence(
                NORTH_CONCORD_ID,
                NORTH_CONCORD_LATITUDE,
                NORTH_CONCORD_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mOaklandInternationalAirportGeofence = new SimpleGeofence(
                OAKLAND_INTERNATIONAL_AIRPORT_ID,
                OAKLAND_INTERNATIONAL_AIRPORT_LATITUDE,
                OAKLAND_INTERNATIONAL_AIRPORT_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mOrindaGeofence = new SimpleGeofence(
                ORINDA_ID,
                ORINDA_LATITUDE,
                ORINDA_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mPittsburgGeofence = new SimpleGeofence(
                PITTSBURG_ID,
                PITTSBURG_LATITUDE,
                PITTSBURG_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mPleasantHillGeofence = new SimpleGeofence(
                PLEASANT_HILL_ID,
                PLEASANT_HILL_LATITUDE,
                PLEASANT_HILL_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mPowellStGeofence = new SimpleGeofence(
                POWELL_ST_ID,
                POWELL_ST_LATITUDE,
                POWELL_ST_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mRichmondGeofence = new SimpleGeofence(
                RICHMOND_ID,
                RICHMOND_LATITUDE,
                RICHMOND_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mRockridgeGeofence = new SimpleGeofence(
                ROCKRIDGE_ID,
                ROCKRIDGE_LATITUDE,
                ROCKRIDGE_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mSanBrunoGeofence = new SimpleGeofence(
                SAN_BRUNO_ID,
                SAN_BRUNO_LATITUDE,
                SAN_BRUNO_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mSanFranciscoAirportGeofence = new SimpleGeofence(
                SAN_FRANCISCO_AIRPORT_ID,
                SAN_FRANCISCO_AIRPORT_LATITUDE,
                SAN_FRANCISCO_AIRPORT_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mSanLeandroGeofence = new SimpleGeofence(
                SAN_LEANDRO_ID,
                SAN_LEANDRO_LATITUDE,
                SAN_LEANDRO_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mSouthHaywardGeofence = new SimpleGeofence(
                SOUTH_HAYWARD_ID,
                SOUTH_HAYWARD_LATITUDE,
                SOUTH_HAYWARD_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mSouthSanFranciscoGeofence = new SimpleGeofence(
                SOUTH_SAN_FRANCISCO_ID,
                SOUTH_SAN_FRANCISCO_LATITUDE,
                SOUTH_SAN_FRANCISCO_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mUnionCityGeofence = new SimpleGeofence(
                UNION_CITY_ID,
                UNION_CITY_LATITUDE,
                UNION_CITY_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mWalnutCreekGeofence = new SimpleGeofence(
                WALNUT_CREEK_ID,
                WALNUT_CREEK_LATITUDE,
                WALNUT_CREEK_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mWestDublinGeofence = new SimpleGeofence(
                WEST_DUBLIN_ID,
                WEST_DUBLIN_LATITUDE,
                WEST_DUBLIN_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mWestOaklandGeofence = new SimpleGeofence(
                WEST_OAKLAND_ID,
                WEST_OAKLAND_LATITUDE,
                WEST_OAKLAND_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );






        // Store these flat versions in SharedPreferences and add them to the geofence list.
//        mGeofenceStorage.setGeofence(FREMONT_ID,mFremontGeofence);
//        mGeofenceStorage.setGeofence(EMBARCADERO_ID, mEmbarcaderoGeofence);
//        mGeofenceStorage.setGeofence(CIVICCENTER_ID, mCivicCenterGeofence);


        //mGeofenceStorage.setGeofence(ANDROID_BUILDING_ID, mAndroidBuildingGeofence);
        mGeofenceStorage.setGeofence(TWELFTH_ST_OAKLAND_ID, mTwelfthStOaklandGeofence);
        mGeofenceStorage.setGeofence(SIXTEENTH_ST_MISSION_ID, mSixteenthStMissionGeofence);
        mGeofenceStorage.setGeofence(NINETEENTH_ST_OAKLAND_ID, mNineteenthStOaklandGeofence);
        mGeofenceStorage.setGeofence(TWENTY_FOURTH_ST_MISSION_ID, mTwentyFourthStMissionGeofence);
        mGeofenceStorage.setGeofence(ASHBY_ID, mAshbyGeofence);
        mGeofenceStorage.setGeofence(BALBOA_PARK_ID, mBalboaParkGeofence);
        mGeofenceStorage.setGeofence(BAY_FAIR_ID, mBayFairGeofence);
        mGeofenceStorage.setGeofence(CASTRO_VALLEY_ID, mCastroValleyGeofence);
        mGeofenceStorage.setGeofence(CIVIC_CENTER_ID, mCivicCenterGeofence);
        mGeofenceStorage.setGeofence(COLISEUM_ID, mColiseumGeofence);
        mGeofenceStorage.setGeofence(COLMA_ID, mColmaGeofence);
        mGeofenceStorage.setGeofence(CONCORD_ID, mConcordGeofence);
        mGeofenceStorage.setGeofence(DALY_CITY_ID, mDalyCityGeofence);
        mGeofenceStorage.setGeofence(DOWNTOWN_BERKELEY_ID, mDowntownBerkeleyGeofence);
        mGeofenceStorage.setGeofence(DUBLIN_ID, mDublinGeofence);
        mGeofenceStorage.setGeofence(EL_CERRITO_DEL_NORTE_ID, mElCerritoDelNorteGeofence);
        mGeofenceStorage.setGeofence(EL_CERRITO_PLAZA_ID, mElCerritoPlazaGeofence);
        mGeofenceStorage.setGeofence(EMBARCADERO_ID, mEmbarcaderoGeofence);
        mGeofenceStorage.setGeofence(FREMONT_ID, mFremontGeofence);
        mGeofenceStorage.setGeofence(FRUITVALE_ID, mFruitvaleGeofence);
        mGeofenceStorage.setGeofence(GLEN_PARK_ID, mGlenParkGeofence);
        mGeofenceStorage.setGeofence(HAYWARD_ID, mHaywardGeofence);
        mGeofenceStorage.setGeofence(LAFAYETTE_ID, mLafayetteGeofence);
        mGeofenceStorage.setGeofence(LAKE_MERRITT_ID, mLakeMerrittGeofence);
        mGeofenceStorage.setGeofence(MACARTHUR_ID, mMacArthurGeofence);
        mGeofenceStorage.setGeofence(MILLBRAE_ID, mMillbraeGeofence);
        mGeofenceStorage.setGeofence(MONTGOMERY_ST_ID, mMontgomeryStGeofence);
        mGeofenceStorage.setGeofence(NORTH_BERKELEY_ID, mNorthBerkeleyGeofence);
        mGeofenceStorage.setGeofence(NORTH_CONCORD_ID, mNorthConcordGeofence);
        mGeofenceStorage.setGeofence(OAKLAND_INTERNATIONAL_AIRPORT_ID, mOaklandInternationalAirportGeofence);
        mGeofenceStorage.setGeofence(ORINDA_ID, mOrindaGeofence);
        mGeofenceStorage.setGeofence(PITTSBURG_ID, mPittsburgGeofence);
        mGeofenceStorage.setGeofence(PLEASANT_HILL_ID, mPleasantHillGeofence);
        mGeofenceStorage.setGeofence(POWELL_ST_ID, mPowellStGeofence);
        mGeofenceStorage.setGeofence(RICHMOND_ID, mRichmondGeofence);
        mGeofenceStorage.setGeofence(ROCKRIDGE_ID, mRockridgeGeofence);
        mGeofenceStorage.setGeofence(SAN_BRUNO_ID, mSanBrunoGeofence);
        mGeofenceStorage.setGeofence(SAN_FRANCISCO_AIRPORT_ID, mSanFranciscoAirportGeofence);
        mGeofenceStorage.setGeofence(SAN_LEANDRO_ID, mSanLeandroGeofence);
        mGeofenceStorage.setGeofence(SOUTH_HAYWARD_ID, mSouthHaywardGeofence);
        mGeofenceStorage.setGeofence(SOUTH_SAN_FRANCISCO_ID, mSouthSanFranciscoGeofence);
        mGeofenceStorage.setGeofence(UNION_CITY_ID, mUnionCityGeofence);
        mGeofenceStorage.setGeofence(WALNUT_CREEK_ID, mWalnutCreekGeofence);
        mGeofenceStorage.setGeofence(WEST_DUBLIN_ID, mWestDublinGeofence);
        mGeofenceStorage.setGeofence(WEST_OAKLAND_ID, mWestOaklandGeofence);




    }

    @Override
    public void onConnected(Bundle connectionHint) {

        Location loc=LocationServices.FusedLocationApi.getLastLocation(mApiClient);
        Log.d(TAG, "last location " + loc);

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mApiClient, mLocationRequest, this);


    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: " + location.toString());
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // If the error has a resolution, start a Google Play services activity to resolve it.
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                Log.e(TAG, "Exception while resolving connection error.", e);
            }
        } else {
            int errorCode = connectionResult.getErrorCode();
            Log.e(TAG, "Connection to Google Play services failed with error code " + errorCode);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (null != mGeofenceRequestIntent) {
            LocationServices.GeofencingApi.removeGeofences(mApiClient, mGeofenceRequestIntent);
        }
    }

    /**
     * Checks if Google Play services is available.
     * @return true if it is.
     */
    private boolean isGooglePlayServicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Google Play services is available.");
            }
            return true;
        } else {
            Log.e(TAG, "Google Play services is unavailable.");
            return false;
        }
    }


    /**
     * Create a PendingIntent that triggers GeofenceTransitionIntentService when a geofence
     * transition occurs.
     */
    private PendingIntent getGeofenceTransitionPendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }



    public void toggleMenu(View v){
        this.root.toggleMenu();
    }

    public void saveSettings(View v){
        SharedPreferences sp = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("alarmTone", spinner3.getSelectedItem().toString());
        editor.putString("homeStation", spinner1.getSelectedItem().toString());
        editor.putString("awayStation", spinner2.getSelectedItem().toString());
        editor.commit();
        //String xx = sp.getString("alarmTone", "n/a");
       // Log.d(TAG, "sharedpref retrived: " + xx);
        this.root.toggleMenu();
    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedStation;
        SimpleGeofence sg;
        double sg_lat, sg_long;
        TextView mytext = (TextView) view;
        //Toast.makeText(this, mytext.getText(), Toast.LENGTH_LONG).show();

        if (mytext != null) {

            selectedStation = (String) mytext.getText();
            if (selectedStation != "SELECT STATION"){
                sg = mGeofenceStorage.getGeofence(selectedStation);
                sg_lat = sg.getLatitude();
                sg_long = sg.getLongitude();
                CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(sg_lat, sg_long), 13);
                googleMap.animateCamera(center);

            }




//            //start of original code
//            if (mytext.getText() == "Fremont") {
//                Toast.makeText(this, mytext.getText(), Toast.LENGTH_LONG).show();
//                CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(37.557355, -121.9764), 13);
//                googleMap.animateCamera(center);
//
//
//            } else if (mytext.getText() == "Embarcadero") {
//                Toast.makeText(this, mytext.getText(), Toast.LENGTH_LONG).show();
//                CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(37.792976, -122.396742), 13);
//                googleMap.animateCamera(center);
//
//            } else if (mytext.getText() == "Powell St.") {
//                mp = MediaPlayer.create(this, R.raw.pagerbeeps);
//                mp.setLooping(true);
//                mp.start();
//
////                for (int i = 0; i < 100000; i++){
////                 ;
////                }
////                mp.setLooping(false);
//
//                ActionDialogFragment actionFragment = new ActionDialogFragment();
//                //warningFragment.show(getSupportFragmentManager(), "Warning");
//                actionFragment.show(getFragmentManager(), "question");
//            }
//            //end of original code
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


//    @Override
//    public void onDialogPositiveClick(DialogFragment dialog) {
//        mp.setLooping(false);
//
//    }
//
//    @Override
//    public void onDialogNegativeClick(DialogFragment dialog) {
//
//    }

}