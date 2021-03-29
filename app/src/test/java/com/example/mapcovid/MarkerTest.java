package com.example.mapcovid;

import com.google.android.gms.maps.GoogleMap;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MarkerTest {
    private GoogleMap map;
    @Test
    public void MarkerTest() throws IOException {
        MapsActivity2 theActivity = new MapsActivity2();
        theActivity.setMarkers(this.map);
        
    }
}