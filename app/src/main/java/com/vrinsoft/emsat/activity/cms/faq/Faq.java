package com.vrinsoft.emsat.activity.cms.faq;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;


import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.cms.faq.adapter.FAQExListAdapter;
import com.vrinsoft.emsat.apis.api.faqs.FAQApiHandler;
import com.vrinsoft.emsat.apis.api.faqs.OnFAQ;
import com.vrinsoft.emsat.apis.model.faqs.BeanFaqs;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.FaqBinding;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.LogUtils;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Faq extends MasterActivity implements View.OnClickListener {

    ExpandableListView expandableListView;
    Activity mActivity;
    FaqBinding mBinding;
    private ArrayList<BeanFaqs.Result> mCatagoryArrayList = null;
    private ArrayList<BeanFaqs.Result> mArrayListTemp = new ArrayList<BeanFaqs.Result>();;

    private FAQExListAdapter expListAdapter;

    private FAQApiHandler faqApiHandler;
    //region Pagination
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private int lastPage = 1;
//    private int total_record1 = 0;
    //endregion

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getContentLayout() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.faq, null, false);

        return mBinding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = Faq.this;
        setDrawerVisible(false);
        faqApiHandler = new FAQApiHandler();

        setFAQContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBarConfig();
    }

    private void setToolBarConfig() {
        masterBinding.toolbar.txtTitle.setVisibility(View.VISIBLE);
        masterBinding.toolbar.txtTitle.setText(getString(R.string.faqs));
        masterBinding.toolbar.imgHome.setVisibility(View.GONE);
        masterBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        masterBinding.toolbar.rlNotification.setVisibility(View.GONE);
        masterBinding.toolbar.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.finishCurrentActivity(Faq.this);
            }
        });
        masterBinding.toolbar.rlNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavigationUtils.startActivity(mActivity, Notification.class, null);
            }
        });
    }

    private void setFAQContent() {

        expandableListView = ((ExpandableListView) findViewById(R.id.exFAQ));
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        LogUtils.LOGD("==========PAGE NUMBER=====", lastPage + "");
        loading = false;
        fetchFAQList(lastPage);

        expandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (loading) {
                    LogUtils.LOGD("==========PAGE NUMBER=====", lastPage + "");
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        fetchFAQList(lastPage);
                    }
                }
            }
        });
    }

    private void fetchFAQList(final int pageNo) {
        ViewUtils.showDialog(mActivity, false);
        faqApiHandler.fetchFAQList(Pref.getValue(mActivity, AppPreference.USER_INFO.USER_ID, NetworkConstants.QUESTION.TEMP_USERid),
                Pref.getValue(mActivity, AppPreference.USER_INFO.TOKEN, NetworkConstants.QUESTION.TEMP_TOKEN), pageNo, new OnFAQ() {
                    @Override
                    public void getResponse(boolean isSuccess, ArrayList<BeanFaqs> beanFaq, String errorMsgSystem) {
                        if (isSuccess) {
                            if (beanFaq.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {

                                ViewUtils.showDialog(mActivity, true);

//                                total_record1 = beanFaq.get(0).getTotalCount();

                                if (beanFaq.get(0).getResult().size() != 0 && beanFaq.get(0).getResult() != null) {

                                    if (lastPage == 1) {
                                        mCatagoryArrayList = new ArrayList<>();
                                        mCatagoryArrayList = beanFaq.get(0).getResult();
                                    } else {
                                        mArrayListTemp.clear();
                                        mArrayListTemp = beanFaq.get(0).getResult();
                                    }

                                    if (lastPage == 1) {
                                        if (mCatagoryArrayList.size() > 0 && mCatagoryArrayList != null) {
                                            expListAdapter = new FAQExListAdapter(mActivity, mCatagoryArrayList);
                                            expandableListView.setAdapter(expListAdapter);
                                        }
                                    } else {
                                        mCatagoryArrayList.addAll(mArrayListTemp);
                                        expListAdapter.notifyDataSetChanged();
                                        //expListAdapter.addAll(beanFaq.get(0).getResult());
                                    }

                                    /*if (total_record1 == expListAdapter.getArrayList().size()) {
                                        loading = false;
                                    } else {
                                        lastPage++;
                                        loading = true;
                                    }*/
                                }
                            } else {
                                ViewUtils.showDialog(mActivity, true);
                                //ViewUtils.showToast(mActivity, beanFaq.get(0).getMessage(), null);
                            }
                        } else {
                            ViewUtils.showDialog(mActivity, true);
                            ViewUtils.showToast(mActivity, errorMsgSystem, null);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgHome:
                ((MasterActivity) mActivity).toggleDrawer();
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
