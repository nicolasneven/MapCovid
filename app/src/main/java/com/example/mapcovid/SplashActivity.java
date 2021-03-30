package com.example.mapcovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.content.SharedPreferences;
import android.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = SplashActivity.this.getSharedPreferences("pref",0);
        boolean firstRun = pref.getBoolean("firstRun", true);
        if(firstRun)
        {
            try {
                SharedPreferences.Editor editor= pref.edit();
                Log.i("onCreate: ","first time" );
                editor.putBoolean("firstRun",false);
                editor.commit();
                onBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Log.i("onCreate: ","second time");
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    Intent homeIntent = new Intent(SplashActivity.this, MapsActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);

        }
        setContentView(R.layout.splash_screen);

    }

    public boolean onBoard() throws IOException {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(SplashActivity.this, Onboarding1.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
        return true;
    }

    public boolean onMaps() throws IOException{
        Log.i("onCreate: ","second time");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(SplashActivity.this, MapsActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
        return true;
    }
}