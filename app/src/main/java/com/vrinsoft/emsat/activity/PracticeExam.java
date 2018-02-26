package com.vrinsoft.emsat.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vrinsoft.emsat.Adapter.WordsAdapter;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.apis.handler_interface.ApiHandler;
import com.vrinsoft.emsat.apis.handler_interface.OnResponse;
import com.vrinsoft.emsat.apis.model.exam_question.QuestionBean;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.databinding.ActivityPracticeExamBinding;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.NavigationUtils;
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

import static com.vrinsoft.emsat.apis.rest.NetworkConstants.KEY_PAGE_NO;
import static com.vrinsoft.emsat.apis.rest.NetworkConstants.KEY_TOKEN;
import static com.vrinsoft.emsat.apis.rest.NetworkConstants.KEY_USER_ID;
import static com.vrinsoft.emsat.apis.rest.NetworkConstants.QUESTION.KEY_TEST_ID;
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
import static com.vrinsoft.emsat.utils.ViewUtils.showDoubleBtnAlert;
import static com.vrinsoft.emsat.utils.ViewUtils.showSingleBtnAlert;
import static com.vrinsoft.emsat.utils.ViewUtils.showToast;

public class PracticeExam extends AppCompatActivity implements View.OnClickListener {
    ApiHandler apiHandler;
    Activity mActivity;
    ArrayList<QuestionBean.Result> mArrayList;
    ActivityPracticeExamBinding mBinding;
    TagLayout tagLayout;
    ArrayList<String> wordsList;
    WordsAdapter mAdapter;
    String QUESTION = "";
    private int pos = 0;
    QuestionBean.Result questionBean;

    int progress;
    int endTime;
    CountDownTimer countDownTimer;
    GridLayoutManager layoutManager;

    Bundle mBundle;
    String mFrom = "";
    boolean is_view_only = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_practice_exam);

        mActivity = PracticeExam.this;
        apiHandler = new ApiHandler();
        tagLayout = (TagLayout) findViewById(R.id.mFrameLayout);

        mBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.txtTitle.setText(getString(R.string.practice_exam));

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mFrom = mBundle.getString("FROM");
            if (mFrom.equals("CHECK_ANS")) {
                is_view_only = true;
                mBinding.toolbar.txtRight.setVisibility(View.GONE);
                mBinding.headerBar.setVisibility(View.INVISIBLE);
            } else {
                is_view_only = false;
                mBinding.toolbar.txtRight.setVisibility(View.VISIBLE);
                mBinding.headerBar.setVisibility(View.VISIBLE);
            }
        }

        layoutManager = new GridLayoutManager(mActivity, 3);
        mBinding.rvWords.setLayoutManager(layoutManager);
        getQuestionList();

        if (!is_view_only) {
            mBinding.rbTrue.setOnClickListener(this);
            mBinding.rbFalse.setOnClickListener(this);

            mBinding.txtOpt1.setOnClickListener(this);
            mBinding.txtOpt2.setOnClickListener(this);
            mBinding.txtOpt3.setOnClickListener(this);
            mBinding.txtOpt4.setOnClickListener(this);
        }

        mBinding.txtNext.setOnClickListener(this);
        mBinding.txtPrev.setOnClickListener(this);
        mBinding.toolbar.txtRight.setOnClickListener(this);
    }

    private void getQuestionList() {
        ViewUtils.showDialog(mActivity, false);

        HashMap<String, String> params = new HashMap<>();
        params.put(KEY_USER_ID, "1");
        params.put(KEY_TOKEN, "dHDOsK");
        params.put(KEY_TEST_ID, "1");
        params.put(KEY_PAGE_NO, String.valueOf(1));

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

//                                mTotalCount = mainArray.get(0).getTotalRecords();
                                mArrayList = mainArray.get(0).getResult();
                                setData();

                                if (!is_view_only)
                                    startCountDown();

                            } else if (mainArray.get(0).getCode() == 0) {
                                showToast(mActivity, "No data found", null);
                            } else if (mainArray.get(0).getCode() == -2) {
                                showToast(mActivity, "something_went_wrong_please_login_again", null);
                            } else if (mainArray.get(0).getCode() == -3) {//Account inactive
                                showToast(mActivity, "your_account_inactive", null);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (errorObject != null) {

                    }
                } else if (errorMsgSystem.equals(ApiErrorUtils.SOMETHING_WENT_WRONG) || errorMsgSystem.equals(ApiErrorUtils.ERROR_NETWORK)) {
                    showToast(mActivity, errorMsgSystem, null);
                }

            }
        });
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
           /* case R.id.btnReset:
                reset();
                break;*/
            case R.id.txtNext:
                if (!is_view_only)
                    setFillData();

                if (pos < mArrayList.size() - 1) {
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
                if (pos == mArrayList.size() - 1) {
                    stopCountDown();
                    getFinalScore();
                } else {
                    showDoubleBtnAlert(mActivity, "Quit Exam", "Are You sure you want to quit exam?",
                            getString(android.R.string.yes), getString(android.R.string.no),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    stopCountDown();
                                    getFinalScore();
                                }
                            });
                }
                break;
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
                                getFinalScore();
                            }
                        });
            }
        };
        countDownTimer.start(); // start timer
    }


    void stopCountDown() {
        mBinding.circleCountDownView.setProgress(endTime, endTime);
        countDownTimer.cancel();
    }

    void setData() {
        LOGD("POSITION :: SET_DATA", pos + "");

        if (mArrayList != null && mArrayList.size() > 0) {
            questionBean = mArrayList.get(pos);

            mBinding.txtCount.setText(pos + 1 + "/" + mArrayList.size());

            if (pos > 0) {
                mBinding.txtPrev.setVisibility(View.VISIBLE);
                if (pos == mArrayList.size() - 1) {
                    mBinding.txtNext.setVisibility(View.INVISIBLE);
                    mBinding.toolbar.txtRight.setText(getString(R.string.done));
                } else {
                    mBinding.txtNext.setVisibility(View.VISIBLE);
                    mBinding.toolbar.txtRight.setText(getString(R.string.quit));
                }
            } else {
                mBinding.txtPrev.setVisibility(View.INVISIBLE);
            }

            String type = questionBean.getQuestionType();
            switch (type) {
                case MCQ:
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

                    break;
                case TRUE_FALSE:
                    tagLayout.setVisibility(View.GONE);
                    mBinding.rvWords.setVisibility(View.GONE);
                    mBinding.llTrueFalse.setVisibility(View.VISIBLE);
                    mBinding.llMCQ.setVisibility(View.GONE);

//                    btnReset.setVisibility(View.GONE);
                    mBinding.txtQuestion.setVisibility(View.VISIBLE);

                    mBinding.txtQuestion.setText(questionBean.getQuestionName());

                    if (questionBean.getUser_ans() != null && !questionBean.getUser_ans().equals("")) {
                        if (questionBean.getUser_ans().equals("true"))
                            setCheckedTrueFalse(mBinding.rbTrue, mBinding.rbFalse);
                        else if (questionBean.getUser_ans().equals("false"))
                            setCheckedTrueFalse(mBinding.rbFalse, mBinding.rbTrue);
                    }

                    break;
                case FILL_BLANK:
                    tagLayout.setVisibility(View.VISIBLE);
                    mBinding.rvWords.setVisibility(View.VISIBLE);
                    mBinding.llTrueFalse.setVisibility(View.GONE);
                    mBinding.llMCQ.setVisibility(View.GONE);

//                    btnReset.setVisibility(View.VISIBLE);
                    mBinding.txtQuestion.setVisibility(View.GONE);
                    mBinding.txtQuestion.setText("");

//                     mBinding.txtNext.setEnabled(false);

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
                    break;
            }
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
        if (questionBean.getQuestionType().equals(FILL_BLANK)) {
            mArrayList.get(pos).setUser_ans(getSentence(tagLayout).replaceAll(getString(R.string.blanks), "####"));
            mArrayList.get(pos).setRemaining_options(getRemianingOptions());
        }
    }

    void getFinalScore() {

        if (mArrayList != null && mArrayList.size() > 0) {

            int obtained_score = 0, total_score = 0, skipped_ans = 0, correct_ans = 0, wrong_ans = 0;
            for (int i = 0; i < mArrayList.size(); i++) {
                QuestionBean.Result result = mArrayList.get(i);
                total_score = total_score + Integer.parseInt(mArrayList.get(i).getQuestionMark());
                if (result.getUser_ans() != null && !result.getUser_ans().equals("")) {
                    switch (result.getQuestionType()) {
                        case MCQ:
                            if (result.getMcqAnswer().trim().equals(result.getUser_ans().trim())) {
                                obtained_score = obtained_score + Integer.parseInt(questionBean.getQuestionMark());
                                correct_ans++;
                            } else
                                wrong_ans++;
                            break;
                        case TRUE_FALSE:
                            if (result.getAnswer().toLowerCase().equals(result.getUser_ans().toLowerCase())) {
                                obtained_score = obtained_score + Integer.parseInt(questionBean.getQuestionMark());
                                correct_ans++;
                            } else
                                wrong_ans++;
                            break;
                        case FILL_BLANK:
                            if (result.getFillAnswer().equals(result.getUser_ans())) {
                                obtained_score = obtained_score + Integer.parseInt(questionBean.getQuestionMark());
                                correct_ans++;
                            } else
                                wrong_ans++;
                            break;
                    }
                } else {
                    skipped_ans++;
                }
            }

            if (AppConstants.mQuestionList != null) {
                AppConstants.mQuestionList = null;
            }

            AppConstants.mQuestionList = mArrayList;

            startActivity(new Intent(mActivity, ExamResult.class)
                    .putExtra(TOTAL_SCORE, total_score)
                    .putExtra(OBTAINED_SCORE, obtained_score)
                    .putExtra(TOTAL_ANS, mArrayList.size())
                    .putExtra(SKIPPED_ANS, skipped_ans)
                    .putExtra(CORRECT_ANS, correct_ans)
                    .putExtra(WRONG_ANS, wrong_ans));
        }

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

    void reset() {
        if (tagLayout != null) {
            tagLayout.removeAllViews();
        }
        makeDraggableTag(questionBean.getQuestionName());
        mBinding.txtNext.setEnabled(false);

        if (wordsList != null && wordsList.size() > 0) {
            mAdapter = new WordsAdapter(mActivity, wordsList);
            mBinding.rvWords.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}
