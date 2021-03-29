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
public class OnboardingTest {
    @Mock
    Onboarding1 oboard;
    //Checks if Button is successfully created in onboarding screens
    @Test
    public void onboardingButtonTest(){
        oboard = new Onboarding1();
        Button b;
        b = oboard.createButton();
        assertNotNull(b);

    }
}
