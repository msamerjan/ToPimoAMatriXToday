package com.mobiledev.topimpamatricks;

import android.support.test.rule.ActivityTestRule;

import com.mobiledev.topimpamatricks.Calculator.DetailActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by maiaphoebedylansamerjan on 5/5/16.
 */
public class DetailTest {
    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(DetailActivity.class);

    @Test
    public void testDetailViewExists(){
        onView(withId(R.id.activity_detail_click_view)).check(matches(isDisplayed()));
        onView(withId(R.id.definition_row_webview)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_activity_webview)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_row_textview)).check(matches(isDisplayed()));

    }

    @Test
    public void testInformationCorrect(){
        onData(withId(R.id.))


    }


}
