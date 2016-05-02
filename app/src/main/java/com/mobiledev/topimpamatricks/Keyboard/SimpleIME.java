package com.mobiledev.topimpamatricks.Keyboard;


import android.app.Activity;
import android.app.Dialog;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.text.InputType;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;

import com.mobiledev.topimpamatricks.R;

import butterknife.Bind;

public class SimpleIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private MathKeyboardView key;
    private MathKeyboard keyboard;
    private MathKeyboard currentKeyboard;
    private Activity mHostActivity;
    private InputMethodManager mInputMethodManager;
    private int mLastDisplayWidth;
    private CompletionInfo[] mCompletions;

    private StringBuilder mComposing = new StringBuilder();
    private long mMetaState;

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

    

    public interface KeyListener{
 void KeyboardActionListener ();
}



    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public void onInitializeInterface() {
        if (keyboard != null) {
            // Configuration changes can happen after the keyboard gets recreated,
            // so we need to be able to re-build the keyboards if the available
            // space has changed.
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
        keyboard = new MathKeyboard(this, R.xml.qwerty);
    }

    @Override
    public View onCreateInputView() {
        key = (MathKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new MathKeyboard(this, R.xml.qwerty);
        key.setKeyboard(keyboard);
        key.setOnKeyboardActionListener(this);
        return key;
    }


   private void setMathKeyboard(MathKeyboard nextKeyboard) {
        final boolean shouldSupportLanguageSwitchKey =
                mInputMethodManager.shouldOfferSwitchingToNextInputMethod(getToken());
        nextKeyboard.setLanguageSwitchKeyVisibility(shouldSupportLanguageSwitchKey);
        key.setKeyboard(nextKeyboard);
    }


    @Override public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);

        // Reset our state.  We want to do this even if restarting, because
        // the underlying state of the text editor could have changed in any way.
        mComposing.setLength(0);
        //updateCandidates();

        if (!restarting) {
            // Clear shift states.
            mMetaState = 0;
        }

        mCompletions = null;

        switch (attribute.inputType & InputType.TYPE_MASK_CLASS) {
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_DATETIME:
               currentKeyboard = keyboard;
                break;

            default:
                currentKeyboard = keyboard;
        }

        // Update the label on the enter key, depending on what the application
        // says it will do.
        currentKeyboard.setImeOptions(getResources(), attribute.imeOptions);
    }


    @Override
    public void onFinishInput() {
        super.onFinishInput();

        // Clear current composing text and candidates.
        mComposing.setLength(0);
        //updateCandidates();

        // We only hide the candidates window when finishing input on
        // a particular editor, to avoid popping the underlying application
        // up and down if the user is entering text into the bottom of
        // its window.
        setCandidatesViewShown(false);

        currentKeyboard = keyboard;
        if (key != null) {
            key.closing();
        }
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        // Apply the selected keyboard to the input view.
        setMathKeyboard(currentKeyboard);
        key.closing();
        final InputMethodSubtype subtype = mInputMethodManager.getCurrentInputMethodSubtype();
        key.setSubtypeOnSpaceKey(subtype);
    }

    @Override
    public void onCurrentInputMethodSubtypeChanged(InputMethodSubtype subtype) {
        key.setSubtypeOnSpaceKey(subtype);
    }


    @Override
    public void onUpdateSelection(int oldSelStart, int oldSelEnd,
                                            int newSelStart, int newSelEnd,
                                            int candidatesStart, int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd,
                candidatesStart, candidatesEnd);

    }


    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (event.getRepeatCount() == 0 && key != null) {
                    if (key.handleBack()) {
                        return true;
                    }
                }
                break;

            case KeyEvent.KEYCODE_DEL:
                if (mComposing.length() > 0) {
                    onKey(Keyboard.KEYCODE_DELETE, null);
                    return true;
                }
                break;

            case KeyEvent.KEYCODE_ENTER:
                return false;

            default:
                //if (PROCESS_HARD_KEYS) {
                   // if (mPredictionOn && translateKeyDown(keyCode, event)) {
                        return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void commitTyped(InputConnection inputConnection) {
        if (mComposing.length() > 0) {
            inputConnection.commitText(mComposing, mComposing.length());
            mComposing.setLength(0);
            //updateCandidates();
        }
    }

    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }

    private void sendKey(int keyCode) {
        switch (keyCode) {
            case '\n':
                keyDownUp(KeyEvent.KEYCODE_ENTER);
                break;
            default:
                if (keyCode >= '0' && keyCode <= '9') {
                    keyDownUp(keyCode - '0' + KeyEvent.KEYCODE_0);
                } else {
                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                }
                break;
        }
    }


    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
        if (focusCurrent == null || focusCurrent.getClass() != EditText.class) return;
        if (primaryCode == MathKeyboard.KEYCODE_DELETE)
            ic.deleteSurroundingText(1, 0);
        else if (primaryCode == MathKeyboard.KEYCODE_DONE) {
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
        } else if (primaryCode == CodeLeft) {
            Selection.setSelection(entry2.getText(), entry2.getSelectionStart());
            entry1.requestFocus();
            Selection.setSelection(entry3.getText(), entry3.getSelectionStart());
            entry2.requestFocus();
            Selection.setSelection(entry4.getText(), entry4.getSelectionStart());
            entry3.requestFocus();
            ic.sendKeyEvent(new KeyEvent(KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_ENTER));
        } else if (primaryCode == CodeRight) {
            Selection.setSelection(entry1.getText(), entry1.getSelectionStart());
            entry2.requestFocus();
            Selection.setSelection(entry2.getText(), entry2.getSelectionStart());
            entry3.requestFocus();
            Selection.setSelection(entry3.getText(), entry3.getSelectionStart());
            entry4.requestFocus();
            ic.sendKeyEvent(new KeyEvent(KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_ENTER));
        } else if (primaryCode == CodeUp) {
            Selection.setSelection(entry3.getText(), entry3.getSelectionStart());
            entry1.requestFocus();
            Selection.setSelection(entry4.getText(), entry4.getSelectionStart());
            entry2.requestFocus();
            ic.sendKeyEvent(new KeyEvent(KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_ENTER));
        } else if (primaryCode == CodeDown) {
            Selection.setSelection(entry1.getText(), entry1.getSelectionStart());
            entry3.requestFocus();
            Selection.setSelection(entry2.getText(), entry2.getSelectionStart());
            entry4.requestFocus();
            ic.sendKeyEvent(new KeyEvent(KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_ENTER));
        } else if (primaryCode == ImaginaryNum) {
            ic.sendKeyEvent(new KeyEvent(-(1), KeyEvent.KEYCODE_ENTER));
        } else {
            char code = (char) primaryCode;
            ic.commitText(String.valueOf(code), 1);
        }
    }




    @Override
    public void onText(CharSequence text) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        ic.beginBatchEdit();
        if (mComposing.length() > 0) {
            commitTyped(ic);
        }
        ic.commitText(text, 0);
        ic.endBatchEdit();
        //updateShiftKeyState(getCurrentInputEditorInfo());
    }

    /*private void updateCandidates() {
        if (!mCompletionOn) {
            if (mComposing.length() > 0) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(mComposing.toString());
                setSuggestions(list, true, true);
            } else {
                setSuggestions(null, false, false);
            }
        }
    }*/

   /* private void handleBackspace() {
        final int length = mComposing.length();
        if (length > 1) {
            mComposing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            updateCandidates();
        } else if (length > 0) {
            mComposing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
            updateCandidates();
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }*/

    /*private void handleShift() {
        if (mInputView == null) {
            return;
        }

        Keyboard currentKeyboard = mInputView.getKeyboard();
        if (keyboard == currentKeyboard) {
            // Alphabet keyboard
            checkToggleCapsLock();
            key.setShifted(mCapsLock || !key.isShifted());
        } else if (currentKeyboard == mQwertyKeyboard) {
            mQwertyKeyboard.setShifted(true);
            setMathKeyboard(mSymbolsShiftedKeyboard);
            mSymbolsShiftedKeyboard.setShifted(true);
        } else if (currentKeyboard == mSymbolsShiftedKeyboard) {
            mSymbolsShiftedKeyboard.setShifted(false);
            setLatinKeyboard(mSymbolsKeyboard);
            mSymbolsKeyboard.setShifted(false);
        }
    }*/

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (key.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
        }
        /*if (isAlphabet(primaryCode) && mPredictionOn) {
            mComposing.append((char) primaryCode);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            updateShiftKeyState(getCurrentInputEditorInfo());
            updateCandidates();
        } else {*/
            getCurrentInputConnection().commitText(
                    String.valueOf((char) primaryCode), 1);
        }
    private void handleClose() {
        commitTyped(getCurrentInputConnection());
        requestHideSelf(0);
        key.closing();
    }

    private IBinder getToken() {
        final Dialog dialog = getWindow();
        if (dialog == null) {
            return null;
        }
        final Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }
        return window.getAttributes().token;
    }
    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {
        handleClose();

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

}
