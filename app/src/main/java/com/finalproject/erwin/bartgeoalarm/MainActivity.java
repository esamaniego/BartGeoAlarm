package com.finalproject.erwin.bartgeoalarm;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements OnMapReadyCallback{

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

        MapFragment mapFrag = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        if (savedInstanceState == null) {
            mapFrag.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(37.792976, -122.396742));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.792976, -122.396742)).title("Embarcadero"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(37.784991, -122.406857)).title("Powell"));
    }


}