package com.example.mapcovid;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SettingsLocationTest {

    @Rule public ActivityScenarioRule<SettingsActivity> activityScenarioRule
            = new ActivityScenarioRule<>(SettingsActivity.class);

    @Test
    public void locationButtonTest() {
        onView(withId(R.id.switch1))
                .perform(click());
        onView(withId(R.id.switch1))
                .perform(click());
    }

}