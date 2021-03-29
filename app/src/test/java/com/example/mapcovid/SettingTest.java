package com.example.mapcovid;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SettingTest {

    @Test
    public void loadWebsite() throws IOException {
        assertTrue(WebScraper.loadWebsite());
    }

    @Test
    public void printData() throws IOException {
        assertTrue(WebScraper.printData());
    }

}