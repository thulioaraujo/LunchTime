package com.codechallenge.dbserver.lunchtime.views;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.codechallenge.dbserver.lunchtime.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by thulioaraujo on 1/19/2017.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    ActivityTestRule<LoginActivity> activityTestRule;

    @Before
    public void setUp() throws Exception {
        activityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    }

    @Test
    public void checkIfFieldsAreDisplayed() {
        activityTestRule.launchActivity(new Intent());
        onView(withId(R.id.input_email)).check(matches(isDisplayed()));
    }
}