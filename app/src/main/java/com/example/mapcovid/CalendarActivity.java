package com.example.mapcovid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    private TextView thedate;
    private Button btngocalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        thedate = (TextView) findViewById(R.id.date);
        btngocalendar = (Button) findViewById(R.id.btngocalendar);

        Intent incoming = getIntent();
        String date = incoming.getStringExtra("date");
        thedate.setText(date);

        btngocalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarHistory.class);
                startActivity(intent);
            }
        });
    }

}