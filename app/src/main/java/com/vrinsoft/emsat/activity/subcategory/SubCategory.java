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
import com.vrinsoft.emsat.activity.subcategory.model.Result;
import com.vrinsoft.emsat.databinding.ActivitySubcategoryBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;


public class SubCategory extends MasterActivity
{
    String mTitle = "";
    private static int PERCENT = 0;
    ActivitySubcategoryBinding binding;
    Activity mActivity;
    ArrayList<Result> mArrayList = new ArrayList<>();
    SubCategoryListAdapter mAdapter;
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
        setDrawerVisible(false);
        director = new Director(this);
        setUIConfig();
        fetchNotificationList(1);
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

                NavigationUtils.startActivity(mActivity, MyTestActivity.class, null);
            }
        });
        binding.rvSubCategory.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(AppConstants.INTENT_NAME))
        {
            mTitle = bundle.getString(AppConstants.INTENT_NAME);
        }
        setToolBarConfig();
    }

    public void setToolBarConfig() {
        masterBinding.toolbar.txtTitle.setVisibility(View.VISIBLE);
        masterBinding.toolbar.txtTitle.setText(mTitle);
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

    private void fetchNotificationList(int pageNo) {

        if(AppConstants.isTestModeOn) {
            mArrayList.clear();
            for (int i = 1; i <= 4; i++) {
                Result result = new Result();
                result.setBroadcastMsg("Topic " + i);
                mArrayList.add(result);
            }
            mAdapter.notifyDataSetChanged();
            binding.txtNoDataFound.setVisibility(View.GONE);
            binding.rvSubCategory.setVisibility(View.VISIBLE);

            binding.txtRefresh2.setText(mArrayList.get(0).getBroadcastMsg());
            binding.txtRefresh4.setText(mArrayList.get(1).getBroadcastMsg());
            binding.txtRefresh6.setText(mArrayList.get(2).getBroadcastMsg());
            binding.txtRefresh8.setText(mArrayList.get(3).getBroadcastMsg());
        }

    }

}
