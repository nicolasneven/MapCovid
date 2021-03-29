package com.example.mapcovid;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import org.mockito.Mock;
import org.mockito.Mockito;
import android.view.View;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetTest {
    @Mock
    Media Tweet;

    //Checks if the WebView is displaying the correct Twitter URL
    @Test
    public void correctTwitterURLTest() {
        Tweet = new Media();
        String s;
        s = Tweet.getURL();
        assertEquals(s, "https://twitter.com/charlesoxyer/timelines/1373342013463130117?ref_src=twsrc%5Etfw");

    }

    //Tests to make sure the filtered stream for the timeline receives the right string of rules
    @Test
    public void filteredStreamRulesTest() {
        Tweet = new Media();
        String s;
        s = Tweet.displayRules();
        assertEquals(s, "\"(covid OR Coronavirus OR Covid-19 OR Covid19) AND (Los Angeles)\", \"covid news in LA\"");

    }
}

