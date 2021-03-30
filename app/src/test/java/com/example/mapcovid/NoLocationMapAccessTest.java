package com.example.mapcovid;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NoLocationMapAccessTest {

    @Test
    public void noLocationMapAccessTest() {
        //test to see if the map opens up on a location even if location pref is off
        //can test by using default location
        String location = MapsActivity.getDefaultLocation();
        assertNotNull(location);


    }


}