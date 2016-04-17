package com.mobiledev.topimpamatricks.Keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * Created by maiaphoebedylansamerjan on 4/13/16.
 */
public class MathKeyboardView extends KeyboardView{

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
        } else {
            return super.onLongPress(key);
        }
    }
    /*void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final MathKeyboard keyboard = (MathKeyboard)getKeyboard();
        keyboard.setSpaceIcon(getResources().getDrawable(subtype.getIconResId()));
        invalidateAllKeys();
    }*/
}
