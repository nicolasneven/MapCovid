package com.example.mapcovid;

import static org.junit.Assert.*;
import org.junit.Test;
import com.google.android.gms.maps.GoogleMap;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MapTest {

    @Test
    public void MapCreateTest(){
        GoogleMap googleMap;
        MapsActivity.onMapReady(googleMap);
        //assertTrue(WebScraper.test());
    }
}