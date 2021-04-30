package com.example.mapcovid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;
import android.net.Uri;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.maps.android.data.kml.KmlLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.view.View;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import android.content.pm.Signature;

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

    // private static boolean testPermission = false;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap map;
    private CameraPosition cameraPosition;
    private File imagePath;
    private FloatingActionButton share;
    private FloatingActionButton legend;
    private Marker expo;
    private static FusedLocationProviderClient fusedLocationClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(34.05, -118.24);
    private static final int DEFAULT_ZOOM = 15;
    private boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private static Task<Location> lastKnownLocation2;
    private static Location lastKnownLocation;
    private static int SPLASH_TIME_OUT = 4000;
    private BottomNavigationView navView;

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        navView = findViewById(R.id.bottom_navigation);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        /*
        // Construct a PlacesClient
        Places.initialize(getApplicationContext(), "AIzaSyDdvVgxr1ImjzYJxDTBJzkMhyhn7Uo5Ye8");
        placesClient = Places.createClient(this);
        */


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        context = getApplicationContext();


        //Share button
        share = (FloatingActionButton) findViewById(R.id.shareButton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bit = takeScreenshot();
                saveBitmap(bit);
                shareScreen();
            }
        });



        //legend button
        legend = (FloatingActionButton) findViewById(R.id.legendButton);
        legend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = (ImageView) findViewById(R.id.legendPic);
                if (iv.getVisibility() == iv.INVISIBLE)
                    iv.setVisibility(iv.VISIBLE);
                else
                    iv.setVisibility(iv.INVISIBLE);
            }
        });


        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //navView.setSelectedItemId(R.id.mapicon);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Toast.makeText(MapsActivity.this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
                //return true;
                switch (item.getItemId()) {
                    case R.id.mapicon:
                        return true;
                    case R.id.mediaicon:
                        startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.historyicon:
                        Intent hisintent = new Intent(getApplicationContext(), History.class);
                        startActivity(hisintent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.settingsicon:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        
//        String filename = "locations";
//        String fileContents = "{\"locations\":[{\"latitude\":34.0195,\"longitude\":-118.4912,\"time\":1619012120886},{\"latitude\":34.0736,\"longitude\":-118.4004,\"time\":1619123120886}]}";
//        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
//            fos.write(fileContents.getBytes());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
            //KmlLayer layer = new KmlLayer(map, R.raw.colored, getApplicationContext());
            KmlLayer layer = readKML();
            layer.addLayerToMap();
            // Set a listener for geometry clicked events.
            layer.setOnFeatureClickListener(feature -> Toast.makeText(MapsActivity.this,
                    feature.getProperty("name") + "\nCases: " + feature.getProperty("cases") + "\nDeaths: " + feature.getProperty("deaths"),
                    Toast.LENGTH_LONG).show());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Testing Locations
        LatLng usc = new LatLng(34.02024, -118.28083);
        map.moveCamera(CameraUpdateFactory.newLatLng(usc));
        map.setMinZoomPreference(12);
        LatLng expopark = new LatLng(34.011175, -118.28433);
        expo = map.addMarker(new MarkerOptions().position(expopark).title("Expo Park Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng universalcommunity = new LatLng(34.02738, -118.25810);
        map.addMarker(new MarkerOptions().position(universalcommunity).title("Universal Community Health Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng crenshaw = new LatLng(34.02243, -118.33473);
        map.addMarker(new MarkerOptions().position(crenshaw).title("Crenshaw Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng doctornow = new LatLng(34.06350, -118.37565);
        map.addMarker(new MarkerOptions().position(doctornow).title("Doctor Now Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng crenshawkiosk = new LatLng(33.98997, -118.32946);
        map.addMarker(new MarkerOptions().position(crenshawkiosk).title("Crenshaw Kiosk Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng jwch = new LatLng(34.04338, -118.24290);
        map.addMarker(new MarkerOptions().position(jwch).title("JWCH Institute Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng engemann = new LatLng(34.02551, -118.28808);
        map.addMarker(new MarkerOptions().position(engemann).title("USC Engemann Testing").snippet("Hours: Mon-Fri 9-5"));
        LatLng community = new LatLng(34.05608, -118.27463);
        map.addMarker(new MarkerOptions().position(community).title("Angeles Community Testing").snippet("Hours: Mon-Fri 9-5 https://www.angelescommunity.org"));
        LatLng engemannn = new LatLng(34.02551, -118.28808);
        map.addMarker(new MarkerOptions().position(engemannn).title("USC Engemann Testing").snippet("Hours: Mon-Fri 9-5"));


        //check how far the user is
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        float[] distance = new float[2];
        System.out.println("BEEPBOP");
        getLocationPermission();
        System.out.println(PackageManager.PERMISSION_GRANTED);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        System.out.println("THIS IS NOT A DRILL");
        //CancellationToken cancelledToken = null;
        //lastKnownLocation2 = fusedLocationClient.getCurrentLocation(1, cancelledToken);
        //boolean inside;
        // try {
        //    lastKnownLocation = lastKnownLocation2.getResult();

        //} catch (IllegalStateException exception) {
        //  return;
        //}

        System.out.println("DREAMS");


    }


    public static boolean getCB() {
        final boolean[] result = {false};
        LatLng ne = new LatLng(34.4921, -117.4003);
        LatLng sw = new LatLng(33.4233, -118.58);

        LatLngBounds curScreen = new LatLngBounds(sw, ne);
        Location location = lastKnownLocation2.getResult();
        LatLng currLoc = new LatLng(location.getLatitude(), location.getLongitude());
        //LatLng currLoc = new LatLng(35.5, -118);
        boolean cb2 = false;
        cb2 = curScreen.contains(currLoc);

        System.out.println("PLEASE FUCKING WORK: " + cb2);
        return cb2;

//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            // return TODO;
//        }
//        float[] distance = new float[2];
//        lastKnownLocation2 = fusedLocationClient.getLastLocation();
//        lastKnownLocation2.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                location = lastKnownLocation2.getResult();
//                System.out.println("THIS IS A DRILL");
//                // Location.distanceBetween(expo.getPosition().latitude, expo.getPosition().longitude, location.getLatitude(), location.getLongitude(), distance);
//
//                LatLng ne = new LatLng(34.4921, -117.4003);
//                LatLng sw = new LatLng(33.4233, -118.58);
//
//                LatLngBounds curScreen = new LatLngBounds(sw, ne);
//                // Location location = lastKnownLocation2.getResult();
//                LatLng currLoc = new LatLng(location.getLatitude(), location.getLongitude());
//                // LatLng currLoc = new LatLng(35.5, -118);
//
//                if (curScreen.contains(currLoc)) {
//                    result[0] = true;
//                }
//            }
//        });
//        return result[0];
    }

    public KmlLayer readKML() throws IOException, XmlPullParserException {
        Thread myThread = new Thread() {
            public void run() {
                try {
                    int[][] data = getData();
                    boolean success = updateKML(data);
                    if (success) {
                        System.out.println("Update KML Success");
                    } else {
                        System.out.println("Update KML Error");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
        InputStream is = this.openFileInput("legend.kml");
        KmlLayer layer = new KmlLayer(map, is, getApplicationContext());
        // KmlLayer layer = new KmlLayer(map, R.raw.legend, getApplicationContext());
        return layer;
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
                // permission = true;
                // getDeviceLocation();
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
            // permission = false;

        }
       /* Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/
        // [END maps_check_location_permission]
    }


    @Override
    public boolean onMyLocationButtonClick() {
        // Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // getDeviceLocation();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        // getLocationPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return TODO;
        }
        float[] distance = new float[2];
        lastKnownLocation2 = fusedLocationClient.getLastLocation();
        lastKnownLocation2.addOnSuccessListener(new OnSuccessListener<Location>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(Location location) {
                location = lastKnownLocation2.getResult();
                // System.out.println("THIS IS A DRILL");
                // Location.distanceBetween(expo.getPosition().latitude, expo.getPosition().longitude, location.getLatitude(), location.getLongitude(), distance);

                LatLng ne = new LatLng(34.4921, -117.4003);
                LatLng sw = new LatLng(33.4233, -118.58);

                LatLngBounds curScreen = new LatLngBounds(sw, ne);
                // Location location = lastKnownLocation2.getResult();
                LatLng currLoc = new LatLng(location.getLatitude(), location.getLongitude());
                // LatLng currLoc = new LatLng(35.5, -118);

                if (!curScreen.contains(currLoc)) {
                    TextView txtView = (TextView) findViewById(R.id.warningText);
                    txtView.setVisibility(View.VISIBLE);
                } else {
                    // append location to JSON
                    /*
                    JSONObject jo = new JSONObject();
                    JSONArray ja = new JSONArray();
                    Map m = new LinkedHashMap(2);
                    m.put("longitude", location.getLongitude());
                    m.put("latitude", location.getLatitude());
                    long time = System.currentTimeMillis();
                    m.put("time", time);
                    ja.put(m);
                    try {
                        jo.put("locations", ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(jo.toString()); */
                    FileInputStream fis = null;
                    try {
                        fis = context.openFileInput("locations");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(fis, StandardCharsets.UTF_8);
                    StringBuilder stringBuilder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                        String line = reader.readLine();
                        while (line != null) {
                            stringBuilder.append(line).append('\n');
                            line = reader.readLine();
                        }
                    } catch (IOException e) {
                        // Error occurred when opening raw file for reading.
                    } finally {
                        String contents = stringBuilder.toString();
                        //System.out.println(contents);
                        String json = contents;
                        Gson gson = new Gson();
                        JsonObject inputObj  = gson.fromJson(json, JsonObject.class);
                        JsonObject newObject = new JsonObject() ;
                        long time = System.currentTimeMillis();
                        newObject.addProperty("latitude", location.getLatitude());
                        newObject.addProperty("longitude", location.getLongitude());
                        newObject.addProperty("time", time);
                        inputObj.get("locations").getAsJsonArray().add(newObject);
                        System.out.println(inputObj);
                        String filename = "locations";
                        String fileContents = inputObj.toString();
                        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
                            fos.write(fileContents.getBytes());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
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
        Toast.makeText(this, "called getDeviceLocation()", Toast.LENGTH_SHORT).show();
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

    public static int getLocationPermission() {
        return LOCATION_PERMISSION_REQUEST_CODE;
    }

    public static String getTag() {
        return TAG;
    }

    public static String getDefaultLocation() {
        LatLng loc = new LatLng(34.05, -118.24);
        return loc.toString();
    }

    //Screenshot Section
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache();
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        // ContextWrapper cw = new ContextWrapper(getApplicationContext());
        //File temp = cw.getDir("imageDir", Context.MODE_PRIVATE);
        //imagePath = new File(temp, "UniqueFileName" + ".jpg");
        //imagePath = new File(Environment.getExternalStorageDirectory() + "/scrnshot.png");
        imagePath = new File(Environment.getExternalStorageDirectory() + "/Download/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public void shareScreen() {
            /*Uri uri = FileProvider.getUriForFile(
                    MapsActivity.this,
                    "com.example.mapcovid.provider", //(use your app signature + ".provider" )
                    imagePath);*/
        Uri uri = Uri.fromFile(imagePath);

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "Take a look at this LA Covid Map provided by MapCovid!";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "LA County Map");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    public void sendMessage(View view) {
        LatLng usc = new LatLng(34.02024, -118.28083);
        map.moveCamera(CameraUpdateFactory.newLatLng(usc));
        map.setMinZoomPreference(14);
    }

    public int[][] getData() throws IOException {
        Document doc = Jsoup.connect("http://publichealth.lacounty.gov/media/coronavirus/locations.htm").get();
        Elements table = doc.select("table.table.table-striped.table-bordered.table-sm").next();
        // System.out.println(table.html());
        int[][] data = new int[6][4];
        int i = 0, j = 0;
        for (Element e : table.select("tr")) {
            // adams-normandie, exposition park, harvard heights, jefferson park, pico-union, university park
            if (i == 87 || i == 123 || i == 135 || i == 142 || i == 172 || i == 201) {
                System.out.println(e.text());
                String[] split = e.text().split(" ");
                int[] city;
                int l = 0;
                for (int k = split.length - 4; k < split.length; k++) {
                    data[j][l] = Integer.parseInt(split[k]);
                    // System.out.println(data[j][l]);
                    l++;
                }
                j++;
            } else if (i > 340) break;
            i++;
        }
        return data;
    }

    public boolean updateKML(int[][] data) {
        try {
            // File inputFile = new File("android.resource://com.example.mapcovid/raw/legend");
            InputStream stream = this.getResources().openRawResource(R.raw.legend);
            InputSource isource = new InputSource(stream);
            // File inputFile = new File(dir, "legend.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(isource);
            ((org.w3c.dom.Document) doc).getDocumentElement().normalize();
            // System.out.println("Root element :" + ((org.w3c.dom.Document) doc).getDocumentElement().getNodeName());
            NodeList values = ((org.w3c.dom.Document) doc).getElementsByTagName("value");
            System.out.println("----------------------------");
            int city = 0;
            for (int i = 0; i < values.getLength(); i++) {
                Node node = values.item(i); // city stats
                // System.out.print(i + " ");
                // System.out.println(node.getTextContent());
                if (node.getNodeType() == Node.ELEMENT_NODE) { // update data here
                    org.w3c.dom.Element e = (org.w3c.dom.Element) node;
                    if (i == 1 || i == 4 || i == 7 || i == 10 || i == 13 || i == 16) {
                        e.setTextContent(Integer.toString(data[city][0]));
                    } else if (i == 2 || i == 5 || i == 8 || i == 11 || i == 14 || i == 17) {
                        e.setTextContent(Integer.toString(data[city][2]));
                        city++;
                    }
                    // System.out.println(node.getTextContent());
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            // StreamResult result = new StreamResult(new File("../MapCovid/app/src/main/res/raw/legend.kml"));
            // OutputStream os = openFileOutput("legend.kml", Context.MODE_PRIVATE);
            StreamResult result = new StreamResult(new File(this.getFilesDir(), "legend.kml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }



}
