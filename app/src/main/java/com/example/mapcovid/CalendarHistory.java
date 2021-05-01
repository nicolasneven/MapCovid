package com.example.mapcovid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.vo.DateData;

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
        setContentView(R.layout.calendar_layout);
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
                    System.out.println("STAMPS:" + tempStamp);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //highhlight dates that have history
        MCalendarView calendarView2 = ((MCalendarView) findViewById(R.id.calendar2));

        ArrayList<DateData> date_with_history=new ArrayList<>();
        date_with_history.add(new DateData(2021,04,26));
        date_with_history.add(new DateData(2021,04,27));

        for(int i=0;i<date_with_history.size();i++) {
            calendarView2.markDate(date_with_history.get(i).getYear(),date_with_history.get(i).getMonth(),date_with_history.get(i).getDay());//mark multiple dates with this code.
        }

        recyclerView = findViewById(R.id.RecyclerView);

        CalendarView calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                List<String> newlongnums = new ArrayList<>();
                List<String> newlatnums = new ArrayList<>();
                List<String> newdates = new ArrayList<>();
                String mes = "";
                if(month == 0){
                    mes = "Jan";
                }
                if(month == 1){
                    mes = "Feb";
                }
                if(month == 2){
                    mes = "Mar";
                }
                if(month == 3){
                    mes = "Apr";
                }
                if(month == 4){
                    mes = "May";
                }
                if(month == 5){
                    mes = "Jun";
                }
                if(month == 6){
                    mes = "Jul";
                }
                if(month == 7){
                    mes = "Aug";
                }
                if(month == 8){
                    mes = "Sep";
                }
                if(month == 9){
                    mes = "Oct";
                }
                if(month == 10){
                    mes = "Nov";
                }
                if(month == 11){
                    mes = "Dec";
                }
                for(int i = 0; i < dates.size(); i++){
                    System.out.println("MES" + mes);
                    System.out.println("MONTH" + dates.get(i).substring(4,7));
                    if(mes.equals(dates.get(i).substring(4,7)) && String.valueOf(dayOfMonth).equals(dates.get(i).substring(8,10))){
                       //  && String.valueOf(year) == dates.get(i).substring(24,27)
                        System.out.println("BEEP");
                        newlongnums.add(longnums.get(i));
                        newlatnums.add(latnums.get(i));
                        newdates.add(dates.get(i));

                    }
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                System.out.println("A");
                CustomAdapter customAdapter = new CustomAdapter((ArrayList<String>) newlongnums, (ArrayList<String>) newlatnums, (ArrayList<String>) newdates, CalendarHistory.this);
                System.out.println("B");
                recyclerView.setAdapter(customAdapter);
                System.out.println("C");

                //TextView textView3 = findViewById(R.id.textView3);
               // textView3.setText(year + " " + month + "" + dayOfMonth);


            }
        });
    }
    //end of oncreate function


}
