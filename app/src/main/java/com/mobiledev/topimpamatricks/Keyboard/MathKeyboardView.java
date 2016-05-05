package com.mobiledev.topimpamatricks.Keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodSubtype;

import com.mobiledev.topimpamatricks.R;

import static android.support.v4.content.res.ResourcesCompat.getDrawable;

/**
 * Created by maiaphoebedylansamerjan on 4/13/16.
 */
public class MathKeyboardView extends KeyboardView {


    static final int KEYCODE_OPTIONS = -100;

    static final int KEYCODE_LANGUAGE_SWITCH = -101;


    public MathKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MathKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean onLongPress(Key key) {
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        }
        /*if (key.codes[0] == CodeLeft) {
            getOnKeyboardActionListener().onArrowPressed();
            return true;
        }
        if (key.codes[0] == CodeRight) {

        }
        if (key.codes[0] == CodeDown) {

        }
        if (key.codes[0] == CodeUp) {

        }*/

        else {
            return super.onLongPress(key);
        }
    }


 /* public class GridAddition extends Activity {
        RelativeLayout containerLayout;
         int totalEditTexts = 0;

        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            setContentView(R.layout.edit_text_calculator_activity);
            containerLayout = (RelativeLayout)findViewById(R.id.edit_calculator);
        }

        public void onArrowPressed() {
            totalEditTexts++;
            if (totalEditTexts > 100)
                return;
            EditText editText = new EditText(this);
            containerLayout.addView(editText);
            editText.setGravity(Gravity.RIGHT);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) editText.getLayoutParams();
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            editText.setLayoutParams(layoutParams);
            //if you want to identify the created editTexts, set a tag, like below
            editText.setTag("EditText" + totalEditTexts);

        }
    }*/

    void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final MathKeyboard keyboard = (MathKeyboard)getKeyboard();
        keyboard.setSpaceIcon(getDrawable(getResources(), R.drawable.space_bard, null));
        invalidateAllKeys();
    }
}
