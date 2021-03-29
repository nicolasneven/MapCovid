package com.example.mapcovid;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LocationPermissionTest {

    @Test
    public void locationPermissionTest() {
        assertTrue(MapsActivity.getLocationPermission()==1);
    }

    @Test
    public void defaultLocationTest() {
        String location = MapsActivity.getDefaultLocation();
        // System.out.println(location);
        assertTrue(MapsActivity.getDefaultLocation().equals("lat/lng: (34.05,-118.24)"));
    }
}