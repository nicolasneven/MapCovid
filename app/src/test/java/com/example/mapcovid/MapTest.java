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

import com.google.maps.android.data.kml.KmlLayer;

import org.mockito.runners.MockitoJUnitRunner;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapTest {

    /*
    @Mock
    MapsActivity mapsObj;
    //Checks if the location tracking button is successfully created in the setting screen
    @Test
    public void kmlReadTest() throws IOException, XmlPullParserException {
        mapsObj = new MapsActivity();
        KmlLayer testLayer;
        testLayer = mapsObj.readKML();
        assertNotNull(testLayer);
    }
     */
    @Test
    public void tagsTest(){
        assertNotNull(MapsActivity.getTag());
    }

    @Mock
    MapsActivity mapsObj;
    @Test
    public void onMyLocationTest(){
        mapsObj = new MapsActivity();
        boolean test = mapsObj.onMyLocationButtonClick();
        assertFalse(test);
    }

}
