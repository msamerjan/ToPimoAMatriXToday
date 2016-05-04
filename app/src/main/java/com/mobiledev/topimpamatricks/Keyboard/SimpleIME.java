package com.mobiledev.topimpamatricks.Keyboard;


import android.app.Activity;
import android.app.Dialog;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.text.InputType;
import android.text.Selection;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyCharacterMap;
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

    static final boolean PROCESS_HARD_KEYS = true;

    private MathKeyboardView key;
    private MathKeyboard mathKeyboard;
    private MathKeyboard qwertyKeyboard;

    private MathKeyboard currentKeyboard;

    private String space;
    private Activity mHostActivity;
    private InputMethodManager mInputMethodManager;
    private int mLastDisplayWidth;
    private CompletionInfo[] mCompletions;

    private StringBuilder mComposing = new StringBuilder();
    private long mMetaState;
    private boolean mCapsLock;
    private long mLastShiftTime;

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


    @Override
    public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        space = getResources().getString(R.string.word_separators);
    }

    @Override
    public void onInitializeInterface() {
        if (mathKeyboard != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
        mathKeyboard = new MathKeyboard(this, R.xml.math_symbols);
        qwertyKeyboard=new MathKeyboard(this,R.xml.qwerty);
    }

    @Override
    public View onCreateInputView() {
        key = (MathKeyboardView) getLayoutInflater().inflate( R.layout.edit_text_calculator_activity, null);

        key.setOnKeyboardActionListener(this);
        key.setKeyboard(mathKeyboard);

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
               currentKeyboard = mathKeyboard;
                /*int variation= attribute.inputType & InputType.TYPE_MASK_VARIATION;
                    if(variation==InputType.TYPE_NUMBER_FLAG_DECIMAL||variation==InputType.TYPE_NUMBER_FLAG_SIGNED){

                    }*/
                break;
            case InputType.TYPE_CLASS_TEXT:
                currentKeyboard=qwertyKeyboard;

            default:
                currentKeyboard = mathKeyboard;
                updateShiftKeyState(attribute);

        }
        currentKeyboard.setImeOptions(getResources(), attribute.imeOptions);
    }


    @Override
    public void onFinishInput() {
        super.onFinishInput();

        mComposing.setLength(0);

        setCandidatesViewShown(false);

        currentKeyboard = mathKeyboard;
        if (key != null) {
            key.closing();
        }
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
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


    private boolean translateKeyDown(int keyCode, KeyEvent event) {
        mMetaState = MetaKeyKeyListener.handleKeyDown(mMetaState,
                keyCode, event);
        int c = event.getUnicodeChar(MetaKeyKeyListener.getMetaState(mMetaState));
        mMetaState = MetaKeyKeyListener.adjustMetaAfterKeypress(mMetaState);
        InputConnection ic = getCurrentInputConnection();
        if (c == 0 || ic == null) {
            return false;
        }

        boolean dead = false;
        if ((c & KeyCharacterMap.COMBINING_ACCENT) != 0) {
            dead = true;
            c = c & KeyCharacterMap.COMBINING_ACCENT_MASK;
        }

        if (mComposing.length() > 0) {
            char accent = mComposing.charAt(mComposing.length() -1 );
            int composed = KeyEvent.getDeadChar(accent, c);
            if (composed != 0) {
                c = composed;
                mComposing.setLength(mComposing.length()-1);
            }
        }

        onKey(c, null);

        return true;
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
                if (PROCESS_HARD_KEYS) {
                    if (translateKeyDown(keyCode, event)) {
                        return true;
                    }
                }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (PROCESS_HARD_KEYS) {
                mMetaState = MetaKeyKeyListener.handleKeyUp(mMetaState,
                        keyCode, event);
        }

        return super.onKeyUp(keyCode, event);
    }

    private void commitTyped(InputConnection inputConnection) {
        if (mComposing.length() > 0) {
            inputConnection.commitText(mComposing, mComposing.length());
            mComposing.setLength(0);
        }
    }

    private void updateShiftKeyState(EditorInfo attr) {
        if (attr != null
                && key != null && qwertyKeyboard == key.getKeyboard()) {
            int caps = 0;
            EditorInfo ei = getCurrentInputEditorInfo();
            if (ei != null && ei.inputType != InputType.TYPE_NULL) {
                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
            }
            key.setShifted(mCapsLock || caps != 0);
        }
    }

    private boolean isAlphabet(int code) {
        if (Character.isLetter(code)) {
            return true;
        } else {
            return false;
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
        if (isWordSeparator(primaryCode)) {
            // Handle separator
            if (mComposing.length() > 0) {
                commitTyped(getCurrentInputConnection());
            }
            sendKey(primaryCode);
            updateShiftKeyState(getCurrentInputEditorInfo());
        } else if (primaryCode == MathKeyboard.KEYCODE_DELETE) {
            handleBackspace();
        } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {
        handleShift();
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
        handleClose();
        return;
         } else if (primaryCode == MathKeyboardView.KEYCODE_LANGUAGE_SWITCH) {
        handleLanguageSwitch();
        return;
            } else if (primaryCode == MathKeyboardView.KEYCODE_OPTIONS) {
        // Show a menu or somethin'
            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE
            && key != null) {
        Keyboard current = key.getKeyboard();
        if (current == qwertyKeyboard) {
            setMathKeyboard(mathKeyboard);
        } else {
            setMathKeyboard(qwertyKeyboard);
        }
    } else if (primaryCode == MathKeyboard.KEYCODE_DONE) {
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
            handleCharacter(primaryCode, keyCodes);
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

   private void handleBackspace() {
        final int length = mComposing.length();
        if (length > 1) {
            mComposing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(mComposing, 1);
        } else if (length > 0) {
            mComposing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    private void handleShift() {
        if (key == null) {
            return;
        }

        Keyboard currentKeyboard = key.getKeyboard();
        if (mathKeyboard == currentKeyboard) {
            mathKeyboard.setShifted(false);
            setMathKeyboard(mathKeyboard);
            mathKeyboard.setShifted(false);
        } else if (currentKeyboard == qwertyKeyboard) {
            checkToggleCapsLock();
            key.setShifted(mCapsLock || !key.isShifted());
        }
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (key.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
        }
        if (isAlphabet(primaryCode)) {
            mComposing.append((char) primaryCode);
            getCurrentInputConnection().setComposingText(mComposing, 1);
            updateShiftKeyState(getCurrentInputEditorInfo());

        } else {
            getCurrentInputConnection().commitText(
                    String.valueOf((char) primaryCode), 1);
        }
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

    private void handleLanguageSwitch() {
        mInputMethodManager.switchToNextInputMethod(getToken(), false /* onlyCurrentIme */);
    }

    private void checkToggleCapsLock() {
        long now = System.currentTimeMillis();
        if (mLastShiftTime + 800 > now) {
            mCapsLock = !mCapsLock;
            mLastShiftTime = 0;
        } else {
            mLastShiftTime = now;
        }
    }

    private String getWordSeparators() {
        return space;
    }

    public boolean isWordSeparator(int code) {
        String separators = getWordSeparators();
        return separators.contains(String.valueOf((char)code));
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
