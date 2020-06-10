package org.d3ifcool.cubeacon.activities;

import org.d3ifcool.cubeacon.R;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mainActivityTestRule = new ActivityTestRule<SignUpActivity>(SignUpActivity.class);

    @Test
    public void testAppInfo() throws InterruptedException {

        onView(withId(R.id.signup_username)).perform(typeText("petetz"));
        Thread.sleep(1000);
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signup_password)).perform(typeText("petetz"));
        Thread.sleep(1000);
        Espresso.closeSoftKeyboard();
        Thread.sleep(1000);
        onView(withId(R.id.btn_signup_user)).perform(click());
        Thread.sleep(1000);

    }

}