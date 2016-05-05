package com.mobiledev.topimpamatricks;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by maiaphoebedylansamerjan on 4/20/16.
 */
public class MatrixTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testMatrixExists() {

        onView(withId(R.id.activity_main_digital_matrix)).check(matches(isDisplayed()));

        onView(withId(R.id.new_matrix_button)).check(matches(isDisplayed()));

        onView(withId(R.id.activity_keyboard_icon)).check(matches(isDisplayed()));



        pressBack();
    }

    @Test
    public void matrixChangedCorrectly(){
        

    }


}
