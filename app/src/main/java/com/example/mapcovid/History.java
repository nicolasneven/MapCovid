package com.example.mapcovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class History extends AppCompatActivity {

    String s1[], s2[];
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setSelectedItemId(R.id.historyicon);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mapicon:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mediaicon:
                        startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.historyicon:
                        return true;
                    case R.id.settingsicon:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

//        Button clearbutton = (Button) findViewById(R.id.clearhistory);
//
//        clearbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), PopActivity.class);
//                startActivity(i);
//            }
//        });

//        LatLng ne = new LatLng(34.4921, -117.4003);
//        LatLng sw = new LatLng(33.4233, -118.58);
//
//        LatLngBounds curScreen = new LatLngBounds(ne, sw);
//
//        //Location current = MapsActivity.historyLoc();
//
//        LatLng current = new LatLng(34.4233, -118.00);
//
//        //LatLng currLoc = new LatLng(current.getLatitude(), current.getLongitude());
//
//        //boolean inside = curScreen.contains(currLoc);
        Switch locSwitch = (Switch) findViewById(R.id.locswitch);
        Boolean inside = locSwitch.isChecked();
//        boolean inside = curScreen.contains(current);
//
        if (!inside){
            Intent i = new Intent(getApplicationContext(), PopActivity.class);
            startActivity(i);
        }

        recycler_view = findViewById(R.id.recycler_view);

        s1 = getResources().getStringArray(R.array.locations);
        s2 = getResources().getStringArray(R.array.days);

        MyAdapter myAdapter = new MyAdapter(this, s1, s2);
        recycler_view.setAdapter(myAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }


}