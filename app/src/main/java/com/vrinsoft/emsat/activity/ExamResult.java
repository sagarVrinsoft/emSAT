package com.vrinsoft.emsat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.apis.model.common.BinGeneralApiResp;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.apis.test_list.BinTestList;
import com.vrinsoft.emsat.apis.test_list.Result;
import com.vrinsoft.emsat.databinding.ActivityExamResultBinding;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.CORRECT_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.OBTAINED_SCORE;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.SKIPPED_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.TOTAL_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.TOTAL_SCORE;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.WRONG_ANS;

public class ExamResult extends MasterActivity implements View.OnClickListener {

    ActivityExamResultBinding mBinding;
    Activity mActivity;
    Bundle mBundle;
    int obtained_score = 0, total_score = 0, total_ans = 0, skipped_ans = 0, correct_ans = 0, wrong_ans = 0;
    private String testID, testName, subCatId;

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getContentLayout() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_exam_result, null, false);
        return mBinding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = ExamResult.this;
        setDrawerVisible(false);
        mBundle = getIntent().getExtras();

        masterBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        masterBinding.toolbar.txtRight.setVisibility(View.VISIBLE);
        masterBinding.toolbar.txtRight.setText(getString(R.string.done));
        masterBinding.toolbar.txtTitle.setText(getString(R.string.score_card));

        if (mBundle != null) {

            total_score = mBundle.getInt(TOTAL_SCORE);
            obtained_score = mBundle.getInt(OBTAINED_SCORE);
            total_ans = mBundle.getInt(TOTAL_ANS);
            skipped_ans = mBundle.getInt(SKIPPED_ANS);
            correct_ans = mBundle.getInt(CORRECT_ANS);
            wrong_ans = mBundle.getInt(WRONG_ANS);
            testID = mBundle.getString(AppConstants.INTENT_TEST_ID);
            testName = mBundle.getString(AppConstants.INTENT_TEST_NAME);
            subCatId = mBundle.getString(AppConstants.INTENT_SUBCAT_ID);

            mBinding.circleProgressView.setProgress(obtained_score * 100, 100);
            mBinding.txtSkip.setText(getString(R.string.skip_ans) + skipped_ans + "/" + total_ans);
            mBinding.txtCorrect.setText(getString(R.string.correct_ans) + correct_ans + "/" + total_ans);
            mBinding.txtWrong.setText(getString(R.string.wrong_ans) + wrong_ans + "/" + total_ans);
        }

        mBinding.txtReTake.setOnClickListener(this);
        mBinding.txtCheckAns.setOnClickListener(this);
        masterBinding.toolbar.txtRight.setOnClickListener(this);
        masterBinding.toolbar.imgBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                ViewUtils.showDoubleBtnAlert(mActivity, getString(R.string.dialog_title_are_you_sure),
                        getString(R.string.dialog_desc_go_without_submit_score),
                        getString(R.string.yes),
                        getString(R.string.submit),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which)
                                {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        NavigationUtils.finishCurrentActivity(mActivity);
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        callApiToSubmitScore();
                                        break;
                                }
                            }
                        });
                break;
            case R.id.txtRight:
                callApiToSubmitScore();
                break;
            case R.id.txtReTake:
                Bundle bundle = new Bundle();
                bundle.putString("FROM", "RE_TAKE");
                bundle.putString(AppConstants.INTENT_TEST_ID, testID);
                bundle.putString(AppConstants.INTENT_TEST_NAME, testName);
                bundle.putString(AppConstants.INTENT_SUBCAT_ID, subCatId);
                NavigationUtils.startActivity(mActivity, PracticeExam.class, bundle);
                finish();
                break;
            case R.id.txtCheckAns:
                bundle = new Bundle();
                bundle.putString("FROM", "CHECK_ANS");
                NavigationUtils.startActivity(mActivity, PracticeExam.class, bundle);
                finish();
                break;
        }
    }

    private void callApiToSubmitScore()
    {
        ViewUtils.showDialog(mActivity, false);
        Call<ArrayList<BinGeneralApiResp>> listCall = ApiClient.getApiInterface().submitTestScore
                (Pref.getUserId(mActivity),
                        subCatId,
                        testID,
                        String.valueOf(obtained_score),
                        "30",
                        Pref.getToken(mActivity));
        listCall.enqueue(new Callback<ArrayList<BinGeneralApiResp>>() {
            @Override
            public void onResponse(Call<ArrayList<BinGeneralApiResp>> call, Response<ArrayList<BinGeneralApiResp>> response) {
                ArrayList<BinGeneralApiResp> list = response.body();
                ViewUtils.showDialog(mActivity, true);
                ViewUtils.showToast(mActivity, list.get(0).getMessage(), null);
                NavigationUtils.finishCurrentActivity(mActivity);
            }

            @Override
            public void onFailure(Call<ArrayList<BinGeneralApiResp>> call, Throwable t) {
                ViewUtils.showDialog(mActivity, true);
                ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        masterBinding.toolbar.imgBack.performClick();
    }
}
