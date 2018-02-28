package com.vrinsoft.emsat;

import android.app.Activity;
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
import android.view.View;

import com.google.gson.Gson;
import com.vrinsoft.emsat.activity.home.Home;
import com.vrinsoft.emsat.activity.signin.SignIn;
import com.vrinsoft.emsat.apis.model.sign_out.BeanLogOut;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.ActivityMasterBinding;
import com.vrinsoft.emsat.navigation_drawer.FragmentDrawer;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.ConnectivityReceiver;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MasterActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener,
        FragmentDrawer.FragmentDrawerListener, SharedPreferences.OnSharedPreferenceChangeListener, DrawerLayout.DrawerListener {

    public final String TAG = MasterActivity.this.getClass().getSimpleName();
    public boolean isConnectedToInternet = false;
    public Gson gson = new Gson();
    public Director director;
    public ActivityMasterBinding masterBinding;
    SharedPreferences pref;
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
            AppConstants.cur_sel_pos = AppConstants.MENU_ITEM.MENU_ITEM_HOME;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectivityReceiver);
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
            masterBinding.toolbar.imgHome.setVisibility(View.VISIBLE);
            masterBinding.toolbar.imgBack.setVisibility(View.GONE);
            masterBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            masterBinding.toolbar.imgHome.setVisibility(View.GONE);
            masterBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
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
        if (key.equals(AppPreference.USER_INFO.USER_PROFILE)) {
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
//            builder = new AlertDialog.Builder(mActivity, android.R.style.Theme_Material_Dialog_Alert);
            builder = new AlertDialog.Builder(mActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mActivity);
        }
        builder.setTitle(getString(R.string.menu_sign_out));
        builder.setMessage(getString(R.string.are_you_sure_you_want_to_sign_out))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        signOut();
                        NavigationUtils.startActivityWithClearStack(mActivity, SignIn.class, null);
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

    public void signOut() {
//  -------------   TEMP    ---------------------------
        AppConstants.isUserPreferedLogOut = true;
        AppConstants.setLogoutFromApp(mActivity);
//  ---------------------------------------------------
        /*ViewUtils.showDialog(mActivity, false);
        Call<ArrayList<BeanLogOut>> listCall = ApiClient.getApiInterface().
                logOut(Pref.getValue(mActivity, AppPreference.USER_INFO.USER_ID, AppPreference.DEFAULT_STR),
                        Pref.getValue(mActivity, AppPreference.USER_INFO.TOKEN, AppPreference.DEFAULT_STR));

        listCall.enqueue(new Callback<ArrayList<BeanLogOut>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanLogOut>> call, Response<ArrayList<BeanLogOut>> response) {
                ArrayList<BeanLogOut> beanLogOut = response.body();
                ViewUtils.showDialog(mActivity, true);
                if (beanLogOut.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {
                    AppConstants.isUserPreferedLogOut = true;
                    AppConstants.setLogoutFromApp(mActivity);
                }
                else if (beanLogOut.get(0).getCode() != null &&
                        beanLogOut.get(0).getCode() ==
                                NetworkConstants.API_CODE_RESPONSE_INVALID_TOKEN) {
                    AppConstants.isUserPreferedLogOut = false;
                    AppConstants.setLogoutFromApp(mActivity);
                } else {
                    ViewUtils.showToast(mActivity, beanLogOut.get(0).getMessage(), null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BeanLogOut>> call, Throwable t) {
                ViewUtils.showDialog(mActivity, true);
                ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
            }
        });*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
