package com.example.mapcovid;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.action.ViewActions.click;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.*;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class OnboardingUITest {
    @Rule public ActivityScenarioRule<Onboarding1> activityScenarioRule
            = new ActivityScenarioRule<>(Onboarding1.class);

    //Test whether the app changes to the subsequent onboarding screen after user hits next button
    @Test
    public void onboardingChangeScreenTest() {
        onView(withId(R.id.button3))
                .perform(click());
        onView(withId(R.id.button4))
                .perform(click());
        onView(withId(R.id.button5))
                .perform(click());
        onView(withId(R.id.button6))
                .perform(click());
    }

}