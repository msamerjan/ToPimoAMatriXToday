package com.mobiledev.topimpamatricks;

import android.inputmethodservice.Keyboard;
import android.support.test.rule.ActivityTestRule;
import android.view.KeyEvent;

import com.mobiledev.topimpamatricks.Calculator.CalculatorActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by maiaphoebedylansamerjan on 4/20/16.
 */
public class KeyboardTest {

    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityRule = new ActivityTestRule<>(CalculatorActivity.class);

    @Test
    public void TestonClick(){
        onView(withId(R.id.edit_calculator)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_calculator)).perform(pressKey(Keyboard.KEYCODE_DONE));
        onView(withId(R.id.edit_calculator)).perform(pressKey(Keyboard.KEYCODE_DELETE));
        onView(withId(R.id.edit_calculator)).perform(pressKey(Keyboard.KEYCODE_MODE_CHANGE));
        onView(withId(R.id.edit_calculator)).perform(pressKey(Keyboard.KEYCODE_SHIFT));
        onView(withId(R.id.edit_calculator)).perform(pressKey(KeyEvent.ACTION_UP));
        onView(withId(R.id.edit_calculator)).perform(pressKey(KeyEvent.ACTION_DOWN));
    }

    @Test
    public void KeyboardLaunch(){
    onView(withId(R.id.keyboard)).check(matches(isDisplayed()));


    }
}
