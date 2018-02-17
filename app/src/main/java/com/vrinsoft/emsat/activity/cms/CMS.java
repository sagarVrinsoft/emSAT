package com.vrinsoft.emsat.activity.cms;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.apis.api.cms.CMSApiHandler;
import com.vrinsoft.emsat.apis.api.cms.OnCMS;
import com.vrinsoft.emsat.apis.model.cms.BeanCMS;
import com.vrinsoft.emsat.databinding.ActivityCmsBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;

public class CMS extends AppCompatActivity implements View.OnClickListener {

    public static String KEY_CMS_ID = "cms_id";
    Bundle bundle;
    String title, cmsID = "";
    ActivityCmsBinding mBinding;
    CMSApiHandler cmsApiHandler;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cms);

        mActivity = this;
        cmsApiHandler = new CMSApiHandler();

        bundle = getIntent().getExtras();

        if (bundle != null) {
            title = bundle.getString(Director.CMS_TITLE);
            cmsID = bundle.getString(KEY_CMS_ID);
        }

        mBinding.mWebview.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBarConfig();
        callCMS();
    }

    private void callCMS() {
        ViewUtils.showDialog(mActivity, false);
        cmsApiHandler.cmsData(cmsID, new OnCMS() {
            @Override
            public void getResponse(boolean isSuccess, ArrayList<BeanCMS> cms, String errorMsgSystem) {
                if (isSuccess) {
                    if (cms.get(0).getCode() == AppConstants.API_CODE_RESPONSE_SUCCESS) {
                        ViewUtils.showDialog(mActivity, true);
                        ArrayList<BeanCMS.Result> listCms = cms.get(0).getResult();
                        if (listCms != null && listCms.size() > 0) {
                            mBinding.mWebview.loadData(listCms.get(0).getDescription(), "text/html", "UTF-8");
                        } else {
                            ViewUtils.showToast(mActivity, getString(R.string.unable_to_load_content), mBinding.mWebview);
                        }
                    } else {
                        ViewUtils.showDialog(mActivity, true);
                        ViewUtils.showToast(mActivity, cms.get(0).getMessage(), null);
                    }
                } else {
                    ViewUtils.showDialog(mActivity, true);
                    ViewUtils.showToast(mActivity, errorMsgSystem, null);
                }
            }
        });
    }

    private void setToolBarConfig() {
        mBinding.toolBar.txtTitle.setText(title);
        mBinding.toolBar.imgBack.setVisibility(View.VISIBLE);
        mBinding.toolBar.imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                NavigationUtils.finishCurrentActivity(this);
                break;
        }
    }
}
