package com.vrinsoft.emsat.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.signin.SignIn;


public class NavigationUtils {

    private static final NavigationUtils ourInstance = new NavigationUtils();

    private NavigationUtils() {
    }

    public static NavigationUtils getInstance() {
        return ourInstance;
    }

    public static void startActivity(Activity context, Class dest, Bundle bundle) {
        Intent intent = new Intent(context, dest);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startActivityWithClearStack(Activity context, Class dest, Bundle bundle) {
        Intent intent = new Intent(context, dest);
        if (bundle != null)
            intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
//        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void finishCurrentActivity(Activity context) {
        context.finish();
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void logoutFromActivity(Activity context)
    {
        Intent intent = new Intent(context, SignIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
//
//    public static void startHomeScreen(Activity context, Bundle bundle)
//    {
//        Intent intent = new Intent(context, HomeDriver.class);
//        if(bundle!=null)
//            intent.putExtras(bundle);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//    }

    public static void startNotificationService(Activity context) {
        /*Intent startIntent = new Intent(context, NotificationService.class);
        startIntent.setAction(AppConstants.NOTIFICATION_ACTION.ACTION_INCOMING_RIDE_MAIN);
        context.startService(startIntent);*/
    }

    public static void stopNotificationService(Activity context) {
        /*Intent startIntent = new Intent(context, NotificationService.class);
        startIntent.setAction(AppConstants.NOTIFICATION_ACTION.STOP_NOTIFY_ACTION);
        context.startService(startIntent);*/
    }
}
