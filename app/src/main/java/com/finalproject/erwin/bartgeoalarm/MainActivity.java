package com.finalproject.erwin.bartgeoalarm;
import android.app.Activity;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements OnMapReadyCallback,GooglePlayServicesClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks , LocationListener, GoogleMap.OnMarkerClickListener,
        AdapterView.OnItemSelectedListener {

    private GoogleMap googleMap;
    FlyOutContainer root;
    private Spinner spinner;
    private String[] stations = {"Change Embarcadero", "Change Powell", "Change Fremont"};

    // Internal List of Geofence objects. In a real app, these might be provided by an API based on
    // locations within the user's proximity.
    List<Geofence> mGeofenceList;

    // These will store hard-coded geofences in this sample app.
    private SimpleGeofence mAndroidBuildingGeofence;
    private SimpleGeofence mYerbaBuenaGeofence;
    private SimpleGeofence mFremontGeofence;

    // Persistent storage for geofences.
    private SimpleGeofenceStore mGeofenceStorage;

    private LocationServices mLocationService;
    // Stores the PendingIntent used to request geofence monitoring.
    private PendingIntent mGeofenceRequestIntent;
    private GoogleApiClient mApiClient;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView mytext = (TextView) view;
        //Toast.makeText(this, mytext.getText(), Toast.LENGTH_LONG).show();

        if (mytext.getText() == "Change Fremont") {
            Toast.makeText(this, mytext.getText(), Toast.LENGTH_LONG).show();
            mGeofenceList.add(mFremontGeofence.toGeofence());
//            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(37.557355, -121.9764));
//            CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
//            googleMap.moveCamera(center);
            //googleMap.animateCamera(zoom);
            //googleMap.moveCamera(center);
            //googleMap.animateCamera(zoom);
        }
//        else if (mytext.getText() == "Change Embarcadero") {
//            Toast.makeText(this, mytext.getText(), Toast.LENGTH_LONG).show();
//            CameraUpdate center = CameraUpdateFactory.newLatLngZoom(new LatLng(37.792976, -122.396742),13);
//            //CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
//            //googleMap.moveCamera(center);
//            googleMap.animateCamera(center);
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


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
        //googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

        MapFragment mapFrag = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        if (savedInstanceState == null) {
            mapFrag.getMapAsync(this);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stations);

        spinner = (Spinner) findViewById(R.id.id_spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }



//    @Override
//    protected void onStart() {
//        super.onStart();
//        mApiClient.connect();
//    }
//



    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(37.754006, -122.197273));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.803664, -122.271604)).title("12th St. Oakland City Center"));
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
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
        );
        mYerbaBuenaGeofence = new SimpleGeofence(
                YERBA_BUENA_ID,                // geofenceId.
                YERBA_BUENA_LATITUDE,
                YERBA_BUENA_LONGITUDE,
                YERBA_BUENA_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
        );

        mFremontGeofence = new SimpleGeofence(
                FREMONT_ID,                // geofenceId
                FREMONT_LATITUDE,
                FREMONT_LONGITUDE,
                FREMONT_RADIUS_METERS,
                GEOFENCE_EXPIRATION_TIME,
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT
        );

        // Store these flat versions in SharedPreferences and add them to the geofence list.
        mGeofenceStorage.setGeofence(ANDROID_BUILDING_ID, mAndroidBuildingGeofence);
        mGeofenceStorage.setGeofence(YERBA_BUENA_ID, mYerbaBuenaGeofence);
        mGeofenceStorage.setGeofence(FREMONT_ID,mFremontGeofence);
        mGeofenceList.add(mAndroidBuildingGeofence.toGeofence());
        mGeofenceList.add(mYerbaBuenaGeofence.toGeofence());
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
        finish();

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