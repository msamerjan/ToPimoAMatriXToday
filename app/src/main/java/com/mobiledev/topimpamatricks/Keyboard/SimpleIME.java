package com.mobiledev.topimpamatricks.Keyboard;


import android.app.Activity;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.text.Selection;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mobiledev.topimpamatricks.R;

import butterknife.Bind;

public class SimpleIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private MathKeyboardView key;
    private MathKeyboard keyboard;
    private Activity mHostActivity;

    public final static int CodePrev= 55000;
    public final static int CodeLeft= 2190;
    public final static int CodeRight= 2192;
    public final static int CodeDown= 2193;
    public final static int CodeUp=2191;
    public final static int CodeClear= 55006;
    public final static int SignChange= 95;
    public final static int ImaginaryNum= 69;

    @Bind(R.id.entry1)
    EditText entry1;
    @Bind(R.id.entry2)
    EditText entry2;
    @Bind(R.id.entry3)
    EditText entry3;
    @Bind(R.id.entry4)
    EditText entry4;



   RelativeLayout containerLayout;
    static int totalEditTexts = 0;


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
        if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) return;
        switch(primaryCode){
            case MathKeyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);
                break;
            case MathKeyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case CodeLeft:
                Selection.setSelection(entry2.getText(), entry2.getSelectionStart());
                entry1.requestFocus();
                Selection.setSelection(entry3.getText(), entry3.getSelectionStart());
                entry2.requestFocus();
                Selection.setSelection(entry4.getText(), entry4.getSelectionStart());
                entry3.requestFocus();
            case CodeRight:
                Selection.setSelection(entry1.getText(), entry1.getSelectionStart());
                entry2.requestFocus();
                Selection.setSelection(entry2.getText(), entry2.getSelectionStart());
                entry3.requestFocus();
                Selection.setSelection(entry3.getText(), entry3.getSelectionStart());
                entry4.requestFocus();
            case CodeUp:
                Selection.setSelection(entry3.getText(), entry3.getSelectionStart());
                entry1.requestFocus();
                Selection.setSelection(entry4.getText(), entry4.getSelectionStart());
                entry2.requestFocus();
            case CodeDown:
                Selection.setSelection(entry1.getText(), entry1.getSelectionStart());
                entry3.requestFocus();
                Selection.setSelection(entry2.getText(), entry2.getSelectionStart());
                entry4.requestFocus();
            case ImaginaryNum:

            default:
                char code = (char)primaryCode;
                ic.commitText(String.valueOf(code),1);
        }

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public View onCreateInputView() {
        key = (MathKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new MathKeyboard(this, R.xml.qwerty);
        key.setKeyboard(keyboard);
        key.setOnKeyboardActionListener(this);
        return key;
    }

    public void addView(View v){
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

}
