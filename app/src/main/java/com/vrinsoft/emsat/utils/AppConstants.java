package com.vrinsoft.emsat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;


import com.vrinsoft.emsat.BuildConfig;
import com.vrinsoft.emsat.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppConstants {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    public static final String PREF_FILE = BuildConfig.APPLICATION_ID+"_PREF";
    public static final int MAPS_REQUEST_CODE_AUTOCOMPLETE = 1123;
    public static final int VALIDATE_DOB_YEAR = -18;

    public static final double PER_METRES_TO_MULTIPLY = 0.00062137119;  // per metre to apply to convert in mile

    public static final int API_CODE_RESPONSE_SUCCESS = 1;
    public static final int API_CODE_RESPONSE_FAIL = 0;
    public static final int API_CODE_RESPONSE_INVALID_TOKEN = -3;
    public static final int API_CODE_RESPONSE_INVALID_ACCOUNT = -2;
    protected static final int PLAY_SERVICES_RESOLUTION_REQUEST = 0x2;
    public static final int ETA_INCOMING_RIDE = 0x234;
    public static final int ETA_HOME_SCREEN = 0x290;
    public static final int ETA_RIDE_START_ONGOING = 0x267;
    public static final int ETA_RIDE_ACCEPT = 0x248;
    public static String OTP_CODE = "";
    public static int preDefaultListPosition = -1;
    public static int cur_sel_pos = MENU_ITEM.MENU_ITEM_HOME;
    public static boolean isUserPreferedLogOut = false;
    public static int CANCEL_REASON_DEFAULT = -1;
    public static String PUSH_NOTIFICATION_INTENT_KEY = "notification_key";
    public static String PUSH_NOTIFICATION_INTENT_VALUE = "notification_value";
    public static String ETA_SHARE_MY_RIDE_INFO = "";
    public static boolean isFromPickUp;
    public static boolean isNearByUsersAvailFirstTime = false;
    public static final String ignore_declined = "declined";
    public static boolean isTestModeOn = true;

    public static String currencySymbol = "$";


    public static void rateUsOnPlayStore(Context context) {
        try {
            //       context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getResources().getString(R.string.playstore_pkgid))));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
    }

    public static void composeEmail(Context context) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"test@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "test");
        intent.putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from my demo app :-)");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else
            ViewUtils.showToast(context, "Gmail App is not installed", null);
    }

    /*public static void shareDefault(Context context, String contentToShare, String defaultMsg) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            Log.e(context.getClass().getSimpleName(), "" + contentToShare);
            sendIntent.putExtra(Intent.EXTRA_TEXT, contentToShare);
            sendIntent.setType("text/plain");
            context.startActivity(Intent.createChooser(
                    sendIntent, Validator.isNullEmpty(defaultMsg) ? context.getResources().getText(R.string.share) : defaultMsg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
    }

    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

//    latlng="+ lat+","+lng +"

//    public static String GetCountryZipCode(Activity context) {
////        https://stackoverflow.com/a/17266260/2054348
//        String CountryID = "";
//        String CountryZipCode = "";
//
//        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        //getNetworkCountryIso
//        CountryID = manager.getSimCountryIso().toUpperCase();
//
//        String[] rl = context.getResources().getStringArray(R.array.CountryCodes);
//
//        if (!TextUtils.isEmpty(CountryID)) {
//            for (int i = 0; i < rl.length; i++) {
//                String[] g = rl[i].split(",");
//                if (g[1].trim().equals(CountryID.trim())) {
//                    CountryZipCode = g[0];
//                    break;
//                }
//            }
//        } else {
//            String IN = "+91,IN";
//            String[] g = IN.split(",");
//            CountryZipCode = g[0];
//        }
//        return CountryZipCode;
//    }

    public static String getEncodedString(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get_KM_from_Meters(float meter) {
        return new DecimalFormat("#.##").format(meter / 1000f);
    }

//    public static Country getUserCountryInfo(Activity activity) {
////        https://github.com/mukeshsolanki/country-picker-android
//        Country country = Country.getCountryFromSIM(activity);
//        if (country != null) {
//            return country;
//        }
//        return null;
//    }

    public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        } else {
            return false;
        }
    }

    /*public static void setLogoutFromApp(Activity mActivity) {
        if (!AppConstants.isUserPreferedLogOut)
            ViewUtils.showToast(mActivity, mActivity.getString(R.string.invalid_token_msg), null);
        AppConstants.isUserPreferedLogOut = false;
        Pref.clearAllData(mActivity);
        NavigationUtils.logoutFromActivity(mActivity);
    }*/

    public interface DATE_FORMAT {
        public static String DD_MM_YYYY = "dd MMM yyy";

        public static String API_DD_MM_YYYY = "yyyy-MM-dd";
    }

    public interface SingleChoiceDialogMode {
        public static final int FAV_SPOTS = 1;
        public static final int CANCEL_REASONS_DIALOG = 2;
    }

    public interface GOOGLE_API {
        public static String MODE_TRAVEL = "driving";
        public static String STATUS_OK = "OK";

        public interface STATIC_MAP {
            //            public static String markerSize = "tiny";
            public static String markerColor = "black";
            public static String pathColor = "0x000000ff";
            public static String path_thickness = "1";
            public static String scale = "2";// resolution of Image
            public static String map_width = "512";
            public static String map_height = "256";
            public interface MARKER {
                public static String SIZE_TINY = "tiny";
                public static String SIZE_MID = "mid";
                public static String SIZE_SMALL = "small";
            }
        }
    }

    public interface DATE_MODE {
        public static int PLAN_RIDE = 1;
        public static int FILTER = 2;
    }

    public interface GPS_CONSTANTS {
        public static String KEY_LOCATION_CHANGED = "LOCATION_CHANGED";
    }

    public interface MENU_ITEM {
        public static int MENU_ITEM_HOME = 0;
        public static int MENU_ITEM_PROFILE = 1;
        public static int MENU_ITEM_MY_TEST = 2;
        public static int MENU_ITEM_HELP = 3;
        public static int MENU_ITEM_SIGN_OUT = 4;
    }

    public interface GENDER {
        public static final int MALE = 1;
        public static final int FEMALE = 2;
    }

    public interface USER_TYPE {
        public static int TYPE_BOTH = 0;
        public static int TYPE_RIDER = 1;
        public static int TYPE_DRIVER = 2;
    }

    public interface RIDE_STATUS_ACTION {
        public static String CURRENT_RIDE_STATUS = "ride_status_current";
//        public static String EXPIRED_INCOMING_RIDE = "expired_incoming_ride";
    }

    public interface PUSH_NOTIFICATION_ACTION {
        public static String PUSH = "PUSH_NOTIFICATION";
    }


    public interface NOTIFICATION_BROADCAST {
        public static String isBroadcast = "true";
        public static String isBroadcastNot = "false";
    }

    public interface CMS {
        public static String RATE_CARD_MANAGEMENT = "1";
        public static String TERMS_CONDITIONS = "2";
        public static String PRIVACY_POLICY = "4";
        public static String DISCLOSURE = "5";
    }

    public interface RIDE_STATUS {
        //        public static String KEY_CURRENT_RIDE_STATUS = "ride_status_current_key";
        public static String PUSH_RESPONSE_INCOMING_RIDE = "ride_request";
        public static int PENDING = 0;
        public static int ACCEPT = 1;
        public static int ACCEPT_CONFIRM = 11;
        //        public static int ARRIVE = 2;
        public static int START_ONGOING = 3;
        public static int CANCEL = 4;
        public static int CANCEL_CONFIRM = 44;
        public static int END = 5;
        //        public static int END_CONFIRM = 55;//Rider Payment Screen
        public static int DECLINE = 6;
        public static int DECLINE_CONFIRM = 66;
        public static int REQUEST = 100;
        public static int TEMP_NONE = -1;
    }

    public interface APPCOLOR {
        public static final String PRIMARY = "#006AA8";
        public static final String HINT_TEXT_COLOR = "#737373";
    }

    public interface FAV_SPOT_REQUEST_CODE {

        public static int RIDE_NOW_PICK_UP_REQUEST_CODE = 104;
        public static int RIDE_NOW_DROP_OFF_REQUEST_CODE = 105;

        public static int PLAN_RIDE_PICK_UP_REQUEST_CODE = 106;
        public static int PLAN_RIDE_DROP_OFF_REQUEST_CODE = 107;
    }

    public interface LOCATION_PICKER_ADDRESS_REQUEST_CODE {

        public static final int FAVOURITE_SPOT = 101;
        public static final int RIDE_NOW_PICK_UP = 102;
        public static final int RIDE_NOW_DROP_OFF = 103;

        public static final int PLAN_RIDE_PICK_UP = 104;
        public static final int PLAN_RIDE_DROP_OFF = 105;
    }

    public interface FILTER_REQUEST_CODE {
        public static final int DISCUSSION_BOARD_REQUEST_CODE = 101;
    }

    public interface PAYMENT_REQUEST_CODE {
        public static final int PAYMENT = 1001;
    }

    public interface FEEDBACK_REQUEST_CODE {
        public static final int FEEDBACK = 1002;
    }

    /*NUM_OF_CHECKED_VIEWS indicates no. of views to be checked/unchecked*/
    public interface NUM_OF_CHECKED_VIEWS {
        public static final int TWO = 2;
        public static final int THREE = 3;
        public static final int FOUR = 4;
    }

    public interface START_RIDE {
        public static final String CONFIRMATION_CODE = "123456";
    }

    public interface RADAR_ANIMATION {
        public static final int SHOW_RADAR = 1;
        public static final int DISMISS_RADAR = 2;
    }

    public interface FAV_SPOT {
        public static final String FAV_SPOT_DRAWER = "1";
        public static final String FAV_SPOT_RIDE_NOW = "2";
    }

    public interface REQUEST_TYPE {
        public static int TYPE_BOTH = 0;
        public static int OFFERING_RIDE = 1;
        public static int REQUESTING_RIDE = 2;
    }

    public interface RIDING_COMPANION_PREF {
        public static final int MALE = 1;
        public static final int FEMALE = 2;
        public static final int ANY = 0;
    }

    public interface ALLOWED_RIDER {
        public static final int ONE = 1;
        public static final int ONE_PLUS = 2;
    }

    public interface FEEDBACK_SYMBOL_TYPE {
        public static final int LOVE = 1;
        public static final int HAPPY = 1;
        public static final int AVERAGE = 2;
        public static final int SAD = 3;
    }

    public interface SETTINGS_NOTIFICATION_TYPE {
        public static final String WHEN_APP_HAS_NEW_FEATURES = "1";
        public static final String WHEN_NEW_DRIVER_JOIN = "2";
        public static final String WHEN_FAV_DRIVER_RIDING = "3";
    }

    public interface FEEDBACK_WHATS_WRONG {
        public static final int RUDE = 1;
        public static final int INDECENT = 2;
        public static final int ANNOYING = 3;
    }

    public interface FEEDBACK_BLOCK_PERSON {
        public static final int YES = 1;
        public static final int NO = 2;
    }

    public interface PAYMENT_TYPE {
        public static final int WALLET = 1;
        public static final int PAYPAL = 2;
        public static final int CASH = 3;
        public static final int CARD = 4;
    }

    public interface WALLET_FROM {
        public static final int AHOY_WALLET = 1;
        public static final int PAYMENT = 2;
    }

    public interface DEEPLINK {
        public static final String LOGIN = "LOGIN";
        public static final String HOME = "HOME";
    }
}
