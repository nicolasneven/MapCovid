package com.example.mapcovid;

import android.content.Context;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.web.model.Atom;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.*;
import org.junit.runner.RunWith;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.web.webdriver.DriverAtoms.clearElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
import androidx.test.espresso.web.webdriver.DriverAtoms;
import androidx.test.espresso.web.webdriver.Locator;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TwitterUITest {
    @Rule
    public ActivityTestRule<MediaActivity> mActivityRule =
            new ActivityTestRule<MediaActivity>(MediaActivity.class, false, true) {
                @Override
                protected void afterActivityLaunched() {
                    onWebView(withId(R.id.timeline_webview)).forceJavascriptEnabled();
                }
            };
    @Test
    public void testScroll() {
        onView(withId(R.id.timeline_webview))
                //.withElement(findElement(Locator.ID,"container"))
                .perform(ViewActions.swipeUp());
    }
}