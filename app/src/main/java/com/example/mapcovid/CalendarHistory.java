package com.example.mapcovid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CalendarHistory extends AppCompatActivity {

    //arrays with all the tracked locations
    List<String> longnums;
    List<String> latnums;
    List<String> dates;
    
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

        //read the values from location history file
        Context context = getApplicationContext();
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
            //IOException error
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

                for(int i=0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String tempStamp = object.getString("time");
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
        recyclerView = findViewById(R.id.RecyclerView);
    }
    //end of oncreate function

}
