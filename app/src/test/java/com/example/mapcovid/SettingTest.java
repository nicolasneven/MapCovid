package com.example.mapcovid;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.mockito.Mock;
import org.mockito.Mockito;
import android.view.View;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SettingTest {
    @Mock
    SettingsActivity settingbObj;
    //Checks if the location tracking button is successfully created in the setting screen
    @Test
    public void locatonButtonTest(){
        settingbObj = new SettingsActivity();
        Button a;
        a = settingbObj.createLocationButton();
        assertNotNull(a);
    }

    @Mock
    SettingsActivity settingbObj2;
    //Checks if the location tracking button is successfully created in the setting screen
    @Test
    public void notificationButtonTest(){
        settingbObj2 = new SettingsActivity();
        Button b;
        b = settingbObj2.createNotificationButton();
        assertNotNull(b);
    }

}
