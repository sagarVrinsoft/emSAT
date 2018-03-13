package com.vrinsoft.emsat.activity.mytest;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.PracticeExam;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.apis.test_list.BinTestList;
import com.vrinsoft.emsat.apis.test_list.Result;
import com.vrinsoft.emsat.databinding.ActivityMytestBinding;
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


public class MyTestActivity extends MasterActivity {
    private static int PERCENT = 0;
    ActivityMytestBinding binding;
    Activity mActivity;
    ArrayList<Result> mArrayList = new ArrayList<>();
    MyTestListAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    String subCatId, subCatName;
    Bundle bundle;
    String pageNo = "1";
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getContentLayout() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_mytest, null, false);
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setDrawerVisible(false);
        director = new Director(this);
        bundle = getIntent().getExtras();
        subCatId = bundle.getString(AppConstants.INTENT_SUBCAT_ID);
        subCatName = bundle.getString(AppConstants.INTENT_SUBCAT_NAME);
        setUIConfig();
        fetchData();
    }

    private void setUIConfig() {
        binding.rvSubCategory.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        binding.rvSubCategory.setLayoutManager(linearLayoutManager);
        mAdapter = new MyTestListAdapter(mActivity, mArrayList, new MyTestListAdapter.OnClickable() {
            @Override
            public void getPosition(int position) {
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
        masterBinding.toolbar.txtTitle.setText(subCatName);
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

    private void fetchData() {

        if (AppConstants.isTestModeOn) {
            mArrayList.clear();
            for (int i = 1; i <= 4; i++) {
                Result result = new Result();
                result.setTestName("Topic " + i);
                mArrayList.add(result);
            }
            mAdapter.notifyDataSetChanged();
            binding.txtNoDataFound.setVisibility(View.GONE);
            binding.rvSubCategory.setVisibility(View.VISIBLE);
        } else {
//            call APi
            ViewUtils.showDialog(mActivity, false);
            Call<ArrayList<BinTestList>> listCall =
                    ApiClient.getApiInterface().getListOfTests(
                            Pref.getUserId(mActivity),
                            subCatId,
                            Pref.getToken(mActivity),
                            pageNo
                    );

            listCall.enqueue(new Callback<ArrayList<BinTestList>>() {
                @Override
                public void onResponse(Call<ArrayList<BinTestList>> call, Response<ArrayList<BinTestList>> response) {
                    ArrayList<BinTestList> list = response.body();
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
                public void onFailure(Call<ArrayList<BinTestList>> call, Throwable t) {
                    ViewUtils.showDialog(mActivity, true);
                    ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
                }
            });
        }
    }

}
