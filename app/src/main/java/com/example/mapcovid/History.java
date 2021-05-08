package com.example.mapcovid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    List<String> longnums;
    List<String> latnums;
    List<String> dates;
    List<String> unixval;
    RecyclerView recyclerView;
    private Button btngocalendar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        btngocalendar = (Button) findViewById(R.id.btngocalendar);
        btngocalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarHistory.class);
                startActivity(intent);
            }
        });

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

        //read the values from location history file and add them all to the correct arrays

        Context context = getApplicationContext();
        displayLocations(context);

        Spinner dropdown = findViewById(R.id.spinner1);


        //onclick the one you want to delete:
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select a date to delete")){
                    //do nothing
                }
                else{
                    //show confirmation if possible
                    //find the associated unixval
                    //rewrite the file with the one less location
                    String filename = "locations";
                    String fileContents = "{\"locations\":[";
                    for(int i=0; i<dates.size(); i++){
                        if (i != position-1) {
                            fileContents += "{\"latitude\":" + latnums.get(i) + ",\"longitude\":" + longnums.get(i) + ",\"time\":" + unixval.get(i) +"}";
                            if (i != dates.size()-1 && position != dates.size()){
                                fileContents += ",";
                            }
                            else if(i != dates.size()-2 && position == dates.size()){
                                fileContents += ",";
                            }
                        }
                    }
                    fileContents += "]}";
                    try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
                        fos.write(fileContents.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //read file again and display history again
                    //redisplay the dropdown
                    displayLocations(context);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button clearhistory = (Button) findViewById(R.id.clearhistory);
        clearhistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //clear all three lists
                dates.clear();
                latnums.clear();
                longnums.clear();
                unixval.clear();

                //do layout thing again
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                CustomAdapter customAdapter = new CustomAdapter((ArrayList<String>) longnums, (ArrayList<String>) latnums, (ArrayList<String>) dates, History.this);
                recyclerView.setAdapter(customAdapter);

                Spinner dropdown = findViewById(R.id.spinner1);
                String items[] = new String[dates.size()+1];
                items[0] = "Select a date to delete";
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, items);
                dropdown.setAdapter(adapter);

                String filename = "locations";
                String fileContents = "{\"locations\":[]}";
                try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
                    fos.write(fileContents.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void displayLocations(Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput("locations");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(inputStreamReader)){
            String line = reader.readLine();
            while(line != null){
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        }
        catch(IOException e){

        }
        finally {
            String contents = stringBuilder.toString();
            String tempcontents = contents.substring(13, contents.length()-2);

            JSONArray array = null;
            try {
                array = new JSONArray(tempcontents);
                longnums = new ArrayList<>();
                latnums = new ArrayList<>();
                dates = new ArrayList<>();
                unixval = new ArrayList<>();

                for(int i=0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    String tempStamp = object.getString("time");
                    unixval.add(tempStamp);
                    long timeStamp = Long.parseLong(tempStamp);
                    java.util.Date time= new java.util.Date((long)timeStamp);
                    tempStamp = time + "";
                    longnums.add(object.getString("longitude"));
                    latnums.add(object.getString("latitude"));
                    dates.add(tempStamp);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        Spinner dropdown = findViewById(R.id.spinner1);
        String items[] = new String[dates.size()+1];
        items[0] = "Select a date to delete";
        for(int j =1;j<=dates.size();j++){
            items[j] = dates.get(j-1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        recyclerView = findViewById(R.id.RecyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomAdapter customAdapter = new CustomAdapter((ArrayList<String>) longnums, (ArrayList<String>) latnums, (ArrayList<String>) dates, History.this);
        recyclerView.setAdapter(customAdapter);

    }




}