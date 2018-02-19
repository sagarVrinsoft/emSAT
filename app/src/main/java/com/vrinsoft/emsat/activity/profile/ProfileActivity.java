package com.vrinsoft.emsat.activity.profile;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.databinding.ActivityProfileBinding;

/**
 * Created by komal on 19/2/18.
 */

public class ProfileActivity extends MasterActivity
{
    ActivityProfileBinding profileBinding;
    Activity mActivity;
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getContentLayout() {
        profileBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_profile, null, false);
        return profileBinding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }
}
