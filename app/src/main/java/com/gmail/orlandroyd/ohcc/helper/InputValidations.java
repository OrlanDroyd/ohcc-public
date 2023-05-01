package com.gmail.orlandroyd.ohcc.helper;

import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Pattern;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by OrlanDroyd on 30/04/19
 */
public class InputValidations {
    private Context context;
    public static final Pattern NAME_PATTERRN =
            Pattern.compile("^[a-zA-ZÁÉÍÓÚñáéíóúÑ]+(([',. -][a-zA-Z ÁÉÍÓÚñáéíóúÑ])?[a-zA-ZÁÉÍÓÚñáéíóúÑ]*)*$");

    /**
     * constructor
     *
     * @param context
     */
    public InputValidations(Context context) {
        this.context = context;
    }

    /**
     * method to check EditText filled.
     *
     * @param compatEditText
     * @param message
     * @return
     */
    public boolean isEditTextFilled(AppCompatEditText compatEditText, String message) {
        String value = compatEditText.getText().toString().trim();
        if (value.isEmpty()) {
            FancyToast.makeText(context, message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            hideKeyboardFrom(compatEditText);
            return false;
        }
        return true;
    }


    /**
     * method to check InputEditText filled.
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to check InputEditText length
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @param length
     * @return
     */
    public boolean isInputEditTextLength(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message, int length) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || value.length() < length) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to check InputEditText has valid email.
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextName(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !NAME_PATTERRN.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextPhone(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !Patterns.PHONE.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to check InputEditText matches
     *
     * @param textInputEditText1
     * @param textInputEditText2
     * @param textInputLayout2
     * @param message
     * @return
     */
    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout2, String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            textInputLayout2.setError(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        } else {
            textInputLayout2.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to hide keyboard
     *
     * @param view
     */
    public void hideKeyboardFrom(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void emptyInputEditTexts(TextInputEditText... textInputEditTexts) {
        for (TextInputEditText textInputEditText : textInputEditTexts) {
            textInputEditText.setText(null);
        }
    }

}
