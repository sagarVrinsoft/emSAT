package com.vrinsoft.emsat.activity;

import android.app.Activity;
import android.app.Notification;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.databinding.ActivityHomeBinding;
import com.vrinsoft.emsat.utils.NavigationUtils;


public class Home extends MasterActivity
{
    // Develop
    ActivityHomeBinding binding;
    Activity mActivity;

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getContentLayout() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_home, null, false);
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBarConfig();
    }

    public void setToolBarConfig() {
        masterBinding.toolbar.txtTitle.setVisibility(View.GONE);
        masterBinding.toolbar.imgHome.setVisibility(View.VISIBLE);
        masterBinding.toolbar.rlNotification.setVisibility(View.VISIBLE);
        masterBinding.toolbar.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });
        masterBinding.toolbar.rlNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavigationUtils.startActivity(mActivity, Notification.class, null);
            }
        });
        //setNotificationBadgeCount(mActivity);
    }
}
