package com.vrinsoft.emsat.utils;

public class AppPreference {

    public static String PREF_DEMO = "demo_screens_flexipool";
    public static String PREF_LOGIN = "login";
    public static String FCM_PREF_REG_ID = "regId";
    public static String IS_DEVICE_REGISTER_FCM = "IS_DEVICE_REGISTER_FCM";
    public static String PREF_ACCURACY_LOCATION_LAST_KNOWN = "AccuracyLastKnownLocation";
    public static String PREF_LOCATION_LAST_KNOWN = "LastKnownLocation";
    public static String PREF_LOCATION_DRIVER_ON_ACCEPT = "LOCATION_DRIVER_ON_ACCEPT";// Store location of driver at the time of accepting ride to draw path as source location.
    public static String PREF_LOCATION_DRIVER_TO_DROP = "LOCATION_DRIVER_TO_DROP";// use for navigation in Google Maps
    public static String PREF_NOTIFICATION_COUNT = "notification_count";
    //    ===========================================
    public static String PREF_INCOMING_RIDE_EXPIRY_TIME = "INCOMING_RIDE_EXPIRY_TIME";//Time in Seconds
    public static String PREF_INCOMING_RIDE_EXPIRY_TIME_DRIVER = "INCOMING_RIDE_EXPIRY_TIME_DRIVER";//Time in Seconds
    public static int PREF_INCOMING_RIDE_EXPIRY_TIME_DEFAULT = 60;// seconds

    public static String PREF_RIDE_STATUS = "RIDE_STATUS";

    public static String PREF_IS_FEEDBACK_SHOWN_DRIVER = "FEEDBACK_SHOWN_DRIVER";
    public static String PREF_IS_FEEDBACK_SHOWN_RIDER = "FEEDBACK_SHOWN_RIDER";
    //    ======= DRIVER/RIDER FLOW MGMT =========

    public static String PREF_PICKUP_LOC_TITLE = "PICKUP_LOC_TITLE";
    public static String PREF_DESTINATION_LOC_TITLE = "DESTINATION_LOC_TITLE";

    // PREF_IS_CONFIRMATION_RIDE_STARTED >> this indicates Confimration Number & Cancel button
    // should not be enable once ride start event is invoked.
    // And will release(false) this bool when ride status change to start.
    public static String PREF_IS_CONFIRMATION_RIDE_STARTED = "IS_CONFIRMATION_RIDE_STARTED";
    public static String PREF_IS_RIDE_ONGOING_STATUS = "IS_RIDE_ONGOING_STATUS";

    public static String PREF_CURRENT_TRIP_COST = "CURRENT_TRIP_COST";//stores value to show it in Payment screen
//    ======= DRIVER/RIDER FLOW MGMT =========
    public static String PREF_CURRENCY_SYMBOL = "currency_symbol";
    public static String PREF_CURRENCY_DECIMAL = "currency_decimal";
    public static String PREF_FP_RIDE_RATE = "fp_ride_rate";
//    ====================================
    public static String PREF_OTP = "OTP";
    public static String PREF_TRIP_ID = "TRIP_ID";//Purpose to store actual distance in KM for this TripId
    public static String PREF_VOICE_MUTE = "VOICE_MUTE";

    public static String PREF_PICKUP_LAT_LNG = "PICKUP_LAT_LNG";
    public static String IS_INCOMING_RIDE = "IS_INCOMING_RIDE";

    /*------ Requester will be block when PREF_TRIP_REQUEST_SENT is true -----*/
//    public static String PREF_TRIP_REQUEST_SENT = "TRIP_REQUEST_SENT";

    public interface LANGUAGE {
        public static String PREF_LANGUAGE_NAME = "LANGUAGE_NAME";//string value
        public static String PREF_LANGUAGE_ID = "LANGUAGE_ID";//string value
        public static String DEFAULT_LANGUAGE_ID = "1";//string value
        public static String DEFAULT_LANGUAGE_LABELS = "DEFAULT_LANGUAGE_LABELS";//ENGLISH
    }

    public interface USER_INFO {
        public static String PREF_USER_ID = "USERID";
        public static String PREF_FIRST_NAME = "FNAME";
        public static String PREF_LAST_NAME = "LNAME";
        public static String PREF_EMAIL_ID = "EMAIL";
        public static String PREF_MOBILE_NO = "MOBILENO";
        public static String PREF_PASSWORD = "PASSWORD";
        public static String PREF_EMERGENCY_NO = "EMERGENCY_NO";
        public static String PREF_AGE = "AGE";
        public static String PREF_USER_TYPE = "USER_TYPE";// current role of user
        public static String PREF_USER_TYPE_ACTUAL = "PREF_USER_TYPE_ACTUAL";// status of user while register as both/rider/driver
        public static String PREF_METRO_AREA = "METRO_AREA";
        public static String PREF_DOB = "DOB";
        public static String PREF_GENDER = "GENDER";
        public static String PREF_PROFESSION = "PROFESSION";
        public static String PREF_TOKEN = "TOKEN";
        public static String PREF_COUNTRY_CODE = "COUNTRY_CODE";
        public static String PREF_USER_PROFILE = "USER_PROFILE";
        public static String PREF_SSN_NUMBER = "SSN_NUMBER";
        public static String PREF_RIDING_COMPANION = "RIDING_COMPANION";
        public static String PREF_ALLOWED_RIDE = "ALLOWED_RIDE";


        public static String PREF_FEEDBACK_USER_ID = "FEEDBACK_USER_ID";
        public static String PREF_FEEDBACK_USER_IS_FAV = "FEEDBACK_USER_IS_FAV";

        public static String PREF_FEEDBACK_USER_NAME = "FEEDBACK_USER_NAME";
        public static String PREF_FEEDBACK_USER_IMG = "FEEDBACK_USER_IMG";
        public static String PREF_FEEDBACK_USER_RIDE_ID = "FEEDBACK_USER_RIDE_ID";
    }

    public interface VEHICLE_INFO {
        public static String PREF_VEHICLE_MODEL = "MODEL";
        public static String PREF_VEHICLE_YEAR = "YEAR";
        public static String PREF_VEHICLE_STATE = "STATE";
        public static String PREF_VEHICLE_PLATE_NO = "PLATE_NO";
    }

    public interface USER_SETTINGS {
        public static String PREF_IS_NOTIFICATION_ON = "IS_NOTIFICATION_ON";
        public static String PREF_APP_NEW_FEATURES = "APP_NEW_FEATURES";
        public static String PREF_NEW_DRIVER_JOIN = "NEW_DRIVER_JOIN";
        public static String PREF_FAV_DRIVER_RIDING = "FAV_DRIVER_RIDING";
    }

    public interface PAYMENT{
        public static String PREF_WALLET_AMOUNT = "WALLET_AMOUNT";
        public static String PREF_IS_PAYMENT_DONE = "IS_PAYMENT_DONE";//To Ensure Payment is done
    }

    public interface BOOK_RIDE{
        public static String PREF_BOOK_NEXT_RIDE_IS_ASKED = "BOOK_NEXT_RIDE";
    }

    public interface NAVIGATION{
        public static String AVOID_VALUE_API_KEY = "AVOID_VALUE_API_KEY";
        public static String AVOID_HIGHWAYS_KEY = "AVOID_HIGHWAYS_KEY";
        public static String AVOID_TOLLS_KEY = "AVOID_TOLLS_KEY";
        public static String AVOID_HIGHWAYS = "highways";
        public static String AVOID_TOLLS = "tolls";
    }

}
