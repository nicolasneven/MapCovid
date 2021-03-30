package com.example.mapcovid;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

@RunWith(JUnit4.class)

public class regTest{

    @Rule
    public ActivityScenarioRule rule =
            new ActivityScenarioRule<>(MapsActivity.class);


    @Test
    public void myTest() {
        ActivityScenario scenario = rule.getScenario();
        //scenario.onActivity()
        // Your test code goes here.
    }

}
