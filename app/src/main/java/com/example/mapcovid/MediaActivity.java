package com.example.mapcovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatDelegate;

public class MediaActivity extends AppCompatActivity {
    public static final String TAG = "TimeLineActivity";

    private static final String baseURl = "http://twitter.com";

    private static final String widgetInfo = "<a class=\"twitter-timeline\" href=\"https://twitter.com/charlesoxyer/timelines/1373342013463130117?ref_src=twsrc%5Etfw\">MapCovid - Curated tweets by charlesoxyer</a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";
    private static final String widgetInfo2 = "<a class=\"twitter-timeline\" data-theme=\"dark\" href=\"https://twitter.com/charlesoxyer/timelines/1373342013463130117?ref_src=twsrc%5Etfw\">MapCovid - Curated tweets by charlesoxyer</a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";
    private static final String widgetLink = "https://twitter.com/charlesoxyer/timelines/1373342013463130117?ref_src=twsrc%5Etfw";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setSelectedItemId(R.id.mediaicon);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mapicon:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.mediaicon:
                        return true;
                    case R.id.historyicon:
                        startActivity(new Intent(getApplicationContext(), History.class));
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

        WebView webView = (WebView) findViewById(R.id.timeline_webview);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            webView.loadDataWithBaseURL(baseURl, widgetInfo2, "text/html", "UTF-8", null);
        }
        else
            webView.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

}