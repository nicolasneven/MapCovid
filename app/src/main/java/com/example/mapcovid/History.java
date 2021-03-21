package com.example.mapcovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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

        navView.setSelectedItemId(R.id.activity_history);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.activity_maps:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.activity_media:
                        startActivity(new Intent(getApplicationContext(), MediaActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.activity_history:
                        return true;
                    case R.id.settings_activity:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        recycler_view = findViewById(R.id.recycler_view);

        s1 = getResources().getStringArray(R.array.locations);
        s2 = getResources().getStringArray(R.array.days);

        MyAdapter myAdapter = new MyAdapter(this, s1, s2);
        recycler_view.setAdapter(myAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }
}