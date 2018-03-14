package com.vrinsoft.emsat.activity.home;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.subcategory.SubCategory;
import com.vrinsoft.emsat.apis.model.category.BinCategory;
import com.vrinsoft.emsat.apis.model.category.Result;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.ActivityHomeBinding;
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


public class Home extends MasterActivity {
    ActivityHomeBinding binding;
    Activity mActivity;
    MainCategoryListAdapter mAdapter;
    ArrayList<Result> mArrayList = new ArrayList<>();
    String pageNo = "1";
    private LinearLayoutManager linearLayoutManager;

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
        director = new Director(this);
        setUIConfig();
        fetchData();
    }

    private void setUIConfig() {
        binding.rvGridModules.setNestedScrollingEnabled(false);
        binding.rvGridModules.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MainCategoryListAdapter(mActivity, mArrayList, new MainCategoryListAdapter.OnClickable() {
            @Override
            public void getPosition(int position) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.INTENT_CAT_NAME, mArrayList.get(position).getCategoryName());
                bundle.putString(AppConstants.INTENT_CAT_ID, mArrayList.get(position).getCategoryId());
                NavigationUtils.startActivity(mActivity, SubCategory.class, bundle);
            }
        });
        binding.rvGridModules.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBarConfig();
    }

    public void setToolBarConfig() {
        masterBinding.toolbar.txtTitle.setVisibility(View.VISIBLE);
        masterBinding.toolbar.txtTitle.setText(getString(R.string.app_name));
        masterBinding.toolbar.imgHome.setVisibility(View.VISIBLE);
        masterBinding.toolbar.imgHome.setImageResource(R.drawable.ic_home_black);
        masterBinding.toolbar.imgBack.setVisibility(View.GONE);
        masterBinding.toolbar.rlNotification.setVisibility(View.GONE);
        masterBinding.toolbar.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });
    }

    private void fetchData() {

        if (AppConstants.isTestModeOn) {
            mArrayList.clear();
            for (int i = 1; i <= 4; i++) {
                Result result = new Result();
                result.setCategoryName("Subject " + i);
                mArrayList.add(result);
            }
            mAdapter.notifyDataSetChanged();
            binding.txtNoDataFound.setVisibility(View.GONE);
            binding.rvGridModules.setVisibility(View.VISIBLE);
        } else {
            ViewUtils.showDialog(mActivity, false);
            Call<ArrayList<BinCategory>> listCall =
                    ApiClient.getApiInterface().getListOfCategories(
                            Pref.getUserId(mActivity),
                            Pref.getToken(mActivity),
                            pageNo
                    );

            listCall.enqueue(new Callback<ArrayList<BinCategory>>() {
                @Override
                public void onResponse(Call<ArrayList<BinCategory>> call, Response<ArrayList<BinCategory>> response) {
                    ArrayList<BinCategory> list = response.body();
                    ViewUtils.showDialog(mActivity, true);
                    if (list.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {
                        List<Result> listData = list.get(0).getResult();
                        if (listData != null && listData.size() > 0) {
                            mArrayList.clear();
                            mArrayList.addAll(listData);
                            mAdapter.notifyDataSetChanged();
                            binding.txtNoDataFound.setVisibility(View.GONE);
                            binding.rvGridModules.setVisibility(View.VISIBLE);
                        } else {
                            binding.txtNoDataFound.setVisibility(View.VISIBLE);
                            binding.rvGridModules.setVisibility(View.GONE);
                        }
                    } else {
                        ViewUtils.showToast(mActivity, list.get(0).getMessage(), null);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<BinCategory>> call, Throwable t) {
                    ViewUtils.showDialog(mActivity, true);
                    ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
                }
            });
        }

    }
}