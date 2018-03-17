package com.vrinsoft.emsat.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vrinsoft.emsat.Adapter.WordsAdapter;
import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.apis.handler_interface.ApiHandler;
import com.vrinsoft.emsat.apis.handler_interface.OnResponse;
import com.vrinsoft.emsat.apis.model.exam_question.QuestionBean;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.ActivityPracticeExamBinding;
import com.vrinsoft.emsat.databinding.DialogHintBinding;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;
import com.vrinsoft.emsat.utils.widget.TagLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.vrinsoft.emsat.apis.rest.NetworkConstants.KEY_TOKEN;
import static com.vrinsoft.emsat.apis.rest.NetworkConstants.KEY_USER_ID;
import static com.vrinsoft.emsat.apis.rest.NetworkConstants.QUESTION.KEY_TEST_ID;
import static com.vrinsoft.emsat.utils.AppConstants.ANS_TYPE.CORRECT;
import static com.vrinsoft.emsat.utils.AppConstants.ANS_TYPE.SKIP;
import static com.vrinsoft.emsat.utils.AppConstants.ANS_TYPE.WRONG;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.CORRECT_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.OBTAINED_SCORE;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.SKIPPED_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.TOTAL_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.TOTAL_SCORE;
import static com.vrinsoft.emsat.utils.AppConstants.BUNDLE_KEY.WRONG_ANS;
import static com.vrinsoft.emsat.utils.AppConstants.QUESTION_TYPE.FILL_BLANK;
import static com.vrinsoft.emsat.utils.AppConstants.QUESTION_TYPE.MCQ;
import static com.vrinsoft.emsat.utils.AppConstants.QUESTION_TYPE.TRUE_FALSE;
import static com.vrinsoft.emsat.utils.LogUtils.LOGD;
import static com.vrinsoft.emsat.utils.ViewUtils.SpannableText;
import static com.vrinsoft.emsat.utils.ViewUtils.showDoubleBtnAlert;
import static com.vrinsoft.emsat.utils.ViewUtils.showSingleBtnAlert;
import static com.vrinsoft.emsat.utils.ViewUtils.showToast;

public class PracticeExam extends MasterActivity implements View.OnClickListener {
    ApiHandler apiHandler;
    Activity mActivity;
    ArrayList<QuestionBean.Result> mArrayList = new ArrayList<>();
    ActivityPracticeExamBinding mBinding;
    TagLayout tagLayout;
    ArrayList<String> wordsList;
    WordsAdapter mAdapter;
    String QUESTION = "";
    QuestionBean.Result questionBean;
    int progress;
    int endTime;
    CountDownTimer countDownTimer;
    GridLayoutManager layoutManager;
    Bundle mBundle;
    String mFrom = "";
    boolean is_view_only = false;
    private int pos = 0;
    private String testID, testName, subCatId;

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getContentLayout() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_practice_exam, null, false);
        return mBinding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = PracticeExam.this;
        setDrawerVisible(false);
        apiHandler = new ApiHandler();
        tagLayout = (TagLayout) findViewById(R.id.mFrameLayout);

        masterBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        masterBinding.toolbar.txtTitle.setText(getString(R.string.practice_exam));

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            if(mBundle.containsKey(AppConstants.INTENT_TEST_ID))
            {
                testID = mBundle.getString(AppConstants.INTENT_TEST_ID);
            }
            if(mBundle.containsKey(AppConstants.INTENT_TEST_NAME))
            {
                testName = mBundle.getString(AppConstants.INTENT_TEST_NAME);
                masterBinding.toolbar.txtTitle.setText(testName);
            }
            if(mBundle.containsKey(AppConstants.INTENT_SUBCAT_ID))
            {
                subCatId = mBundle.getString(AppConstants.INTENT_SUBCAT_ID);
            }
            mFrom = mBundle.getString("FROM");
            if (mFrom.equals("CHECK_ANS")) {
                is_view_only = true;
                masterBinding.toolbar.txtRight.setVisibility(View.GONE);
                mBinding.headerBar.setVisibility(View.GONE);

                if (AppConstants.mQuestionList != null && AppConstants.mQuestionList.size() > 0)
                    mArrayList.clear();
                    mArrayList.addAll(AppConstants.mQuestionList);

            } else {
                is_view_only = false;
                masterBinding.toolbar.txtRight.setVisibility(View.VISIBLE);
                mBinding.headerBar.setVisibility(View.VISIBLE);
            }
        }

        layoutManager = new GridLayoutManager(mActivity, 3);
        mBinding.rvWords.setLayoutManager(layoutManager);


        if (!is_view_only) {
            getQuestionList();
            mBinding.rbTrue.setOnClickListener(this);
            mBinding.rbFalse.setOnClickListener(this);

            mBinding.txtOpt1.setOnClickListener(this);
            mBinding.txtOpt2.setOnClickListener(this);
            mBinding.txtOpt3.setOnClickListener(this);
            mBinding.txtOpt4.setOnClickListener(this);
        } else
            setData();

        mBinding.txtNext.setOnClickListener(this);
        mBinding.txtPrev.setOnClickListener(this);
        mBinding.llHint.setOnClickListener(this);
        mBinding.llResetDraggableOptions.setOnClickListener(this);
        masterBinding.toolbar.txtRight.setOnClickListener(this);
        masterBinding.toolbar.imgBack.setOnClickListener(this);
    }

    private void getQuestionList() {
        ViewUtils.showDialog(mActivity, false);

        HashMap<String, String> params = new HashMap<>();
        params.put(KEY_USER_ID, Pref.getUserId(mActivity));
        params.put(KEY_TOKEN, Pref.getToken(mActivity));
        params.put(KEY_TEST_ID, testID);

        LOGD("PARAMS::", params + "");

        Call<ResponseBody> listCall = ApiClient.getApiInterface().getQuestions(params);
        apiHandler.request(listCall, new OnResponse() {
            @Override
            public void getResponse(boolean isSuccess, Response<ResponseBody> response, JSONObject errorObject, String errorMsgSystem) {
                ViewUtils.showDialog(mActivity, true);
                if (isSuccess) {
                    if (response != null) {
                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<Collection<QuestionBean>>() {
                        }.getType();
                        ArrayList<QuestionBean> mainArray = null;
                        try {
                            mainArray = gson.fromJson(response.body().string(), collectionType);
                            if (mainArray.get(0).getCode() == 1) {
                                mArrayList.clear();
                                mArrayList.addAll(mainArray.get(0).getResult()==null?new ArrayList<QuestionBean.Result>():mainArray.get(0).getResult());
                                if(mArrayList!=null && mArrayList.size()>0) {
                                    setData();
                                    startCountDown();
                                }
                                else
                                {
                                    setNoneData();
                                    showToast(mActivity, mainArray.get(0).getMessage(), null);
                                }

                            }
                            else
                            {
                                setNoneData();
                                showToast(mActivity, mainArray.get(0).getMessage(), null);
                            }
                        } catch (IOException e) {
                            setNoneData();
                            e.printStackTrace();
                        }
                    } else if (errorObject != null) {
                        setNoneData();
                    }
                } else if (errorMsgSystem.equals(ApiErrorUtils.SOMETHING_WENT_WRONG) || errorMsgSystem.equals(ApiErrorUtils.ERROR_NETWORK)) {
                    showToast(mActivity, errorMsgSystem, null);
                    mBinding.txtNext.setVisibility(View.VISIBLE);
                    mBinding.txtPrev.setVisibility(View.VISIBLE);
                    masterBinding.toolbar.txtRight.setVisibility(View.GONE);
                    setNoneData();
                }

            }
        });
    }

    private void setNoneData() {
        mBinding.txtNext.setVisibility(View.GONE);
        mBinding.txtPrev.setVisibility(View.GONE);
        masterBinding.toolbar.txtRight.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbTrue:
                setCheckedTrueFalse(mBinding.rbTrue, mBinding.rbFalse);
                mArrayList.get(pos).setUser_ans("true");
                break;
            case R.id.rbFalse:
                setCheckedTrueFalse(mBinding.rbFalse, mBinding.rbTrue);
                mArrayList.get(pos).setUser_ans("false");
                break;
            case R.id.txtOpt1:
                setCheckedMCQ(mBinding.txtOpt1, mBinding.txtOpt2, mBinding.txtOpt3, mBinding.txtOpt4);
                mArrayList.get(pos).setUser_ans("1");
                break;
            case R.id.txtOpt2:
                setCheckedMCQ(mBinding.txtOpt2, mBinding.txtOpt1, mBinding.txtOpt3, mBinding.txtOpt4);
                mArrayList.get(pos).setUser_ans("2");
                break;
            case R.id.txtOpt3:
                setCheckedMCQ(mBinding.txtOpt3, mBinding.txtOpt2, mBinding.txtOpt1, mBinding.txtOpt4);
                mArrayList.get(pos).setUser_ans("3");
                break;
            case R.id.txtOpt4:
                setCheckedMCQ(mBinding.txtOpt4, mBinding.txtOpt2, mBinding.txtOpt3, mBinding.txtOpt1);
                mArrayList.get(pos).setUser_ans("4");
                break;
            /*case R.id.btnReset:
                reset();
                break;*/
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.txtNext:
                if (!is_view_only)
                    setFillData();

                if (mArrayList!=null && pos < mArrayList.size() - 1) {
                    pos++;
                    setData();
                }
                break;
            case R.id.txtPrev:
                if (!is_view_only)
                    setFillData();

                if (pos > 0) {
                    pos--;
                    setData();
                }
                break;
            case R.id.txtRight:
                if (mArrayList!=null && pos == mArrayList.size() - 1) {
                    stopCountDown();
                    getFinalScore();
                } else {
                    showDoubleBtnAlert(mActivity, "Quit Exam", "Are You sure you want to quit exam?",
                            getString(android.R.string.yes), getString(android.R.string.no),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i)
                                    {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            stopCountDown();
                                            getFinalScore();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            dialogInterface.dismiss();
                                            break;
                                    }
                                }
                            });
                }
                break;
            case R.id.llHint:
                showHintDialog();
                /*if (questionBean.getQuestionType().equals(FILL_BLANK))
                    reset();
                else
                    showHintDialog();*/
                break;
            case R.id.llResetDraggableOptions:
                reset();
                break;
        }
    }

    private void showHintDialog() {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_DeviceDefault_Dialog);
        DialogHintBinding dialogHintBinding = DataBindingUtil.inflate
                (getLayoutInflater(), R.layout.dialog_hint, null, false);
        dialog.setContentView(dialogHintBinding.getRoot());
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogStyle);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            dialogHintBinding.txtDescription.setText();

        dialogHintBinding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    void startCountDown() {
        progress = 1;
        endTime = 30; // up to finish time

        countDownTimer = new CountDownTimer(endTime * 1000 /*finishTime**/, 1000 /*interval**/) {
            @Override
            public void onTick(long millisUntilFinished) {
                mBinding.circleCountDownView.setProgress(progress, endTime);
                progress = progress + 1;
            }

            @Override
            public void onFinish() {
                mBinding.circleCountDownView.setProgress(progress, endTime);
                countDownTimer.cancel();
                showSingleBtnAlert(mActivity, "Time Out", "Your exam time has been finished.",
                        getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                getFinalScore();
                            }
                        });
            }
        };
        countDownTimer.start(); // start timer
    }

    void stopCountDown() {
        mBinding.circleCountDownView.setProgress(endTime, endTime);
        if(countDownTimer!=null)
            countDownTimer.cancel();
    }

    void setData() {
        LOGD("POSITION :: SET_DATA", pos + "");

        if (mArrayList != null && mArrayList.size() > 0) {
            questionBean = mArrayList.get(pos);

            mBinding.txtCount.setText(pos + 1 + "/" + mArrayList.size());

            /*if (pos > 0) {
                mBinding.txtPrev.setVisibility(View.VISIBLE);
                if (pos == mArrayList.size() - 1) {
                    mBinding.txtNext.setVisibility(View.INVISIBLE);
                    masterBinding.toolbar.txtRight.setText(getString(R.string.done));
                } else {
                    mBinding.txtNext.setVisibility(View.VISIBLE);
                    masterBinding.toolbar.txtRight.setText(getString(R.string.quit));
                }
            }*/
            if(pos == 0)
            {
                mBinding.txtPrev.setVisibility(View.INVISIBLE);
                if(mArrayList.size()==1)
                {
                    mBinding.txtNext.setVisibility(View.INVISIBLE);
                    masterBinding.toolbar.txtRight.setText(getString(R.string.done));
                }
                else
                {
                    mBinding.txtNext.setVisibility(View.VISIBLE);
                    masterBinding.toolbar.txtRight.setText(getString(R.string.quit));
                }
            }
            else if(pos == mArrayList.size() - 1)
            {
                mBinding.txtNext.setVisibility(View.INVISIBLE);
                masterBinding.toolbar.txtRight.setText(getString(R.string.done));
                if(mArrayList.size()==1)
                {
                    mBinding.txtPrev.setVisibility(View.INVISIBLE);
                }
                else
                {
                    mBinding.txtPrev.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                mBinding.txtNext.setVisibility(View.VISIBLE);
                mBinding.txtPrev.setVisibility(View.VISIBLE);
                masterBinding.toolbar.txtRight.setText(getString(R.string.quit));
            }

            String type = questionBean.getQuestionType();
            switch (type) {
                case MCQ:
                    mBinding.llResetDraggableOptions.setVisibility(View.GONE);
                    mBinding.rvWords.setVisibility(View.GONE);
                    tagLayout.setVisibility(View.GONE);
                    mBinding.llTrueFalse.setVisibility(View.GONE);
                    mBinding.llMCQ.setVisibility(View.VISIBLE);

//                    btnReset.setVisibility(View.GONE);
                    mBinding.txtQuestion.setVisibility(View.VISIBLE);
                    mBinding.txtQuestion.setText(questionBean.getQuestionName());

                    mBinding.txtOpt1.setText(questionBean.getOption1());
                    mBinding.txtOpt2.setText(questionBean.getOption2());
                    mBinding.txtOpt3.setText(questionBean.getOption3());
                    mBinding.txtOpt4.setText(questionBean.getOption4());

                    if (is_view_only) {
                        setMCQAns(questionBean.getMcqAnswer());
                        if (questionBean.getAns_type() == WRONG) {
                            if (questionBean.getUser_ans().equals("1"))
                                mBinding.txtOpt1.setBackgroundResource(R.drawable.bg_opt_wrong);
                            else if (questionBean.getUser_ans().equals("2"))
                                mBinding.txtOpt2.setBackgroundResource(R.drawable.bg_opt_wrong);
                            else if (questionBean.getUser_ans().equals("3"))
                                mBinding.txtOpt3.setBackgroundResource(R.drawable.bg_opt_wrong);
                            else if (questionBean.getUser_ans().equals("4"))
                                mBinding.txtOpt4.setBackgroundResource(R.drawable.bg_opt_wrong);
                        }
                    } else {
                        if (questionBean.getUser_ans() != null && !questionBean.getUser_ans().equals("")) {
                            if (questionBean.getUser_ans().equals("1"))
                                setCheckedMCQ(mBinding.txtOpt1, mBinding.txtOpt2, mBinding.txtOpt3, mBinding.txtOpt4);
                            else if (questionBean.getUser_ans().equals("2"))
                                setCheckedMCQ(mBinding.txtOpt2, mBinding.txtOpt1, mBinding.txtOpt3, mBinding.txtOpt4);
                            else if (questionBean.getUser_ans().equals("3"))
                                setCheckedMCQ(mBinding.txtOpt3, mBinding.txtOpt2, mBinding.txtOpt1, mBinding.txtOpt4);
                            else if (questionBean.getUser_ans().equals("4"))
                                setCheckedMCQ(mBinding.txtOpt4, mBinding.txtOpt2, mBinding.txtOpt3, mBinding.txtOpt1);
                        }
                    }


                    break;
                case TRUE_FALSE:
                    mBinding.llResetDraggableOptions.setVisibility(View.GONE);
                    tagLayout.setVisibility(View.GONE);
                    mBinding.rvWords.setVisibility(View.GONE);
                    mBinding.llTrueFalse.setVisibility(View.VISIBLE);
                    mBinding.llMCQ.setVisibility(View.GONE);

//                    btnReset.setVisibility(View.GONE);
                    mBinding.txtQuestion.setVisibility(View.VISIBLE);

                    mBinding.txtQuestion.setText(questionBean.getQuestionName());

                    if (is_view_only) {
                        setTrueFalseAns(questionBean.getAnswer().toLowerCase().trim());

                        if (questionBean.getAns_type() == WRONG) {
                            if (questionBean.getUser_ans().toLowerCase().equals("true"))
                                mBinding.rbTrue.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_true_wrong, 0, 0);
                            else if (questionBean.getUser_ans().toLowerCase().equals("false"))
                                mBinding.rbFalse.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_false_wrong, 0, 0);
                        }
                    } else {
                        if (questionBean.getUser_ans() != null && !questionBean.getUser_ans().equals("")) {
                            if (questionBean.getUser_ans().equals("true"))
                                setCheckedTrueFalse(mBinding.rbTrue, mBinding.rbFalse);
                            else if (questionBean.getUser_ans().equals("false"))
                                setCheckedTrueFalse(mBinding.rbFalse, mBinding.rbTrue);
                        }
                    }


                    break;
                case FILL_BLANK:
                    mBinding.llResetDraggableOptions.setVisibility(View.GONE);
                    mBinding.llTrueFalse.setVisibility(View.GONE);
                    mBinding.llMCQ.setVisibility(View.GONE);

//                    btnReset.setVisibility(View.VISIBLE);


                    if (is_view_only) {
                        tagLayout.setVisibility(View.GONE);
                        mBinding.rvWords.setVisibility(View.GONE);
                        mBinding.txtQuestion.setVisibility(View.VISIBLE);


                        switch (questionBean.getAns_type()) {
                            case SKIP:
                                SpannableText(mActivity, questionBean.getQuestionName(), questionBean.getFillAnswer(),
                                        "", mBinding.txtQuestion);
                                break;
                            case CORRECT:
                                SpannableText(mActivity, questionBean.getQuestionName(), questionBean.getFillAnswer(),
                                        "", mBinding.txtQuestion);
                                break;
                            case WRONG:
                                SpannableText(mActivity, questionBean.getQuestionName(), questionBean.getFillAnswer(),
                                        questionBean.getUser_ans(), mBinding.txtQuestion);
                                break;
                        }

                    } else {
                        mBinding.llResetDraggableOptions.setVisibility(View.VISIBLE);
                        tagLayout.setVisibility(View.VISIBLE);
                        mBinding.rvWords.setVisibility(View.VISIBLE);
                        mBinding.txtQuestion.setVisibility(View.GONE);
                        mBinding.txtQuestion.setText("");

                        wordsList = new ArrayList<>();
                        String[] options = null;
                        if (questionBean.getUser_ans() != null && !questionBean.getUser_ans().equals("")) {
                            if (questionBean.getRemaining_options() != null && !questionBean.getRemaining_options().equals(""))
                                options = questionBean.getRemaining_options().split(",");
                        } else
                            options = questionBean.getOption().split(",");


                        if (options != null && options.length > 0) {

                            for (int i = 0; i < options.length; i++) {
                                wordsList.add(i, options[i]);
                            }

                            if (wordsList != null && wordsList.size() > 0) {
                                mBinding.rvWords.setVisibility(View.VISIBLE);
                                mAdapter = new WordsAdapter(mActivity, wordsList);
                                mBinding.rvWords.setAdapter(mAdapter);
                            } else {
                                mBinding.rvWords.setVisibility(View.GONE);
                            }


                            if (tagLayout != null) {
                                tagLayout.removeAllViews();
                            }

                            if (questionBean.getUser_ans() != null && !questionBean.getUser_ans().equals("")) {
                                makeDraggableTag(questionBean.getUser_ans());
                            } else {
                                makeDraggableTag(questionBean.getQuestionName());
                            }
                        }
                    }

                    break;
            }
        }
    }

    void reset() {
        if (tagLayout != null) {
            tagLayout.removeAllViews();
        }

        mArrayList.get(pos).setUser_ans("");
        mArrayList.get(pos).setRemaining_options("");

        String[] options = questionBean.getOption().split(",");

        if (options != null && options.length > 0) {
            wordsList = new ArrayList<>();
            for (int i = 0; i < options.length; i++) {
                wordsList.add(i, options[i]);
            }

            if (wordsList != null && wordsList.size() > 0) {
                mBinding.rvWords.setVisibility(View.VISIBLE);
                mAdapter = new WordsAdapter(mActivity, wordsList);
                mBinding.rvWords.setAdapter(mAdapter);
            } else {
                mBinding.rvWords.setVisibility(View.GONE);
            }

            makeDraggableTag(questionBean.getQuestionName());
        }
    }

    void makeDraggableTag(String mQuestion) {
        String[] strings = mQuestion.split("####");

        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < strings.length; i++) {
            Log.d("WORD", strings[i]);
            View tagView = layoutInflater.inflate(R.layout.custom_text, null, false);
            TextView textView = (TextView) tagView.findViewById(R.id.txtJust);
            textView.setText(strings[i]);
            tagLayout.addView(tagView);
            if (i < strings.length - 1) {
                tagView = layoutInflater.inflate(R.layout.custom_text, null, false);
                textView = (TextView) tagView.findViewById(R.id.txtJust);
                textView.setText(getString(R.string.blanks));
                textView.setOnDragListener(new ChoiceDragListener());
                tagLayout.addView(tagView);
            }
        }

    }

    void setFillData() {
        if (questionBean!=null && questionBean.getQuestionType().equals(FILL_BLANK)) {
            mArrayList.get(pos).setUser_ans(getSentence(tagLayout).replaceAll(getString(R.string.blanks), "####"));
            mArrayList.get(pos).setRemaining_options(getRemianingOptions());
        }
    }

    void getFinalScore() {
        int obtained_score = 0, total_score = 0, skipped_ans = 0, correct_ans = 0, wrong_ans = 0;
        if (mArrayList != null && mArrayList.size() > 0) {
            for (int i = 0; i < mArrayList.size(); i++) {
                QuestionBean.Result result = mArrayList.get(i);
                total_score = total_score + Integer.parseInt(mArrayList.get(i).getQuestionMark());
                if (result.getUser_ans() != null && !result.getUser_ans().equals("")) {
                    switch (result.getQuestionType()) {
                        case MCQ:
                            if (result.getMcqAnswer().trim().equals(result.getUser_ans().trim())) {
                                obtained_score = obtained_score + Integer.parseInt(questionBean.getQuestionMark());
                                correct_ans++;
                                mArrayList.get(i).setAns_type(CORRECT);
                            } else {
                                wrong_ans++;
                                mArrayList.get(i).setAns_type(WRONG);
                            }
                            break;
                        case TRUE_FALSE:
                            if (result.getAnswer().toLowerCase().equals(result.getUser_ans().toLowerCase())) {
                                obtained_score = obtained_score + Integer.parseInt(questionBean.getQuestionMark());
                                correct_ans++;
                                mArrayList.get(i).setAns_type(CORRECT);
                            } else {
                                wrong_ans++;
                                mArrayList.get(i).setAns_type(WRONG);
                            }
                            break;
                        case FILL_BLANK:
                            if (result.getQuestionName().equals(result.getUser_ans())) {
                                skipped_ans++;
                                mArrayList.get(i).setAns_type(SKIP);
                            } else {
                                if (result.getFillAnswer().equals(result.getUser_ans())) {
                                    obtained_score = obtained_score + Integer.parseInt(questionBean.getQuestionMark());
                                    correct_ans++;
                                    mArrayList.get(i).setAns_type(CORRECT);
                                } else {
                                    wrong_ans++;
                                    mArrayList.get(i).setAns_type(WRONG);
                                }
                            }
                            break;
                    }
                } else {
                    skipped_ans++;
                    mArrayList.get(i).setAns_type(SKIP);
                }
            }

            /*if (AppConstants.mQuestionList != null) {
                AppConstants.mQuestionList = null;
            }*/

            AppConstants.mQuestionList.clear();
            AppConstants.mQuestionList.addAll(mArrayList);

        }
        startActivity(new Intent(mActivity, ExamResult.class)
                .putExtra(TOTAL_SCORE, total_score)
                .putExtra(OBTAINED_SCORE, obtained_score)
                .putExtra(TOTAL_ANS, mArrayList==null?0:mArrayList.size())
                .putExtra(SKIPPED_ANS, skipped_ans)
                .putExtra(CORRECT_ANS, correct_ans)
                .putExtra(WRONG_ANS, wrong_ans)
                .putExtra(AppConstants.INTENT_TEST_ID, testID)
                .putExtra(AppConstants.INTENT_TEST_NAME, testName)
                .putExtra(AppConstants.INTENT_SUBCAT_ID, subCatId));
        finish();

    }

    String getRemianingOptions() {
        String mOptions = "";
        if (wordsList != null && wordsList.size() > 0) {
            for (int i = 0; i < wordsList.size(); i++) {

                View view = layoutManager.findViewByPosition(i);
                TextView txt = (TextView) view.findViewById(R.id.txtParent);
                LOGD("VISIBILITY " + i, String.valueOf(txt.getVisibility()));
                if (txt.getVisibility() == View.VISIBLE) {
                    if (mOptions.equals(""))
                        mOptions = wordsList.get(i);
                    else
                        mOptions = mOptions + "," + wordsList.get(i);
                }
            }
        }
        return mOptions;
    }

    void setCheckedTrueFalse(TextView active, TextView deactive) {
        active.setSelected(true);
        deactive.setSelected(false);
    }

    void setCheckedMCQ(TextView active, TextView deactive1, TextView deactive2, TextView deactive3) {
        active.setSelected(true);
        deactive1.setSelected(false);
        deactive2.setSelected(false);
        deactive3.setSelected(false);
    }

    void setMCQAns(String mAns) {
        mBinding.txtOpt1.setBackgroundResource(R.drawable.bg_opt_deselected);
        mBinding.txtOpt2.setBackgroundResource(R.drawable.bg_opt_deselected);
        mBinding.txtOpt3.setBackgroundResource(R.drawable.bg_opt_deselected);
        mBinding.txtOpt4.setBackgroundResource(R.drawable.bg_opt_deselected);

        switch (mAns) {
            case "1":
                mBinding.txtOpt1.setBackgroundResource(R.drawable.bg_opt_correct);
                break;
            case "2":
                mBinding.txtOpt2.setBackgroundResource(R.drawable.bg_opt_correct);
                break;
            case "3":
                mBinding.txtOpt3.setBackgroundResource(R.drawable.bg_opt_correct);
                break;
            case "4":
                mBinding.txtOpt4.setBackgroundResource(R.drawable.bg_opt_correct);
                break;
        }
    }

    void setTrueFalseAns(String mAns) {
        mBinding.rbTrue.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_false, 0, 0);
        mBinding.rbFalse.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_false, 0, 0);

        switch (mAns) {
            case "true":
                mBinding.rbTrue.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_true_correct, 0, 0);
                break;
            case "false":
                mBinding.rbFalse.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_false_correct, 0, 0);
                break;
        }
    }

    String getSentence(TagLayout viewGroup) {
        String mSentence = "";
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {
                TextView text = (TextView) view;
                mSentence = mSentence + text.getText().toString();
            }
        }
        LOGD("FINAL_SENTENCE", ":: " + mSentence);
        return mSentence;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    @Override
    public void onBackPressed() {
        if (is_view_only) {
            AppConstants.mQuestionList.clear();
            super.onBackPressed();
        } else {
            if(mArrayList.size()==0)
            {
                NavigationUtils.finishCurrentActivity(mActivity);
            }
            else
            {
                showDoubleBtnAlert(mActivity, "Quit Exam", "Are You sure you want to quit exam?",
                        getString(android.R.string.yes), getString(android.R.string.no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        stopCountDown();
                                        getFinalScore();
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        dialogInterface.dismiss();
                                        break;
                                }
                            }
                        });
            }
        }
    }

    /**
     * DragListener will handle dragged views being dropped on the drop area
     * - only the drop action will have processing added to it as we are not
     * - amending the default behavior for other parts of the drag process
     */
    @SuppressLint("NewApi")
    public class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            View draggedView = (View) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    draggedView.setVisibility(View.INVISIBLE);
                    LOGD("ACTION_DRAG_STARTED", "ACTION_DRAG_STARTED");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    LOGD("ACTION_DROP", "ACTION_DROP");

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;

                    //stop displaying the view where it was before it was dragged
                    view.setVisibility(View.GONE);
                    //update the text in the target view to reflect the data being dropped
//                    dropTarget.setText(dropTarget.getText().toString() + dropped.getText().toString());
                    dropTarget.setText(dropped.getText().toString());
                    //make it bold to highlight the fact that an item has been dropped
//                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    //if an item has already been dropped here, there will be a tag
                    Object tag = dropTarget.getTag();
                    //if there is already an item here, set it back visible in its original place
                    if (tag != null) {
                        //the tag is the view id already dropped here
                        int existingID = (Integer) tag;
                        //set the original view visible again
                        findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    //set the tag in the target view being dropped on - to the ID of the view being dropped
                    dropTarget.setTag(dropped.getId());
                    //remove setOnDragListener by setting OnDragListener to null, so that no further drag & dropping on this TextView can be done
                    dropTarget.setOnDragListener(null);

                  /*  if (getSentence(tagLayout).contains(getString(R.string.blanks)))
                        btnNext.setEnabled(false);
                    else
                        btnNext.setEnabled(true);*/

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    LOGD("ACTION_DRAG_ENDED::", event.getResult() + "");
                    if (!event.getResult())
                        draggedView.setVisibility(View.VISIBLE);
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
