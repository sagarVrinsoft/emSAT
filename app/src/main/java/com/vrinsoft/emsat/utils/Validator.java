package com.vrinsoft.emsat.utils;

import android.app.Activity;
import android.widget.TextView;

import com.vrinsoft.emsat.widget.CustomEditTextView;

/**
 * Created by komal on 1/6/17.
 */

public class Validator {

    public static boolean isNullEmpty(String string) {
        if (string!=null && string.length()>0)
            return false;
        else
            return true;
    }

    public static boolean isEmpty(TextView textView) {
        if (textView.getText().toString().trim().length() > 0)
            return false;
        else
            return true;
    }

    public static boolean isEmptyStr(String string) {
        if (string.length() > 0)
            return false;
        else
            return true;
    }

    public static String getTextStr(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static boolean isEmptyText(Activity mActivity, TextView textView, String validationMsg) {
        if (textView.getText().toString().equalsIgnoreCase("")) {
            ViewUtils.showToast(mActivity, validationMsg, textView);
            return true;
        }
        return false;
    }

    public static boolean checkValidation(Activity mActivity,
                                          CustomEditTextView editTextView,
                                          String validationMsg) {
        if (Validator.isEmptyStr(editTextView.getText().toString().trim())) {
            ViewUtils.showToast(mActivity, validationMsg, null);
            editTextView.requestFocus();
            return true;
        }
        return false;
    }
}
