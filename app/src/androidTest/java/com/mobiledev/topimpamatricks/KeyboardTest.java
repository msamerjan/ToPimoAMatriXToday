package com.mobiledev.topimpamatricks;

import android.support.test.rule.ActivityTestRule;

import com.mobiledev.topimpamatricks.Calculator.CalculatorActivity;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by maiaphoebedylansamerjan on 4/20/16.
 */
public class KeyboardTest {

    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityRule = new ActivityTestRule<>(CalculatorActivity.class);

    @Test
    public void TestonClick(){

    }

    @Test
    public void KeyboardLaunch(){
        /*
        // Type text and then press the button.
        onView(withId(R.id.))
                .perform(typeText(STRING_TO_BE_TYPED), closeKeyboard());
        onView(withId(R.id.number_input_calculator)).perform(click());

        // Check that the text was changed.*/

    }
}
