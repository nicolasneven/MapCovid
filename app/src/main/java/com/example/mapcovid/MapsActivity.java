package com.example.mapcovid;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.android.data.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap map;
    private CameraPosition cameraPosition;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(34.05, -118.24);
    private static final int DEFAULT_ZOOM = 15;
    private boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_location_demo);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
        // getDeviceLocation();

        // USC Neighborhood Polygons
        try {
            KmlLayer layer = new KmlLayer(map, R.raw.colored, getApplicationContext());
            layer.addLayerToMap();
            // Set a listener for geometry clicked events.
            layer.setOnFeatureClickListener(feature -> Toast.makeText(MapsActivity.this,
                    feature.getProperty("name") + " Case Rate: " + feature.getProperty("data"),
                    Toast.LENGTH_LONG).show());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Testing Locations
        LatLng losangeles = new LatLng(34.05, -118.24);
        map.moveCamera(CameraUpdateFactory.newLatLng(losangeles));
        map.setMinZoomPreference(10);
        LatLng expopark = new LatLng(34.011175,-118.28433);
        map.addMarker(new MarkerOptions().position(expopark).title("Expo Park Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng universalcommunity = new LatLng(34.02738,-118.25810);
        map.addMarker(new MarkerOptions().position(universalcommunity).title("Universal Community Health Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng crenshaw = new LatLng(34.02243,-118.33473);
        map.addMarker(new MarkerOptions().position(crenshaw).title("Crenshaw Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng doctornow = new LatLng(34.06350,-118.37565);
        map.addMarker(new MarkerOptions().position(doctornow).title("Doctor Now Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng crenshawkiosk = new LatLng(33.98997,-118.32946);
        map.addMarker(new MarkerOptions().position(crenshawkiosk).title("Crenshaw Kiosk Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng jwch = new LatLng(34.04338,-118.24290);
        map.addMarker(new MarkerOptions().position(jwch).title("JWCH Institute Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng engemann = new LatLng(34.02551,-118.28808);
        map.addMarker(new MarkerOptions().position(engemann).title("USC Engemann Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng community = new LatLng(34.05608,-118.27463);
        map.addMarker(new MarkerOptions().position(community).title("Angeles Community Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng engemannn = new LatLng(34.02551,-118.28808);
        map.addMarker(new MarkerOptions().position(engemannn).title("USC Engemann Testing").snippet("Hours: Mon-Fri 9-5"));

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
                // getDeviceLocation();
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        getDeviceLocation();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    // [START maps_check_location_permission_result]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
            // getDeviceLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            // permissionDenied = true;
            // [END_EXCLUDE]
        }
    }
    // [END maps_check_location_permission_result]

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");

    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "called getDeviceLocation()");
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
}

/*
public class MapsActivity0 extends FragmentActivity implements OnMapReadyCallback {

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
     *
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // USC Neighborhood Polygons
        try {
            KmlLayer layer = new KmlLayer(mMap, R.raw.colored, getApplicationContext());
            layer.addLayerToMap();
            // Set a listener for geometry clicked events.
            layer.setOnFeatureClickListener(feature -> Toast.makeText(MapsActivity0.this,
                    feature.getProperty("name") + " Case Rate: " + feature.getProperty("data"),
                    Toast.LENGTH_LONG).show());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Testing Locations
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
} */