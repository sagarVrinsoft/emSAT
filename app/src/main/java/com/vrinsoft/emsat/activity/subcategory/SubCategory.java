package com.vrinsoft.emsat.activity.subcategory;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.mytest.MyTestActivity;
import com.vrinsoft.emsat.apis.model.SubCategory.BinSubCategory;
import com.vrinsoft.emsat.apis.model.SubCategory.Result;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.ActivitySubcategoryBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubCategory extends MasterActivity {
    private static int PERCENT = 0;
    ActivitySubcategoryBinding binding;
    Activity mActivity;
    ArrayList<Result> mArrayList = new ArrayList<>();
    SubCategoryListAdapter mAdapter;
    Bundle bundle;
    String CAT_name, CAT_id;
    private LinearLayoutManager linearLayoutManager;

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
        CAT_id = bundle.getString(AppConstants.INTENT_CAT_ID);
        CAT_name = bundle.getString(AppConstants.INTENT_CAT_NAME);
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
                bundle.putString(AppConstants.INTENT_SUBCAT_ID, mArrayList.get(position).getSubCategoryId());
                bundle.putString(AppConstants.INTENT_SUBCAT_NAME, mArrayList.get(position).getSubCategoryName());
                NavigationUtils.startActivity(mActivity, MyTestActivity.class, bundle);
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
        masterBinding.toolbar.txtTitle.setText(CAT_name);
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

        if (AppConstants.isTestModeOn) {
            mArrayList.clear();
            for (int i = 1; i <= 4; i++) {
                Result result = new Result();
                result.setSubCategoryName("Topic " + i);
                mArrayList.add(result);
            }
            mAdapter.notifyDataSetChanged();
            binding.txtNoDataFound.setVisibility(View.GONE);
            binding.rvSubCategory.setVisibility(View.VISIBLE);

            binding.txtRefresh2.setText(mArrayList.get(0).getSubCategoryName());
            binding.txtRefresh4.setText(mArrayList.get(1).getSubCategoryName());
            binding.txtRefresh6.setText(mArrayList.get(2).getSubCategoryName());
            binding.txtRefresh8.setText(mArrayList.get(3).getSubCategoryName());
        } else {
            ViewUtils.showDialog(mActivity, false);
            Call<ArrayList<BinSubCategory>> list =
                    ApiClient.getApiInterface().getListOfSubCategories
                            (Pref.getUserId(mActivity),
                                    CAT_id,
                                    Pref.getToken(mActivity));
            list.enqueue(new Callback<ArrayList<BinSubCategory>>() {
                @Override
                public void onResponse(retrofit2.Call<ArrayList<BinSubCategory>> call, Response<ArrayList<BinSubCategory>> response) {
                    ArrayList<BinSubCategory> list = response.body();
                    ViewUtils.showDialog(mActivity, true);
                    if (list.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {
                        List<Result> listData = list.get(0).getResult();
                        if (listData != null && listData.size() > 0) {
                            mArrayList.clear();
                            mArrayList.addAll(listData);
                            mAdapter.notifyDataSetChanged();
                            binding.txtNoDataFound.setVisibility(View.GONE);
                            binding.rvSubCategory.setVisibility(View.VISIBLE);

                            if (mArrayList.size() > 0) {
                                binding.txtRefresh2.setText(mArrayList.get(0).getSubCategoryName());
                                binding.txtRefresh2.setVisibility(View.VISIBLE);
                                binding.txtRefresh4.setVisibility(View.GONE);
                                binding.txtRefresh6.setVisibility(View.GONE);
                                binding.txtRefresh8.setVisibility(View.GONE);
                            }
                            if (mArrayList.size() > 1) {
                                binding.txtRefresh4.setText(mArrayList.get(1).getSubCategoryName());
                                binding.txtRefresh2.setVisibility(View.VISIBLE);
                                binding.txtRefresh4.setVisibility(View.VISIBLE);
                                binding.txtRefresh6.setVisibility(View.GONE);
                                binding.txtRefresh8.setVisibility(View.GONE);
                            }
                            if (mArrayList.size() > 2) {
                                binding.txtRefresh6.setText(mArrayList.get(2).getSubCategoryName());
                                binding.txtRefresh2.setVisibility(View.VISIBLE);
                                binding.txtRefresh4.setVisibility(View.VISIBLE);
                                binding.txtRefresh6.setVisibility(View.VISIBLE);
                                binding.txtRefresh8.setVisibility(View.GONE);
                            }
                            if (mArrayList.size() > 3) {
                                binding.txtRefresh8.setText(mArrayList.get(3).getSubCategoryName());
                                binding.txtRefresh2.setVisibility(View.VISIBLE);
                                binding.txtRefresh4.setVisibility(View.VISIBLE);
                                binding.txtRefresh6.setVisibility(View.VISIBLE);
                                binding.txtRefresh8.setVisibility(View.VISIBLE);
                            }

                            if (mArrayList.size() == 0) {
                                setDefaultText();
                            }
                        } else {
                            setDefaultText();
                            binding.txtNoDataFound.setVisibility(View.VISIBLE);
                            binding.rvSubCategory.setVisibility(View.GONE);
                        }
                    } else {
                        setDefaultText();
                        ViewUtils.showToast(mActivity, list.get(0).getMessage(), null);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<ArrayList<BinSubCategory>> call, Throwable t) {
                    ViewUtils.showDialog(mActivity, true);
                    ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
                    setDefaultText();
                }
            });
        }

    }

    public void setDefaultText() {
        binding.txtRefresh2.setText("");
        binding.txtRefresh4.setText("");
        binding.txtRefresh6.setText("");
        binding.txtRefresh8.setText("");
    }

}
