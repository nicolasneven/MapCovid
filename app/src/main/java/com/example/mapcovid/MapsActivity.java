package com.example.mapcovid;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng losangeles = new LatLng(34.05, -118.24);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(losangeles));
        mMap.setMinZoomPreference(10);
        LatLng expopark = new LatLng(34.011175,-118.28433);
        mMap.addMarker(new MarkerOptions().position(expopark).title("Expo Park Testing"));
        LatLng universalcommunity = new LatLng(34.02738,-118.25810);
        mMap.addMarker(new MarkerOptions().position(universalcommunity).title("Universal Community Health Testing"));
        LatLng crenshaw = new LatLng(34.02243,-118.33473);
        mMap.addMarker(new MarkerOptions().position(crenshaw).title("Crenshaw Testing"));
        LatLng doctornow = new LatLng(34.06350,-118.37565);
        mMap.addMarker(new MarkerOptions().position(doctornow).title("Doctor Now Testing"));
        LatLng crenshawkiosk = new LatLng(33.98997,-118.32946);
        mMap.addMarker(new MarkerOptions().position(crenshawkiosk).title("Crenshaw Kiosk Testing"));
        LatLng jwch = new LatLng(34.04338,-118.24290);
        mMap.addMarker(new MarkerOptions().position(jwch).title("JWCH Institute Testing"));
    }
}