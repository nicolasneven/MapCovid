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
public class OnboardingUITest2 {
    @Rule public ActivityScenarioRule<Onboarding5> activityScenarioRule
            = new ActivityScenarioRule<>(Onboarding5.class);

    //Tests switching from onboarding to the main map screen
    @Test
    public void onboardingScreentoMapTest() {
        onView(withId(R.id.button7))
                .perform(click());
    }

}