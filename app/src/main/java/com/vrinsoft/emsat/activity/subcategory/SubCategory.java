package com.vrinsoft.emsat.activity.subcategory;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.PracticeExam;
import com.vrinsoft.emsat.activity.mytest.MyTestActivity;
import com.vrinsoft.emsat.activity.subcategory.model.BeanNotificationList;
import com.vrinsoft.emsat.apis.model.submodules.BinSubModulesResp;
import com.vrinsoft.emsat.apis.model.submodules.Result;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.ActivitySubcategoryBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubCategory extends MasterActivity
{
    private static int PERCENT = 0;
    ActivitySubcategoryBinding binding;
    Activity mActivity;
    ArrayList<Result> mArrayList = new ArrayList<>();
    SubCategoryListAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    Bundle bundle;
    String mod_name, mod_id;

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getContentLayout() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_subcategory, null, false);
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        bundle = getIntent().getExtras();
        mod_id = bundle.getString(AppConstants.INTENT_MODULE_ID);
        mod_name = bundle.getString(AppConstants.INTENT_MODULE_NAME);
        setDrawerVisible(false);
        director = new Director(this);
        setUIConfig();
        fetchData(1);
    }
    private void setUIConfig() {
        binding.rvSubCategory.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        binding.rvSubCategory.setLayoutManager(linearLayoutManager);
        mAdapter = new SubCategoryListAdapter(mActivity, mArrayList, new SubCategoryListAdapter.OnClickable() {
            @Override
            public void getPosition(int position) {
                /*int vOf1 = 100/mArrayList.size();
                PERCENT = (position+1)*vOf1;
                binding.progressBarChart.setPercent(PERCENT);*/

                Bundle bundle = new Bundle();
                bundle.putString("FROM", "NEWEST");
                bundle.putString(AppConstants.INTENT_TEST_ID, mArrayList.get(position).getTestId());
                bundle.putString(AppConstants.INTENT_TEST_NAME, mArrayList.get(position).getTestName());
                NavigationUtils.startActivity(mActivity, PracticeExam.class, bundle);
            }
        });
        binding.rvSubCategory.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBarConfig();
    }

    public void setToolBarConfig() {
        masterBinding.toolbar.txtTitle.setVisibility(View.VISIBLE);
        masterBinding.toolbar.txtTitle.setText(mod_name);
        masterBinding.toolbar.imgHome.setVisibility(View.GONE);
        masterBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        masterBinding.toolbar.rlNotification.setVisibility(View.GONE);
        masterBinding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.finishCurrentActivity(mActivity);
            }
        });
    }

    private void fetchData(int pageNo) {

        if(AppConstants.isTestModeOn) {
            mArrayList.clear();
            for (int i = 1; i <= 4; i++) {
                Result result = new Result();
                result.setTestName("Topic " + i);
                mArrayList.add(result);
            }
            mAdapter.notifyDataSetChanged();
            binding.txtNoDataFound.setVisibility(View.GONE);
            binding.rvSubCategory.setVisibility(View.VISIBLE);

            binding.txtRefresh2.setText(mArrayList.get(0).getTestName());
            binding.txtRefresh4.setText(mArrayList.get(1).getTestName());
            binding.txtRefresh6.setText(mArrayList.get(2).getTestName());
            binding.txtRefresh8.setText(mArrayList.get(3).getTestName());
        }
        else
        {
            ViewUtils.showDialog(mActivity, false);
            Call<ArrayList<BinSubModulesResp>> list = ApiClient.getApiInterface().getListOfSubModules
                    (Pref.getUserId(mActivity),
                            mod_id,
                            Pref.getToken(mActivity),
                            "1");
            list.enqueue(new Callback<ArrayList<BinSubModulesResp>>() {
                @Override
                public void onResponse(retrofit2.Call<ArrayList<BinSubModulesResp>> call, Response<ArrayList<BinSubModulesResp>> response) {
                    ArrayList<BinSubModulesResp> list = response.body();
                    ViewUtils.showDialog(mActivity, true);
                    if (list.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {
                        List<Result> listData = list.get(0).getResult();
                        if (listData != null && listData.size() > 0) {
                            mArrayList.clear();
                            mArrayList.addAll(listData);
                            mAdapter.notifyDataSetChanged();
                            binding.txtNoDataFound.setVisibility(View.GONE);
                            binding.rvSubCategory.setVisibility(View.VISIBLE);
                        } else {
                            binding.txtNoDataFound.setVisibility(View.VISIBLE);
                            binding.rvSubCategory.setVisibility(View.GONE);
                        }
                    }
                    else {
                        ViewUtils.showToast(mActivity, list.get(0).getMessage(), null);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<ArrayList<BinSubModulesResp>> call, Throwable t) {
                    ViewUtils.showDialog(mActivity, true);
                    ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
                }
            });
        }

    }

}
