package com.example.keyboard.keyboard;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.AbstractInputMethodService;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethod;

public class BraPolarKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView kv;
    private Keyboard keyboard;

    private boolean isCaps = false;
    private String charEspecial = "~`|•√π÷×¶∆£¥$¢^°={}\\%©®™✓[]";

    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);
        keyboard = new Keyboard(this,R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        if (primaryCode<-99){
            int code = Math.abs(primaryCode + 100);
            ic.commitText(String.valueOf(charEspecial.charAt(code)),1);
        }
        else{
            switch (primaryCode){
                case -6:
                    keyboard = new Keyboard(this,R.xml.extra);
                    kv.setKeyboard(keyboard);
                    kv.setOnKeyboardActionListener(this);
                    break;
                case -10:
                    keyboard = new Keyboard(this,R.xml.numbers);
                    kv.setKeyboard(keyboard);
                    kv.setOnKeyboardActionListener(this);
                    break;
                case -11:
                    keyboard = new Keyboard(this,R.xml.extra);
                    kv.setKeyboard(keyboard);
                    kv.setOnKeyboardActionListener(this);
                    break;
                case -8:
                    keyboard = new Keyboard(this,R.xml.especial);
                    kv.setKeyboard(keyboard);
                    kv.setOnKeyboardActionListener(this);
                    break;
                case -9:
                    keyboard = new Keyboard(this,R.xml.qwerty);
                    kv.setKeyboard(keyboard);
                    kv.setOnKeyboardActionListener(this);
                    break;
                case Keyboard.KEYCODE_DELETE:
                    CharSequence selectedText = ic.getSelectedText(0);
                    if (TextUtils.isEmpty(selectedText)) {
                        ic.deleteSurroundingText(1, 0);
                    } else {
                        ic.commitText("", 1);
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT:
                    isCaps = !isCaps;
                    keyboard.setShifted(isCaps);
                    kv.invalidateAllKeys();
                    break;
                case Keyboard.KEYCODE_DONE:
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
                    break;
                default:
                    char code = (char) primaryCode;
                    if (Character.isLetter(code)&&isCaps){
                        code = Character.toUpperCase(code);
                    }
                    ic.commitText(String.valueOf(code),1);
            }
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
}
