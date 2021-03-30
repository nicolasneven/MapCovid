package com.example.mapcovid;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MapCenterTest {
    @Rule public ActivityScenarioRule<MapsActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MapsActivity.class);

    @Test
    public void centerLocation() throws InterruptedException {
        onView(withId(R.id.map));
        Thread.sleep(10000);
    }
    //If a location permissions popup appears, then grant permission
    //Manually click on center on location botton on the top right
    //The screen should shift to the user's current location represented by a blue circle
}

