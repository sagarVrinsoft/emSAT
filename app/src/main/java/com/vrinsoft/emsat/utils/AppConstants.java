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
import com.vrinsoft.emsat.apis.model.exam_question.QuestionBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AppConstants {
    public static final String INTENT_CAT_NAME = "cat_name";
    public static final String INTENT_CAT_ID = "cat_id";
    public static final String INTENT_SUBCAT_NAME = "subcat_name";
    public static final String INTENT_SUBCAT_ID = "subcat_id";
    public static final String INTENT_TEST_ID = "test_id";
    public static final String INTENT_TEST_NAME = "test_name";
    public static final int REQUEST_CODE_CHECK_ANS = 1222;
    public static ArrayList<QuestionBean.Result> mQuestionList = new ArrayList<>();

    public interface QUESTION_TYPE {
        public static String MCQ = "1";
        public static String TRUE_FALSE = "2";
        public static String FILL_BLANK = "3";
    }

    public interface ANS_TYPE {
        public static int SKIP = 0;
        public static int CORRECT = 1;
        public static int WRONG = 2;
    }

    public interface BUNDLE_KEY {
        public static String TOTAL_SCORE = "total_score";
        public static String OBTAINED_SCORE = "obtained_score";
        public static String SKIPPED_ANS = "skipped_ans";
        public static String CORRECT_ANS = "correct_ans";
        public static String WRONG_ANS = "wrong_ans";
        public static String TOTAL_ANS = "total_ans";
        public static String TOTAL_TIME_SEC = "total_time_sec";
    }

    public interface DEVICE_TYPE {
        public static String ANDROID = "2";
    }


//    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    public static final int VALIDATE_DOB_YEAR = -18;
    public static int cur_sel_pos = MENU_ITEM.MENU_ITEM_HOME;
    public static boolean isUserPreferedLogOut = false;
    public static String PUSH_NOTIFICATION_INTENT_KEY = "notification_key";
    public static String PUSH_NOTIFICATION_INTENT_VALUE = "notification_value";
    public static boolean isTestModeOn = false;
    public static String currencySymbol = "$";
    public static void rateUsOnPlayStore(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /*public static String getCurrentTime() {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
    }*/

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

    public static void setLogoutFromApp(Activity mActivity) {
        if (!AppConstants.isUserPreferedLogOut)
            ViewUtils.showToast(mActivity, mActivity.getString(R.string.invalid_token_msg), null);
        AppConstants.isUserPreferedLogOut = false;
        Pref.clearAllData(mActivity);
        NavigationUtils.logoutFromActivity(mActivity);
    }

    public interface DATE_FORMAT {
        public static String API_DD_MM_YYYY = "yyyy-MM-dd";
    }

    public interface MENU_ITEM {
        public static int MENU_ITEM_HOME = 0;
        public static int MENU_ITEM_PROFILE = 1;
//        public static int MENU_ITEM_MY_TEST = 2;
        public static int MENU_ITEM_HELP = 2;
        public static int MENU_ITEM_SIGN_OUT = 3;
    }

    public interface GENDER {
        public static final int MALE = 1;
        public static final String MALE_str = "Male";
        public static final int FEMALE = 2;
    }

    public interface PUSH_NOTIFICATION_ACTION {
        public static String PUSH = "PUSH_NOTIFICATION";
    }

    public interface CMS {
        public static String ABOUT_US = "1";
        public static String TERMS_CONDITIONS = "2";
        public static String PRIVACY_POLICY = "3";
    }

    /*NUM_OF_CHECKED_VIEWS indicates no. of views to be checked/unchecked*/
    public interface NUM_OF_CHECKED_VIEWS {
        public static final int TWO = 2;
//        public static final int THREE = 3;
//        public static final int FOUR = 4;
    }

}
