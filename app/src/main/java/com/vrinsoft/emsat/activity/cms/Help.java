package com.vrinsoft.emsat.activity.cms;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.cms.faq.Faq;
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
    Bundle b = new Bundle();
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

        mActivity = this;
        setUIListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBarConfig();
    }

    public void setToolBarConfig() {
        masterBinding.toolbar.txtTitle.setVisibility(View.VISIBLE);
        masterBinding.toolbar.txtTitle.setText(getString(R.string.menu_help));
        masterBinding.toolbar.imgHome.setVisibility(View.VISIBLE);
        masterBinding.toolbar.imgBack.setVisibility(View.GONE);
        masterBinding.toolbar.rlNotification.setVisibility(View.GONE);
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

    private void setUIListener() {
        helpBinding.txtAboutUs.setOnClickListener(this);
        helpBinding.txtPrivacy.setOnClickListener(this);
        helpBinding.txtTerms.setOnClickListener(this);
        helpBinding.txtFaqs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtAboutUs:
                b = new Bundle();
                b.putString(Director.CMS_TITLE, getString(R.string.about_us));
                b.putString(CMS.KEY_CMS_ID, AppConstants.CMS.DISCLOSURE);
                NavigationUtils.startActivity(mActivity, CMS.class, b);
                break;
            case R.id.txtPrivacy:
                b = new Bundle();
                b.putString(Director.CMS_TITLE, getString(R.string.privacy_policy));
                b.putString(CMS.KEY_CMS_ID, AppConstants.CMS.PRIVACY_POLICY);
                NavigationUtils.startActivity(mActivity, CMS.class, b);
                break;
            case R.id.txtTerms:
                b = new Bundle();
                b.putString(Director.CMS_TITLE, getString(R.string.terms));
                b.putString(CMS.KEY_CMS_ID, AppConstants.CMS.TERMS_CONDITIONS);
                NavigationUtils.startActivity(mActivity, CMS.class, b);
                break;
            case R.id.txtFaqs:
//                b = new Bundle();
//                b.putString(Director.CMS_TITLE, getString(R.string.about_us));
//                b.putString(CMS.KEY_CMS_ID, AppConstants.CMS.TERMS_CONDITIONS);
                NavigationUtils.startActivity(mActivity, Faq.class, b);
                break;
        }
    }
}
