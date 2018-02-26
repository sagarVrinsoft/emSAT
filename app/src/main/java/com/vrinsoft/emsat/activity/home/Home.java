package com.vrinsoft.emsat.activity.home;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.home.model.category.Result;
import com.vrinsoft.emsat.activity.subcategory.SubCategory;
import com.vrinsoft.emsat.apis.api.category.subcategory_list.CategoryListApiHandler;
import com.vrinsoft.emsat.apis.api.category.subcategory_list.OnCatList;
import com.vrinsoft.emsat.databinding.ActivityHomeBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;


public class Home extends MasterActivity {
    ActivityHomeBinding binding;
    Activity mActivity;
    MainCategoryListAdapter mAdapter;
    ArrayList<Result> mArrayList = new ArrayList<>();
    private CategoryListApiHandler categoryListApiHandler;
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
        categoryListApiHandler = new CategoryListApiHandler();
        setUIConfig();
        fetchNotificationList(1);
    }

    private void setUIConfig() {
        binding.rvGridModules.setNestedScrollingEnabled(false);
//        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL,
//                false);
        binding.rvGridModules.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MainCategoryListAdapter(mActivity, mArrayList, new MainCategoryListAdapter.OnClickable() {
            @Override
            public void getPosition(int position) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.INTENT_NAME, mArrayList.get(position).getBroadcastMsg());
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
        masterBinding.toolbar.imgBack.setVisibility(View.GONE);
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

    private void fetchNotificationList(int pageNo) {

        ViewUtils.showDialog(mActivity, false);
        categoryListApiHandler.fetchCatData(Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_USER_ID, ""),
                Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_TOKEN, ""),
                pageNo, Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_USER_TYPE, 0), new OnCatList() {
                    @Override
                    public void getResponse(boolean isSuccess, ArrayList<com.vrinsoft.emsat.activity.home.model.category.BeanNotificationList> beanNotificationList, String errorMsgSystem) {
                        ViewUtils.showDialog(mActivity, true);
                        if (isSuccess) {
                            if (beanNotificationList.get(0).getCode() == AppConstants.API_CODE_RESPONSE_SUCCESS) {
                                mArrayList.clear();
                                mArrayList.addAll(beanNotificationList.get(0).getResult());
                                if (mArrayList.size() > 0) {
                                    binding.txtNoDataFound.setVisibility(View.GONE);
                                    binding.rvGridModules.setVisibility(View.VISIBLE);
                                    mAdapter.notifyDataSetChanged();

                                } else {
                                    binding.txtNoDataFound.setVisibility(View.VISIBLE);
                                    binding.rvGridModules.setVisibility(View.GONE);
                                }
                            } else {
                                if (AppConstants.isTestModeOn) {
                                    mArrayList.clear();
                                    for (int i = 1; i <= 4; i++) {
                                        Result result = new Result();
                                        result.setBroadcastMsg("Subject " + i);
                                        mArrayList.add(result);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    binding.txtNoDataFound.setVisibility(View.GONE);
                                    binding.rvGridModules.setVisibility(View.VISIBLE);
                                } else {
                                    binding.txtNoDataFound.setVisibility(View.VISIBLE);
                                    binding.rvGridModules.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            ViewUtils.showToast(mActivity, errorMsgSystem, null);
                        }
                    }
                });
    }
}