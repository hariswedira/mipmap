package org.d3ifcool.cubeacon;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class EventActivityTest {

//        @Rule
//        public ActivityTestRule<activities> mainActivityTestRule= new ActivityTestRule<activities>(activities.class);
//
//        @Test
//        public void testAppInfo() throws InterruptedException {
//
//            \\ Test create account
//            onView(withId(R.id.signup_username_field)).perform(typeText("petet"));Thread.sleep(1000);
//            Espresso.closeSoftKeyboard();
//            onView(withId(R.id.signup_password_field)).perform(typeText("petet"));Thread.sleep(1000);
//            Espresso.closeSoftKeyboard();
//            Thread.sleep(1000);
//            onView(withId(R.id.btn_signup_user)).perform(click());
//            Thread.sleep(1000);
//
//            \\ Test login
//            onView(withId(R.id.text_input_username)).perform(typeText("petet"));
//            Espresso.closeSoftKeyboard();
//            onView(withId(R.id.text_input_password)).perform(typeText("petet"));
//            Espresso.closeSoftKeyboard();
//            onView(withId(R.id.btn_login)).perform(click());
//            Thread.sleep(1000);
//
//            \\ Test Event
//            Thread.sleep(15000);
//            onView(withId(R.id.btn_see_detail)).perform(click());
//            Thread.sleep(3000);
//
//   		 \\ Test View List Event
//            onView(withId(R.id.list_events)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click())); Thread.sleep(3000);
//
//   		 \\ Test View Beacon Information
//            onView(isRoot()).perform(pressBack()); Thread.sleep(1000);
//            onView(isRoot()).perform(pressBack()); Thread.sleep(1000);
//            onView(withId(R.id.pin_bcn_one)).perform(click()); Thread.sleep(3000);
//
//   		 \\ Test Button Card Event
//            onView(withId(R.id.btn_cls_card_event)).perform(click());
//            Thread.sleep(3000);
//        }
//
//
//\\ Test Search Room
//        onView(withId(R.id.iv_search_room)).perform(click());Thread.sleep(3000);
//
//   	 \\ Test Chooser Room List
//        onView(withId(R.id.list_room)).perform(RecyclerViewActions.actionOnItemAtPosition(4,click()));
//        Thread.sleep(3000);
//
//   	 \\ Test Button Infro on Room
//        onView(withId(R.id.btn_info_room)).perform(click());Thread.sleep(3000);
//
//   	 \\ Test Button View Schedule
//        onView(withId(R.id.btn_jadwal)).perform(click());Thread.sleep(3000);
//
//   	 \\ Test Show Direction
//        onView(isRoot()).perform(pressBack());Thread.sleep(1000);
//        onView(isRoot()).perform(pressBack());Thread.sleep(1000);
//        onView(withId(R.id.btn_direction_nav)).perform(click());Thread.sleep(3000);
//
//   	 \\ Test Start Navigation
//        onView(withId(R.id.btn_start_nav)).perform(click());Thread.sleep(30000);
//
//   	 \\ Test Navigation Finish
//        onView(withId(R.id.btn_finish_nav)).perform(click());Thread.sleep(1000);
//
//     \\ Test signout
//        onView(isRoot()).perform(pressBack()); Thread.sleep(1000);
//        onView(withId(R.id.iv_signal)).perform(click());Thread.sleep(1000);
//        onView(withId(R.id.signout)).perform(click());Thread.sleep(1000);


    @Rule
    public ActivityTestRule<EventActivity> mainActivityTestRule= new ActivityTestRule<EventActivity>(EventActivity.class);

    @Test
    public void testAppInfo() throws InterruptedException {

        onView(withId(R.id.iv_signal)).perform(click());Thread.sleep(1000);
        onView(withId(R.id.feedback)).perform(click());Thread.sleep(1000);
        onView(withId(R.id.et_username_profile)).perform(typeText("petetz"));Thread.sleep(1000);
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_password_profile)).perform(typeText("petetz"));Thread.sleep(1000);
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_update_profile)).perform(click());Thread.sleep(1000);
        onView(isRoot()).perform(pressBack()); Thread.sleep(1000);

//        Thread.sleep(15000);
//        onView(withId(R.id.iv_search_room)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.list_room)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
//        Thread.sleep(3000);
//        onView(withId(R.id.btn_direction_nav)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.btn_start_nav)).perform(click());Thread.sleep(30000);
//        onView(withId(R.id.btn_finish_nav)).perform(click());Thread.sleep(1000);


//        onView(withId(R.id.btn_info_room)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.btn_jadwal)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.pin_bcn_one)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.btn_cls_card_event)).perform(click());Thread.sleep(3000);
//        Thread.sleep(5000);
//        onView(isRoot()).perform(pressBack());Thread.sleep(1000);
//        onView(isRoot()).perform(pressBack());Thread.sleep(1000);
//        onView(withId(R.id.pin_bcn_one)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.btn_cls_card_event)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.iv_search_room)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.list_room)).perform(RecyclerViewActions.actionOnItemAtPosition(4,click()));
//        Thread.sleep(3000);
//        onView(withId(R.id.btn_info_room)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.btn_jadwal)).perform(click());Thread.sleep(3000);
//        onView(isRoot()).perform(pressBack());Thread.sleep(1000);
//        onView(isRoot()).perform(pressBack());Thread.sleep(1000);
//        onView(withId(R.id.btn_direction_nav)).perform(click());Thread.sleep(3000);
//        onView(withId(R.id.btn_start_nav)).perform(click());Thread.sleep(30000);
//        onView(withId(R.id.btn_finish_nav)).perform(click());Thread.sleep(1000);
//        onView(isRoot()).perform(pressBack());Thread.sleep(10000);
    }

//    Espresso.onView(withId(R.id.btn_focus)).perform(click());
//        Espresso.onView(withId(R.id.ed_title_task)).perform(typeText(titleTask),closeSoftKeyboard());
//        Thread.sleep(3000);
//        Espresso.onView(isRoot()).perform(pressBack());
//    // tips
//        Espresso.onView(withId(R.id.btn_article)).perform(click());
//        Thread.sleep(5000);
//        Espresso.onView(withId(R.id.list_articles)).perform(click());
//        Thread.sleep(5000);
//        Espresso.onView(isRoot()).perform(pressBack());
//        Espresso.onView(isRoot()).perform(pressBack());
//    // app info
//        Espresso.onView(withId(R.id.btn_schedule)).perform(click());
//        Thread.sleep(5000);
//        Espresso.onView(isRoot()).perform(pressBack());
//    // play music
//        Espresso.onView(withId(R.id.btn_music)).perform(click());
//        Espresso.onView(withId(R.id.button3)).perform(click());
//        Thread.sleep(7000);
//        Espresso.onView(withId(R.id.button7)).perform(click());
//        Thread.sleep(5000);
//        Espresso.onView(withId(R.id.button8)).perform(click());
//        Thread.sleep(5000);
//        Espresso.onView(isRoot()).perform(pressBack());

}