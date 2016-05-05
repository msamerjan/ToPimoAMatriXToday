package com.mobiledev.topimpamatricks;

import android.support.test.rule.ActivityTestRule;

import com.mobiledev.topimpamatricks.Calculator.GridViewCustomAdapter;

import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by maiaphoebedylansamerjan on 5/5/16.
 */
public class EditDataActivity {
    @Rule
    public ActivityTestRule<EditDataActivity> mActivityRule = new ActivityTestRule<>(EditDataActivity.class);

    @Test
    public void TestonClick(){
        onView(withId(R.id.edit_calculator)).check(matches(isDisplayed()));


    }

    @Test
    public void testAdapterClick(){
    }

    @Test
    public void KeyboardLaunch(){
        onView(withId(R.id.keyboard)).check(matches(isDisplayed()));

    }
}
