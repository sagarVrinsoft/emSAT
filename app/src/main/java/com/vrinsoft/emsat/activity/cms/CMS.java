package com.vrinsoft.emsat.activity.cms;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.apis.api.cms.CMSApiHandler;
import com.vrinsoft.emsat.apis.api.cms.OnCMS;
import com.vrinsoft.emsat.apis.model.cms.BeanCMS;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.ActivityCmsBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CMS extends AppCompatActivity implements View.OnClickListener {

    public static String KEY_CMS_ID = "cms_id";
    Bundle bundle;
    String title, cmsID = "";
    ActivityCmsBinding mBinding;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cms);

        mActivity = this;
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
        Call<ArrayList<BeanCMS>> listCall = ApiClient.getApiInterface().cms(cmsID);

        listCall.enqueue(new Callback<ArrayList<BeanCMS>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanCMS>> call, Response<ArrayList<BeanCMS>> response) {
                ArrayList<BeanCMS> beanCMS = response.body();
                ViewUtils.showDialog(mActivity, true);
                if (beanCMS.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {
                    ArrayList<BeanCMS.Result> listCms = beanCMS.get(0).getResult();
                    if (listCms != null && listCms.size() > 0) {
                        mBinding.mWebview.loadData(listCms.get(0).getDescription(), "text/html", "UTF-8");
                    } else {
                        ViewUtils.showToast(mActivity, getString(R.string.unable_to_load_content), mBinding.mWebview);
                    }
                }
                else {
                    ViewUtils.showToast(mActivity, beanCMS.get(0).getMessage(), null);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<BeanCMS>> call, Throwable t) {
                ViewUtils.showDialog(mActivity, true);
                ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
            }
        });
    }

    private void setToolBarConfig() {
        mBinding.toolBar.txtTitle.setText(title);
        mBinding.toolBar.imgBack.setVisibility(View.VISIBLE);
        mBinding.toolBar.imgBack.setOnClickListener(this);
        mBinding.toolBar.imgHome.setVisibility(View.GONE);
        mBinding.toolBar.rlNotification.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                NavigationUtils.finishCurrentActivity(this);
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
