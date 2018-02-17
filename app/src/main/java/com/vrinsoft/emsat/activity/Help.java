package com.vrinsoft.emsat.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.cms.CMS;
import com.vrinsoft.emsat.databinding.ActivityHelpBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.NavigationUtils;

/**
 * Created by komal on 17/2/18.
 */

public class Help extends MasterActivity implements View.OnClickListener {
    ActivityHelpBinding helpBinding;
    Activity mActivity;
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getContentLayout() {
        helpBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_help, null, false);
        return helpBinding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUIListener();
    }

    private void setUIListener() {
        helpBinding.txtAboutUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtAboutUs:
                Bundle b = new Bundle();
                b.putString(Director.CMS_TITLE, getString(R.string.about_us));
                b.putString(CMS.KEY_CMS_ID, AppConstants.CMS.TERMS_CONDITIONS);
                NavigationUtils.startActivity(mActivity, CMS.class, b);
                break;
            case R.id.txtPrivacy:
                break;
            case R.id.txtTerms:
                break;
            case R.id.txtFaqs:
                break;
        }
    }
}
