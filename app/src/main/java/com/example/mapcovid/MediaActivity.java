package com.example.mapcovid;

import androidx.annotation.NonNull;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;
import android.text.Html;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.app.Activity;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatDelegate;

public class MediaActivity extends AppCompatActivity {

    Typeface weatherIcons;
    TextView city,dt,humidity,pressure, currentTemp, weatherIco, update;
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

        if(CheckNetwork.isInternetAvailable(MediaActivity.this)) //returns true if internet available
        {}
        else
        {
            Toast.makeText(MediaActivity.this,"No Internet Connection", Toast.LENGTH_LONG).show();
        }
        //weather shit
        weatherIcons = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weathericons.ttf");
        city = (TextView)findViewById(R.id.city_field);
        update = (TextView)findViewById(R.id.updated_field);
        dt = (TextView)findViewById(R.id.details_field);
        currentTemp = (TextView)findViewById(R.id.current_temperature_field);
        humidity = (TextView)findViewById(R.id.humidity_field);
        pressure = (TextView)findViewById(R.id.pressure_field);
        weatherIco = (TextView)findViewById(R.id.weather_icon);
        weatherIco.setTypeface(weatherIcons);

        Weather.placeIdTask asyncTask = new Weather.placeIdTask(new Weather.AsyncResponse() {

            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {
                city.setText(weather_city);
                update.setText(weather_updatedOn);
                dt.setText(weather_description);
                currentTemp.setText(weather_temperature);
                humidity.setText("Humidity: "+weather_humidity);
                pressure.setText("Pressure: "+weather_pressure);
                weatherIco.setText(Html.fromHtml(weather_iconText));
                if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
                    city.setTextColor(Color.WHITE);
                    update.setTextColor(Color.WHITE);
                    dt.setTextColor(Color.WHITE);
                    currentTemp.setTextColor(Color.WHITE);
                    humidity.setTextColor(Color.WHITE);
                    pressure.setTextColor(Color.WHITE);
                    weatherIco.setTextColor(Color.WHITE);
                }

            }
        });
        asyncTask.execute("34.0488","-118.2518");
        //end of weather shit
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