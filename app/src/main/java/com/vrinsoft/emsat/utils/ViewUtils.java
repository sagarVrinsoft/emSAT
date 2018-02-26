package com.vrinsoft.emsat.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.model.phonebook.BinPhoneBook;
import com.vrinsoft.emsat.utils.dialog.adapter.MyRecyclerViewAdapter;
import com.vrinsoft.emsat.utils.dialog.model.BinItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import static com.vrinsoft.emsat.utils.LogUtils.LOGD;

public class ViewUtils {
    //temp
    public static boolean isCancelRideDialogOpenByPassenger = false;

    public static String DATE_TIME_FORMAT = "dd MMM yyy HH:mm";
    public static String DATE_FORMAT = "dd MMM yyy";
    public static String DATE_FORMAT_DD_MMM = "dd MMM";
    public static String TIME_FORMAT = "hh:mm a";
    public static String DATE_TIME_SEC_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_TIME_SEC_FORMAT_12_hour = "yyyy-MM-dd hh:mm a";
    public static ArrayList<BinItems> cancelReasonsList = new ArrayList<>();
    static ProgressDialog progress;
    static SimpleDateFormat sdf;
    static DatePickerDialog datePickerDialog;
    static int hour = 0;
    static int minute = 0;
    static int year = 0;
    @NonNull
    private static Toast mToast;
    private static String strPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(strPattern);
    Context context;
    int positionSelectedSingleChoice = -1;
    BinItems binItems = null;
    MyRecyclerViewAdapter mAdapter;
    ArrayList<BinPhoneBook> alContacts = new ArrayList<BinPhoneBook>();

    public ViewUtils(Context context) {
        this.context = context;
    }

    public String getScreenResolution(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width + "-" + height;
    }

    public static void showToast(Context mContext, String message, View view) {

        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        } else {
            if (mToast != null)
                mToast.cancel();
            mToast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    public static void setTextInputLayout(EditText editTextView, String hint, LinearLayout ll, Drawable llBackground, TextView txtView, int txtViewVisibility, View view, int viewVisibility) {
        editTextView.setHint(hint);
        ll.setBackground(llBackground);
        txtView.setVisibility(txtViewVisibility);
        view.setVisibility(viewVisibility);
    }

    public static void hideKeyboard(Context context) {
        // Check if no view has focus:
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getScreenResolutionHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceHeight = displayMetrics.heightPixels;

        return deviceHeight;
    }

    public static int getScreenResolutionWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;

        return deviceWidth;
    }

    public static void showKeyBoard(Activity context) {
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public static boolean checkEmail(@Nullable String strEmail) {
        try {
            if (strEmail != null) {
                LOGD("UTILS :", "checkEmail() ==> EMAIL :" + strEmail);
                return EMAIL_ADDRESS_PATTERN.matcher(strEmail).matches();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//        https://stackoverflow.com/questions/12562151/android-get-all-contacts

    public static void statusBarHide(Activity activity) {
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void setLanguage(@NonNull Context context, @NonNull String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        LOGD(context.getClass().getSimpleName(), "==--------- Language set to " + language);
    }

    public static void showSingleBtnAlert(Context context, String title, String message, String btnText, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        alertDialog.create();

        if (!Validator.isNullEmpty(title))
            alertDialog.setTitle(title);
        if (!Validator.isNullEmpty(message))
            alertDialog.setMessage(message);
        alertDialog.setPositiveButton(btnText, onClickListener);
        alertDialog.show();
    }

    public static void showDoubleBtnAlert(Context context, String title, String message, String btnText1, String btnText2, DialogInterface.OnClickListener onClickListener) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.create();
        if (!Validator.isNullEmpty(title))
            alertDialog.setTitle(title);
        if (!Validator.isNullEmpty(message))
            alertDialog.setMessage(message);
        alertDialog.setPositiveButton(btnText1, onClickListener);
        alertDialog.setNegativeButton(btnText2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    public static String checkNull(String key) {

        String result = "";
        if (key != null) {
            return key.equalsIgnoreCase("null") ? "" : key;
        }
        return result;
    }

    public static void setBackgroundColor(Context context, int mColor, View view) {
        view.setBackgroundColor(ContextCompat.getColor(context, mColor));
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
    }

    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static String getCurrentDate() {
        sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT.API_DD_MM_YYYY, Locale.US);
        return sdf.format(new Date());
    }

    public static String getDateAfterOneWeek() {
        Date oneWeek = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(oneWeek);
        cal.add(Calendar.DATE, 7);
        oneWeek = cal.getTime();
        sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT.DD_MM_YYYY, Locale.US);
        return sdf.format(oneWeek);
    }

    public static String getDateAfterOneMonth() {
        Date oneMonth = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(oneMonth);
        cal.add(Calendar.MONTH, 1);
        oneMonth = cal.getTime();
        sdf = new SimpleDateFormat(AppConstants.DATE_FORMAT.DD_MM_YYYY, Locale.US);
        return sdf.format(oneMonth);
    }

    public static void sendMessage(Activity activity, String cellNo, String msg) {
        if (cellNo != null) {
            Uri uri = Uri.parse("smsto:" + cellNo);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", msg);
            activity.startActivity(intent);
        }
    }

    public static void shareMyRideInfo(Activity activity, String cellNo, String msg) {
        Uri uri = Uri.parse("smsto:" + cellNo);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", msg);
        activity.startActivity(intent);
    }

    public static void showDialog(Activity context, boolean isDismiss) {
        try {
            if (isDismiss) {
                if (progress != null && progress.isShowing()) {
                    progress.dismiss();
                    progress = null;
                }
            } else {
                if (progress != null && progress.isShowing()) {
                    progress.dismiss();
                    progress = null;
                }
                progress = new ProgressDialog(context, R.style.MyAlertDialogStyle);
                progress.setMessage("Progressing...");
                progress.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertDateToString(Date objDate, String parseFormat) {
        try {
            return new SimpleDateFormat(parseFormat).format(objDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date convertStringToDate(String strDate, String parseFormat) {
        try {
            return new SimpleDateFormat(parseFormat).parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String roundUpValue(double value) {
        return String.format("%.2f", value);
    }

    public static String getFormattedDate(String strDate, String outputPattern) {
        //Date Format 2017-07-06 07:16:00
        // 06 Jul 2007 15:03
        if (!strDate.equals("")) {
            String inputPattern = DATE_TIME_SEC_FORMAT;
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.US);
            Date date = null;
            String str = null, final_str = "";
            try {
                date = inputFormat.parse(strDate);
                str = outputFormat.format(date);
                final_str = String.format("%s", str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return final_str;
        }
        return "";
    }

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    public static void SpannableText(Context mContext, String mQuestion, String mCorrectAns, String mWrongAns, TextView mTextView) {
        Spannable text1 = new SpannableString(mQuestion.replaceAll("####", mContext.getString(R.string.blanks)));
        text1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.black)), 0, text1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(text1);
        if (!TextUtils.isEmpty(mWrongAns)) {
            Spannable text2 = new SpannableString("\n\n" + mWrongAns.replaceAll("####", mContext.getString(R.string.blanks)));
            text2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.wrong_color)), 0, text2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTextView.append(text2);
        }
        if (!TextUtils.isEmpty(mCorrectAns)) {
            Spannable text3 = new SpannableString("\n\n" + mCorrectAns);
            text3.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.correct_color)), 0, text3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTextView.append(text3);
        }

    }
}
