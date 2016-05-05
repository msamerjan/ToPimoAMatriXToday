package com.mobiledev.topimpamatricks;

import android.support.test.rule.ActivityTestRule;

import com.mobiledev.topimpamatricks.Calculator.CalculatorActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by maiaphoebedylansamerjan on 5/5/16.
 */
public class CalculatorTest {
    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityRule = new ActivityTestRule<>(CalculatorActivity.class);

    @Test
    public void testCalcuatorExists() {

        onView(withId(R.id.edit_calculator)).check(matches(isDisplayed()));
        //onView(withId(R.id.))

    }

    @Test
    public void testMathCorrect(){
        //onData(withId())
    }

}
