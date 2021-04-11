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
public class LocationPermissionTest {
    @Rule public ActivityScenarioRule<MapsActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MapsActivity.class);

    // Loads the map into view for 10 seconds before shutting application
    @Test
    public void locationPermission() throws InterruptedException {
        onView(withId(R.id.map));
        Thread.sleep(20000);
    }

    // To reset permissions go to the Android settings app and navigate to apps then MapCovid.
    // Change location permission to deny. To view changes, remove app via multi-tasking and click force stop.
    // Running the test should give you a popup asking for location permissions. If permissions are granted,
    // you a center-on-location button will appear in the top right and clicking on it will center on the
    // user's location. If the location does not appear right away switching tabs via the nav-bar will fix this emulator bug.
}

