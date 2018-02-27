package com.vrinsoft.emsat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

/**
 * Created by pratik on 2/5/17.
 */

public class Pref {
    private static final String Pref_Key_NeverAskAgainForContact = "NeverAskAgainForContact";
    private static final String Pref_Key_NeverAskAgainForFineLocation = "NeverAskAgainForFineLocation";
    private static final String Pref_Key_NeverAskAgainForReadContacts = "NeverAskAgainForReadContacts";
    private static final String Pref_Key_NeverAskAgainForSendSms = "NeverAskAgainForSendSms";
    private static final String Pref_Key_NeverAskAgainForCamera = "NeverAskAgainForCamera";
    private static final String Pref_Key_NeverAskAgainForStorage = "NeverAskAgainForStorage";
    @Nullable
    private static SharedPreferences sharedPreferences = null;

    public static void openPref(@NonNull Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void clearAllData(Context context)
    {
//        ==== Get Values which are still require after clearing data ========
//        String mFCMtoken = Pref.getValue(context, AppPreference.FCM_PREF_REG_ID, "");
//        String defaultLanguageLabels = Pref.getValue(context, AppPreference.LANGUAGE.DEFAULT_LANGUAGE_LABELS, "");
//        String defaultLangId = Pref.getValue(context, AppPreference.LANGUAGE.PREF_LANGUAGE_ID, "");
//      ======== Clearing Data ===========================================
        Pref.openPref(context);
        Pref.sharedPreferences.edit().clear().commit();
//      ==== Set Values which are still require after clearing data ========
//        Pref.setValue(context, AppPreference.FCM_PREF_REG_ID, mFCMtoken);
//        Pref.setValue(context, AppPreference.LANGUAGE.DEFAULT_LANGUAGE_LABELS, defaultLanguageLabels);
//        Pref.setValue(context, AppPreference.LANGUAGE.PREF_LANGUAGE_ID, defaultLangId);

    }

    @Nullable
    public static String getValue(@NonNull Context context, String key, String defaultValue) {
        Pref.openPref(context);
        String result = Pref.sharedPreferences.getString(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }

    @Nullable
    public static int getValue(@NonNull Context context, String key, int defaultValue) {
        Pref.openPref(context);
        int result = Pref.sharedPreferences.getInt(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }

    @Nullable
    public static boolean getValue(@NonNull Context context, String key, boolean defaultValue) {
        Pref.openPref(context);
        boolean result = Pref.sharedPreferences.getBoolean(key, defaultValue);
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setValue(@NonNull Context context, String key, String value) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putString(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static void setValue(@NonNull Context context, String key, int value) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putInt(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static void setValue(@NonNull Context context, String key, boolean value) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putBoolean(key, value);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    //  ======================= RUNTIME PERMISSIONS =====================================
    public static void setNeverAskAgainForContact(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putBoolean(Pref_Key_NeverAskAgainForContact, isNeverAskAgain);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static boolean isNeverAskAgainForContact(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        boolean result = Pref.sharedPreferences.getBoolean(Pref_Key_NeverAskAgainForContact, false);
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setNeverAskAgainForReadContacts(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putBoolean(Pref_Key_NeverAskAgainForReadContacts, isNeverAskAgain);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static boolean isNeverAskAgainForReadContacts(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        boolean result = Pref.sharedPreferences.getBoolean(Pref_Key_NeverAskAgainForReadContacts, false);
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setNeverAskAgainForSendSms(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putBoolean(Pref_Key_NeverAskAgainForReadContacts, isNeverAskAgain);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static boolean isNeverAskAgainForSendSms(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        boolean result = Pref.sharedPreferences.getBoolean(Pref_Key_NeverAskAgainForReadContacts, false);
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setNeverAskAgainForFineLocation(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putBoolean(Pref_Key_NeverAskAgainForFineLocation, isNeverAskAgain);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static boolean isNeverAskAgainForFineLocation(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        boolean result = Pref.sharedPreferences.getBoolean(Pref_Key_NeverAskAgainForFineLocation, false);
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setNeverAskAgainForCamera(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.commit();
        prefsPrivateEditor.putBoolean(Pref_Key_NeverAskAgainForCamera, isNeverAskAgain);
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static boolean isNeverAskAgainForCamera(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        boolean result = Pref.sharedPreferences.getBoolean(Pref_Key_NeverAskAgainForCamera, false);
        Pref.sharedPreferences = null;
        return result;
    }

    public static void setNeverAskAgainForStorage(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        SharedPreferences.Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
        prefsPrivateEditor.putBoolean(Pref_Key_NeverAskAgainForStorage, isNeverAskAgain);
        prefsPrivateEditor.commit();
        prefsPrivateEditor = null;
        Pref.sharedPreferences = null;
    }

    public static boolean isNeverAskAgainForStorage(Context context, boolean isNeverAskAgain) {
        Pref.openPref(context);
        boolean result = Pref.sharedPreferences.getBoolean(Pref_Key_NeverAskAgainForStorage, false);
        Pref.sharedPreferences = null;
        return result;
    }
    //  ======================= RUNTIME PERMISSIONS =====================================
}
