package com.mobiledev.topimpamatricks.Keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by maiaphoebedylansamerjan on 4/13/16.
 */
public class MathKeyboardView extends KeyboardView{
    GridView container; // create a scrollView in which you can put all EditTexts
    static int totalEditTexts = 0;


    static final int KEYCODE_OPTIONS = -100;

    static final int KEYCODE_LANGUAGE_SWITCH = -101;
    public final static int CodeLeft= 2190;
    public final static int CodeRight= 2192;
    public final static int CodeDown= 2193;
    public final static int CodeUp=2191;

    public MathKeyboardView(Context context, AttributeSet attrs) {super(context, attrs);
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
        if(key.codes[0]==CodeLeft){
            getOnKeyboardActionListener().addView();
            return true;
        }
        if(key.codes[0]==CodeRight){

        }
        if(key.codes[0]==CodeDown){

        }
        if(key.codes[0]==CodeUp){

        }
        else {
            return super.onLongPress(key);
        }
    }

    /*void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final MathKeyboard keyboard = (MathKeyboard)getKeyboard();
        keyboard.setSpaceIcon(getResources().getDrawable(subtype.getIconResId()));
        invalidateAllKeys();
    }*/
}
