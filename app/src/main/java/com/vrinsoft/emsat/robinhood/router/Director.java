package com.vrinsoft.emsat.robinhood.router;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.activity.Home;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.Validator;
import com.vrinsoft.emsat.utils.ViewUtils;


/**
 * Created by SagarShah on 20/7/17.
 */

public class Director {
    public static final java.lang.String CMS_TITLE = "title";
    public static String DL_KEY = "Director";
    AppCompatActivity context;

    public Director(AppCompatActivity context) {
        this.context = context;
    }

    public void directTo(int position, Bundle bundle) {

        if (position == AppConstants.MENU_ITEM.MENU_ITEM_SIGN_OUT) {
            ((MasterActivity) context).signOutCofirmation();
        } else if (position != AppConstants.cur_sel_pos) {
            AppConstants.cur_sel_pos = position;
            switch (position) {

                case AppConstants.MENU_ITEM.MENU_ITEM_PROFILE:
//                    NavigationUtils.startActivity(context, AhoyWallet.class, bundle);
                    ViewUtils.showToast(context, "Profile", null);
                    break;
                case AppConstants.MENU_ITEM.MENU_ITEM_MY_TEST:
//                    NavigationUtils.startActivity(context, AhoyWallet.class, bundle);
                    ViewUtils.showToast(context, "My Test", null);
                    break;
                case AppConstants.MENU_ITEM.MENU_ITEM_HELP:
//                    NavigationUtils.startActivity(context, AhoyWallet.class, bundle);
                    ViewUtils.showToast(context, "Help", null);
                    break;
            }

            if (!(context instanceof Home)) {
                NavigationUtils.finishCurrentActivity(context);
            }
        }
    }
}