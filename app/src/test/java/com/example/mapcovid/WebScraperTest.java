package com.example.mapcovid;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WebScraperTest {

    @Test
    public void WebScraperTest() throws IOException {
        assertTrue(WebScraper.test());
    }
}