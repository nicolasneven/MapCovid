package com.example.mapcovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    List<Float> longnums;
    List<Float> latnums;
    List<String> dates;
    RecyclerView recyclerView;

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

        //SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        boolean isChecked = MapsActivity.getCB();

        if(!isChecked) {
            Intent i = new Intent(getApplicationContext(), PopActivity.class);
            startActivity(i);
        }


        recyclerView = findViewById(R.id.RecyclerView);
        longnums = new ArrayList<>();
        longnums.add((float) 118.009);
        longnums.add((float) 128.009);
        longnums.add((float) 114.009);
        longnums.add((float) 138.009);
        longnums.add((float) 148.009);
        longnums.add((float) 113.009);

        latnums = new ArrayList<>();
        latnums.add((float) 30.009);
        latnums.add((float) 40.009);
        latnums.add((float) 34.009);
        latnums.add((float) 30.409);
        latnums.add((float) 33.009);
        latnums.add((float) 44.009);

        dates = new ArrayList<>();
        dates.add("March 26,2020");
        dates.add("March 22,2020");
        dates.add("March 18,2020");
        dates.add("April 26,2020");
        dates.add("March 8,2020");
        dates.add("March 12,2020");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomAdapter customAdapter = new CustomAdapter((ArrayList<Float>) longnums, (ArrayList<Float>) latnums, (ArrayList<String>) dates, History.this);
        recyclerView.setAdapter(customAdapter);

        Button clearhistory = (Button) findViewById(R.id.clearhistory);
        clearhistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //clear all three lists
                dates.clear();
                latnums.clear();
                longnums.clear();

                //do layout thing again
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                CustomAdapter customAdapter = new CustomAdapter((ArrayList<Float>) longnums, (ArrayList<Float>) latnums, (ArrayList<String>) dates, History.this);
                recyclerView.setAdapter(customAdapter);
            }
        });

    }


}