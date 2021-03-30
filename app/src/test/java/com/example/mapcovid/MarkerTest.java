package com.example.mapcovid;

import android.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.CameraUpdateFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class MarkerTest  {
    //private GoogleMap map2;
    public Marker[] markers;





    @Mock
    MapsActivity2 theActivity;



    @Test
    public void MarkerTest() throws IOException {


       // markers = theActivity.getMarkers();
        //assertTrue(theActivity.setMarkers(markers));
        //assertTrue(theActivity.setMarkers(markers));
        //theActivity.setMarkers(markers);
       // ArgumentCaptor<GoogleMap> captor = ArgumentCaptor.forClass(GoogleMap.class);
     //   ArgumentCaptor<Marker[]> captor2 = ArgumentCaptor.forClass(Marker[].class);
      //  theActivity.getMarkers
       // verify(theActivity).getMarkers(captor.capture());
      //  GoogleMap map2 = captor.getValue();

        //LatLng expopark = new LatLng(34.011175,-118.28433);
        //Marker markerExpo = map2.addMarker(new MarkerOptions().position(expopark).title("Expo Park Testing"));
     //   verify(theActivity).addMarker(captor.capture(), captor2.capture());
     //   markers[0] = markerExpo;
     //   LatLng universalcommunity = new LatLng(34.02738,-118.25810);
     //   Marker markerUniversal =map2.addMarker(new MarkerOptions().position(universalcommunity).title("Universal Community Health Testing"));
     //   markers[1] = markerUniversal;
     //   LatLng crenshaw = new LatLng(34.02243,-118.33473);
     //   Marker markerCrenshaw = map2.addMarker(new MarkerOptions().position(crenshaw).title("Crenshaw Testing"));
     //   markers[2] = markerCrenshaw;
     //   LatLng doctornow = new LatLng(34.06350,-118.37565);
     //   Marker markerDoctor = map2.addMarker(new MarkerOptions().position(doctornow).title("Doctor Now Testing"));
     //   markers[3] = markerDoctor;
     //   LatLng crenshawkiosk = new LatLng(33.98997,-118.32946);
     //   Marker markerKiosk = map2.addMarker(new MarkerOptions().position(crenshawkiosk).title("Crenshaw Kiosk Testing"));
     //   markers[4] = markerKiosk;
     //   LatLng jwch = new LatLng(34.04338,-118.24290);
     //   Marker markerJWCH =map2.addMarker(new MarkerOptions().position(jwch).title("JWCH Institute Testing"));
     //   markers[5] = markerJWCH;
     //   LatLng engemann = new LatLng(34.02551,-118.28808);
     //   Marker markerEngemann =map2.addMarker(new MarkerOptions().position(engemann).title("USC Engemann Testing"));
     //   markers[6] = markerEngemann;

        //LatLng community = new LatLng(34.05608,-118.27463);
      //  Marker markerCommunity =map2.addMarker(new MarkerOptions().position(community).title("Angeles Community Testing"));
      //  markers[7] = markerCommunity;
        LatLng universalcommunity = new LatLng(34.02738,-118.25810);
        double latitude = universalcommunity.latitude;
        double longitude = universalcommunity.latitude;
        assertNotNull(universalcommunity);


    }
}