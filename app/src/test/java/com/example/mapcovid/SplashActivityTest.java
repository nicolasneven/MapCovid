package com.example.mapcovid;

import android.content.Intent;
import android.content.SharedPreferences;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SplashActivityTest {

    @Mock
    SplashActivity splash;

    //test whether this shows up before the onboarding
    @Test
    public void testOnBoarding() throws IOException {

        splash = new SplashActivity();
        boolean s = splash.onBoard();

        SharedPreferences pref = splash.getSharedPreferences("pref",0);
        boolean firstRun = pref.getBoolean("firstRun", true);
        if (firstRun){
            assertTrue(s);
        }
        else{
            assertFalse(s);
        }
    }

    //test whether this shows up before the maps
    @Test
    public void testTime() throws IOException{
        splash = new SplashActivity();
        boolean s = splash.onMaps();

        SharedPreferences pref = splash.getSharedPreferences("pref",0);
        boolean firstRun = pref.getBoolean("firstRun", false);
        if (firstRun){
            assertTrue(s);
        }
        else{
            assertFalse(s);
        }
    }
}