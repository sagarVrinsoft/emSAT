package com.vrinsoft.emsat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.vrinsoft.emsat.activity.Home;
import com.vrinsoft.emsat.databinding.ActivityMasterBinding;
import com.vrinsoft.emsat.navigation_drawer.FragmentDrawer;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.ConnectivityReceiver;
import com.vrinsoft.emsat.widget.CustomTextView;

import java.util.ArrayList;
// Master br
public abstract class MasterActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener,
        FragmentDrawer.FragmentDrawerListener, SharedPreferences.OnSharedPreferenceChangeListener, DrawerLayout.DrawerListener {

    public final String TAG = MasterActivity.this.getClass().getSimpleName();
    public Toolbar mToolbar;
    public ImageView mmToolbarimgMenu, mmToolbarimgBack, mmToolbarimgDone;
    public CustomTextView mmToolbartxtTitle, mmtxtRider, mmtxtDriver, mmtxtNotificationCount;
    //    public SlidingRootNav slideMenuUtils;
    public boolean isConnectedToInternet = false;
    public Gson gson = new Gson();
    public Director director;
    public ActivityMasterBinding masterBinding;
    SharedPreferences pref;
    private FrameLayout mContainer;
    Bundle b = null;
    private FragmentDrawer drawerFragment;
    private Handler mHandler = new Handler();
    Activity mActivity;
    ConnectivityReceiver connectivityReceiver;

    public abstract Activity getActivity();

    public abstract View getContentLayout();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        masterBinding = DataBindingUtil.setContentView(this, R.layout.activity_master);

        mActivity = this;
        connectivityReceiver = new ConnectivityReceiver(this, this);
        director = new Director(this);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
//        pref = getSharedPreferences(AppConstants.PREF_FILE, Context.MODE_PRIVATE);
        pref.registerOnSharedPreferenceChangeListener(this);
        masterBinding.drawerLayout.addDrawerListener(this);
        masterBinding.container.addView(getContentLayout());
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setDrawerListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectivityReceiver,
                ConnectivityReceiver.getIntentFilterInternetConnectivity());
//        setLockMenu();

        if (getActivity() instanceof Home) {
            // To make work while going back to home
            // As nothing is triggered from SlideMenuUtils when back pressed
            // and activity changes.
            AppConstants.cur_sel_pos = AppConstants.MENU_ITEM.MENU_ITEM_RIDE_NOW;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pref.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    @Override
    public void onDrawerItemSelected(final int position) {
        closeDrawer();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                director.directTo(position, b);
            }
        },300);
    }

    public void setDrawerVisible(boolean isDrawer) {
        if (isDrawer) {
            mmToolbarimgMenu.setVisibility(View.VISIBLE);
            mmToolbarimgBack.setVisibility(View.GONE);
            masterBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            mmToolbarimgMenu.setVisibility(View.GONE);
            mmToolbarimgBack.setVisibility(View.VISIBLE);
            masterBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void toggleDrawer() {
        if (masterBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            masterBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            masterBinding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void closeDrawer() {
        masterBinding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    public boolean isOpen() {
        if (masterBinding.drawerLayout.isDrawerOpen(GravityCompat.START))
            return true;
        else
            return false;
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(AppPreference.USER_INFO.PREF_USER_PROFILE)) {
            drawerFragment.setProfileImg();
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        drawerFragment.setRefreshMenu();
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    public void signOutCofirmation() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mActivity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mActivity);
        }
        builder.setTitle(getString(R.string.menu_sign_out));
        builder.setMessage(getString(R.string.are_you_sure_you_want_to_sign_out))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        signOut();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /*public void signOut() {
        ViewUtils.showDialog(mActivity, false);
        logOutApiHandler.logOut(Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_USER_ID, ""), Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_TOKEN, "")
                , new OnLogOut() {
                    @Override
                    public void getResponse(boolean isSuccess, ArrayList<BeanLogOut> beanLogOut, String errorMsgSystem) {
                        if (isSuccess) {
                            if (beanLogOut.get(0).getCode() == AppConstants.API_CODE_RESPONSE_SUCCESS) {
                                ViewUtils.showDialog(mActivity, true);
                                stopGpsService();
                                AppConstants.isUserPreferedLogOut = true;
                                AppConstants.setLogoutFromApp(mActivity);
                            }
                            else if (beanLogOut.get(0).getCode() != null &&
                                    beanLogOut.get(0).getCode() ==
                                            API_Constants.API_CODE_TOKEN_INVALID) {
                                AppConstants.isUserPreferedLogOut = false;
                                AppConstants.setLogoutFromApp(mActivity);
                            } else {
                                ViewUtils.showDialog(mActivity, true);
                                ViewUtils.showToast(mActivity, beanLogOut.get(0).getMessage(), null);
                            }
                        } else {
                            ViewUtils.showDialog(mActivity, true);
                            ViewUtils.showToast(mActivity, errorMsgSystem, null);
                        }
                    }
                });
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
