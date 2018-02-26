package com.vrinsoft.emsat.activity.mytest;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.subcategory.model.BeanNotificationList;
import com.vrinsoft.emsat.activity.subcategory.model.Result;
import com.vrinsoft.emsat.apis.api.subcategory_list.OnSubCatList;
import com.vrinsoft.emsat.apis.api.subcategory_list.SubCategoryListApiHandler;
import com.vrinsoft.emsat.databinding.ActivityMytestBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;


public class MyTestActivity extends MasterActivity {
    private static int PERCENT = 0;
    ActivityMytestBinding binding;
    Activity mActivity;
    ArrayList<Result> mArrayList = new ArrayList<>();
    MyTestListAdapter mAdapter;
    private SubCategoryListApiHandler subCategoryListApiHandler;
    private LinearLayoutManager linearLayoutManager;

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
        director = new Director(this);
        subCategoryListApiHandler = new SubCategoryListApiHandler();
        setUIConfig();
        fetchNotificationList(1);
    }

    private void setUIConfig() {
        binding.rvSubCategory.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        binding.rvSubCategory.setLayoutManager(linearLayoutManager);
        mAdapter = new MyTestListAdapter(mActivity, mArrayList, new MyTestListAdapter.OnClickable() {
            @Override
            public void getPosition(int position) {
                ViewUtils.showToast(mActivity, ""+position, null);
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
        masterBinding.toolbar.txtTitle.setText(getString(R.string.menu_my_test));
        masterBinding.toolbar.imgHome.setVisibility(View.VISIBLE);
        masterBinding.toolbar.imgBack.setVisibility(View.GONE);
        masterBinding.toolbar.rlNotification.setVisibility(View.GONE);
        masterBinding.toolbar.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });
    }

    private void fetchNotificationList(int pageNo) {

        ViewUtils.showDialog(mActivity, false);
        subCategoryListApiHandler.fetchSubCatData(Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_USER_ID, ""),
                Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_TOKEN, ""),
                pageNo, Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_USER_TYPE, 0), new OnSubCatList() {
                    @Override
                    public void getResponse(boolean isSuccess, ArrayList<BeanNotificationList> beanNotificationList, String errorMsgSystem) {
                        ViewUtils.showDialog(mActivity, true);
                        if (isSuccess) {
                            if (beanNotificationList.get(0).getCode() == AppConstants.API_CODE_RESPONSE_SUCCESS) {
                                mArrayList.clear();
                                mArrayList.addAll(beanNotificationList.get(0).getResult());
                                if (mArrayList.size() > 0) {
                                    binding.txtNoDataFound.setVisibility(View.GONE);
                                    binding.rvSubCategory.setVisibility(View.VISIBLE);
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    binding.txtNoDataFound.setVisibility(View.VISIBLE);
                                    binding.rvSubCategory.setVisibility(View.GONE);
                                }
                            } else {
                                if (AppConstants.isTestModeOn) {
                                    mArrayList.clear();
                                    for (int i = 1; i <= 4; i++) {
                                        Result result = new Result();
                                        result.setBroadcastMsg("Topic " + i);
                                        mArrayList.add(result);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    binding.txtNoDataFound.setVisibility(View.GONE);
                                    binding.rvSubCategory.setVisibility(View.VISIBLE);
                                } else {
                                    binding.txtNoDataFound.setVisibility(View.VISIBLE);
                                    binding.rvSubCategory.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            ViewUtils.showToast(mActivity, errorMsgSystem, null);
                        }
                    }
                });
    }

}
