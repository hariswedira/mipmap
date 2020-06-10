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

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mainActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void testAppInfo() throws InterruptedException {
        onView(withId(R.id.username_login)).perform(typeText("petetz"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.password_login)).perform(typeText("petetz"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(1000);
    }


}