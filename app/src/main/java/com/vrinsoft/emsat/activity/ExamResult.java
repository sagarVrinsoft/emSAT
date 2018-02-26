package com.vrinsoft.emsat.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.databinding.ActivityExamResultBinding;
import com.vrinsoft.emsat.utils.NavigationUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.CORRECT_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.OBTAINED_SCORE;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.SKIPPED_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.TOTAL_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.TOTAL_SCORE;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.WRONG_ANS;

public class ExamResult extends AppCompatActivity implements View.OnClickListener {

    ActivityExamResultBinding mBinding;
    Activity mActivity;
    Bundle mBundle;
    int obtained_score = 0, total_score = 0, total_ans = 0, skipped_ans = 0, correct_ans = 0, wrong_ans = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_exam_result);
        mActivity = ExamResult.this;
        mBundle = getIntent().getExtras();

        mBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.txtRight.setVisibility(View.VISIBLE);
        mBinding.toolbar.txtTitle.setText(getString(R.string.score_card));

        if (mBundle != null) {

            total_score = mBundle.getInt(TOTAL_SCORE);
            obtained_score = mBundle.getInt(OBTAINED_SCORE);
            total_ans = mBundle.getInt(TOTAL_ANS);
            skipped_ans = mBundle.getInt(SKIPPED_ANS);
            correct_ans = mBundle.getInt(CORRECT_ANS);
            wrong_ans = mBundle.getInt(WRONG_ANS);

            mBinding.circleProgressView.setProgress(obtained_score, total_score);

            mBinding.txtSkip.setText(getString(R.string.skip_ans) + skipped_ans + "/" + total_ans);
            mBinding.txtCorrect.setText(getString(R.string.correct_ans) + correct_ans + "/" + total_ans);
            mBinding.txtWrong.setText(getString(R.string.wrong_ans) + wrong_ans + "/" + total_ans);
        }

        mBinding.txtReTake.setOnClickListener(this);
        mBinding.txtCheckAns.setOnClickListener(this);
        mBinding.toolbar.txtRight.setOnClickListener(this);
        mBinding.toolbar.imgBack.setOnClickListener(this);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.txtRight:
                onBackPressed();
                break;
            case R.id.txtReTake:
                Bundle bundle = new Bundle();
                bundle.putString("FROM", "RE_TAKE");
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
}
