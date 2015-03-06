package com.finalproject.erwin.bartgeoalarm;
import android.app.Activity;
import android.graphics.Color;
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

import static com.finalproject.erwin.bartgeoalarm.Constants.ALARM_OFF;
import static com.finalproject.erwin.bartgeoalarm.Constants.ALARM_ON;
import static com.finalproject.erwin.bartgeoalarm.Constants.CIVICCENTER_ID;
import static com.finalproject.erwin.bartgeoalarm.Constants.CIVICCENTER_LATITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.CIVICCENTER_LONGITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.CIVICCENTER_RADIUS_METERS;
import static com.finalproject.erwin.bartgeoalarm.Constants.EMBARCADERO_ID;
import static com.finalproject.erwin.bartgeoalarm.Constants.EMBARCADERO_LATITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.EMBARCADERO_LONGITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.EMBARCADERO_RADIUS_METERS;
import static com.finalproject.erwin.bartgeoalarm.Constants.FREMONT_ID;
import static com.finalproject.erwin.bartgeoalarm.Constants.FREMONT_LATITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.FREMONT_LONGITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.FREMONT_RADIUS_METERS;
import static com.finalproject.erwin.bartgeoalarm.Constants.TAG;
import static com.finalproject.erwin.bartgeoalarm.Constants.ANDROID_BUILDING_ID;
import static com.finalproject.erwin.bartgeoalarm.Constants.ANDROID_BUILDING_LATITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.ANDROID_BUILDING_LONGITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.ANDROID_BUILDING_RADIUS_METERS;
import static com.finalproject.erwin.bartgeoalarm.Constants.CONNECTION_FAILURE_RESOLUTION_REQUEST;
import static com.finalproject.erwin.bartgeoalarm.Constants.GEOFENCE_EXPIRATION_TIME;
import static com.finalproject.erwin.bartgeoalarm.Constants.TAG;
import static com.finalproject.erwin.bartgeoalarm.Constants.YERBA_BUENA_ID;
import static com.finalproject.erwin.bartgeoalarm.Constants.YERBA_BUENA_LATITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.YERBA_BUENA_LONGITUDE;
import static com.finalproject.erwin.bartgeoalarm.Constants.YERBA_BUENA_RADIUS_METERS;

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

//public class MainActivity extends ActionBarActivity implements OnMapReadyCallback,GooglePlayServicesClient.ConnectionCallbacks,
//GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks , LocationListener, GoogleMap.OnMarkerClickListener,
//        AdapterView.OnItemSelectedListener {
public class MainActivity extends ActionBarActivity implements GooglePlayServicesClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks , LocationListener, GoogleMap.OnMarkerClickListener,
        AdapterView.OnItemSelectedListener {

    private GoogleMap googleMap;
    FlyOutContainer root;
    private Spinner spinner;
    private Spinner spinner1;
    private Spinner spinner2;
    private String[] stations = {"Select station", "Change Embarcadero", "Change Powell", "Change Fremont", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000"};
    //int alarmOn = 0;

    // Internal List of Geofence objects. In a real app, these might be provided by an API based on
    // locations within the user's proximity.
    List<Geofence> mGeofenceList;

    // These will store hard-coded geofences in this sample app.
    private SimpleGeofence mAndroidBuildingGeofence;
    private SimpleGeofence mYerbaBuenaGeofence;
    private SimpleGeofence mFremontGeofence;
    private SimpleGeofence mEmbarcaderoGeofence;
    private SimpleGeofence mCivicCenterGeofence;

    // Persistent storage for geofences.
    private SimpleGeofenceStore mGeofenceStorage;

    private LocationServices mLocationService;
    // Stores the PendingIntent used to request geofence monitoring.
    private PendingIntent mGeofenceRequestIntent;
    private GoogleApiClient mApiClient;




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

        //mApiClient.connect();

        // Instantiate a new geofence storage area.
        mGeofenceStorage = new SimpleGeofenceStore(this);
        // Instantiate the current List of geofences.
        mGeofenceList = new ArrayList<Geofence>();
        createGeofences();


        //setContentView(R.layout.activity_main);
        googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

//        MapFragment mapFrag = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
//        if (savedInstanceState == null) {
//            mapFrag.getMapAsync(this);
//        }

        initialMapDisplay();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stations);

        spinner = (Spinner) findViewById(R.id.id_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner1 = (Spinner) findViewById(R.id.id_spinner1);
        spinner1.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.id_spinner2);
        spinner2.setAdapter(adapter);


    }



    @Override
    protected void onStart() {
        super.onStart();
        mApiClient.connect();
    }




//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(37.754006, -122.197273));
//        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
//        googleMap.moveCamera(center);
//        googleMap.animateCamera(zoom);
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.803664, -122.271604)).title("12th St. Oakland City Center"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.765062, -122.419694)).title("16th St. Mission"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.80787, -122.269029)).title("19th St. Oakland"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.752254, -122.418466)).title("24th St. Mission"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.853024, -122.26978)).title("Ashby"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.72198087, -122.4474142)).title("Balboa Park"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.697185, -122.126871)).title("Bay Fair"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.690754, -122.075567)).title("Castro Valley"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.779528, -122.413756)).title("Civic Center/UN Plaza"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.754006, -122.197273)).title("Coliseum/Oakland Airport"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.792976, -122.396742)).title("Embarcadero"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.557355, -121.9764)).title("Fremont"));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.784991, -122.406857)).title("Powell"));
//        //googleMap.addMarker(new MarkerOptions().position(new LatLng(37.784991, -122.406857)).title("Walnut Creek"));
//
//        googleMap.setOnMarkerClickListener(this);
//    }



    public void initialMapDisplay() {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(37.754006, -122.197273));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.803664, -122.271604)).title("12th St. Oakland City Center").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.765062, -122.419694)).title("16th St. Mission"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.80787, -122.269029)).title("19th St. Oakland"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.752254, -122.418466)).title("24th St. Mission"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.853024, -122.26978)).title("Ashby"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.72198087, -122.4474142)).title("Balboa Park"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.697185, -122.126871)).title("Bay Fair"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.690754, -122.075567)).title("Castro Valley"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.779528, -122.413756)).title("Civic Center/UN Plaza"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.754006, -122.197273)).title("Coliseum/Oakland Airport"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.792976, -122.396742)).title("Embarcadero"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.557355, -121.9764)).title("Fremont"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.784991, -122.406857)).title("Powell"));
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(37.784991, -122.406857)).title("Walnut Creek"));


        googleMap.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        LatLng point = marker.getPosition();
        String title = marker.getTitle();

//        CircleOptions circleOptions = new CircleOptions()
//                .center(point)   //set center
//                .radius(500)   //set radius in meters
//                .fillColor(Color.TRANSPARENT)  //default
//                .strokeColor(Color.BLUE)
//                .strokeWidth(7);
//
//        googleMap.addCircle(circleOptions);


        Log.d(TAG, "onMarkerClick LatLng: " + point);
        double latlat = point.latitude;
        double longlong = point.longitude;
        Log.d(TAG, "latlat: " + latlat);
        Log.d(TAG, "longlong: " + longlong);

        String geoIDFromLatLong = mGeofenceStorage.getIDFromLatLong(Double.toString(point.latitude) + Double.toString(point.longitude));
        Log.d(TAG, "geoIDfromLatLong: " + geoIDFromLatLong);

        SimpleGeofence clickedSimpleGeofence = mGeofenceStorage.getGeofence(geoIDFromLatLong);
        int currentAlarmStatus = clickedSimpleGeofence.getAlarmStatus();
        Log.d(TAG, "current alarm status is: " + currentAlarmStatus);
//        clickedSimpleGeofence.setAlarmStatus(ALARM_ON);
//        int newAlarmStatus = clickedSimpleGeofence.getAlarmStatus();
//        Log.d(TAG, "new alarm status is: " + newAlarmStatus);


        float hue;

        if (currentAlarmStatus == 0){
            hue = BitmapDescriptorFactory.HUE_GREEN;
            //alarmOn = 1;
            clickedSimpleGeofence.setAlarmStatus(ALARM_ON); //probaby need to call shared pref to update. call setGeofence
            mGeofenceStorage.setGeofence(geoIDFromLatLong, new SimpleGeofence(geoIDFromLatLong,latlat,longlong,EMBARCADERO_RADIUS_METERS,GEOFENCE_EXPIRATION_TIME,Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,ALARM_ON));


        }
        else {
            hue = BitmapDescriptorFactory.HUE_RED;
            //alarmOn = 0;
            clickedSimpleGeofence.setAlarmStatus(ALARM_OFF); //probably need to call shared pref to update. call setGeofence
            mGeofenceStorage.setGeofence(geoIDFromLatLong, new SimpleGeofence(geoIDFromLatLong,latlat,longlong,EMBARCADERO_RADIUS_METERS,GEOFENCE_EXPIRATION_TIME,Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,ALARM_OFF));
        }

        marker.remove();



        googleMap.addMarker(new MarkerOptions()
                .position(point)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(hue)));
                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
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
//        mYerbaBuenaGeofence = new SimpleGeofence(
//                YERBA_BUENA_ID,                // geofenceId.
//                YERBA_BUENA_LATITUDE,
//                YERBA_BUENA_LONGITUDE,
//                YERBA_BUENA_RADIUS_METERS,
//                GEOFENCE_EXPIRATION_TIME,
//                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
//        );

        mEmbarcaderoGeofence = new SimpleGeofence(
                EMBARCADERO_ID,                // geofenceId
                EMBARCADERO_LATITUDE,
                EMBARCADERO_LONGITUDE,
                EMBARCADERO_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );

        mCivicCenterGeofence = new SimpleGeofence(
                CIVICCENTER_ID,                // geofenceId
                CIVICCENTER_LATITUDE,
                CIVICCENTER_LONGITUDE,
                CIVICCENTER_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT,
                ALARM_OFF
        );



        // Store these flat versions in SharedPreferences and add them to the geofence list.
        mGeofenceStorage.setGeofence(ANDROID_BUILDING_ID, mAndroidBuildingGeofence);
//        mGeofenceStorage.setGeofence(YERBA_BUENA_ID, mYerbaBuenaGeofence);
//        mGeofenceStorage.setGeofence(FREMONT_ID,mFremontGeofence);
        mGeofenceStorage.setGeofence(EMBARCADERO_ID, mEmbarcaderoGeofence);
        mGeofenceStorage.setGeofence(CIVICCENTER_ID, mCivicCenterGeofence);
        mGeofenceList.add(mAndroidBuildingGeofence.toGeofence());
//        mGeofenceList.add(mYerbaBuenaGeofence.toGeofence());


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



        // Get the PendingIntent for the geofence monitoring request.
        // Send a request to add the current geofences.
        mGeofenceRequestIntent = getGeofenceTransitionPendingIntent();
        LocationServices.GeofencingApi.addGeofences(mApiClient, mGeofenceList,
                mGeofenceRequestIntent);
        //Toast.makeText(this, getString(R.string.start_geofence_service), Toast.LENGTH_SHORT).show();

        //finish();

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





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView mytext = (TextView) view;
        //Toast.makeText(this, mytext.getText(), Toast.LENGTH_LONG).show();

        if (mytext.getText() == "Change Fremont") {
            Toast.makeText(this, mytext.getText(), Toast.LENGTH_LONG).show();
            mGeofenceList.add(mFremontGeofence.toGeofence());
            CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(37.557355, -121.9764),13);
            //CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
            //googleMap.moveCamera(center);
            googleMap.animateCamera(center);
            //root.toggleMenu();

        }
        else if (mytext.getText() == "Change Embarcadero") {
            Toast.makeText(this, mytext.getText(), Toast.LENGTH_LONG).show();
            CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(37.792976, -122.396742),13);
            googleMap.animateCamera(center);
            //root.toggleMenu();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}