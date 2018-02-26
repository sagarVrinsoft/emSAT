package com.vrinsoft.emsat.apis.model.exam_question;

/**
 * Created by dorji on 2/1/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class QuestionBean {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ArrayList<Result> result = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }


    public class Result implements Serializable {

        @SerializedName("question_type")
        @Expose
        private String questionType;
        @SerializedName("question_type_name")
        @Expose
        private String questionTypeName;
        @SerializedName("question_name")
        @Expose
        private String questionName;
        @SerializedName("question_mark")
        @Expose
        private String questionMark;
        @SerializedName("question_hint")
        @Expose
        private String questionHint;
        @SerializedName("option_1")
        @Expose
        private String option1;
        @SerializedName("option_2")
        @Expose
        private String option2;
        @SerializedName("option_3")
        @Expose
        private String option3;
        @SerializedName("option_4")
        @Expose
        private String option4;
        @SerializedName("mcq_answer")
        @Expose
        private String mcqAnswer;
        @SerializedName("option")
        @Expose
        private String option;
        @SerializedName("fill_answer")
        @Expose
        private String fillAnswer;
        @SerializedName("true_false")
        @Expose
        private String trueFalse;
        @SerializedName("answer")
        @Expose
        private String answer;
        private String user_ans = "", remaining_options = "";
        private int ans_type = 0;

        public int getAns_type() {
            return ans_type;
        }

        public void setAns_type(int ans_type) {
            this.ans_type = ans_type;
        }

        public String getRemaining_options() {
            return remaining_options;
        }

        public void setRemaining_options(String remaining_options) {
            this.remaining_options = remaining_options;
        }

        public String getUser_ans() {
            return user_ans;
        }

        public void setUser_ans(String user_ans) {
            this.user_ans = user_ans;
        }

        public String getQuestionType() {
            return questionType;
        }

        public void setQuestionType(String questionType) {
            this.questionType = questionType;
        }

        public String getQuestionTypeName() {
            return questionTypeName;
        }

        public void setQuestionTypeName(String questionTypeName) {
            this.questionTypeName = questionTypeName;
        }

        public String getQuestionName() {
            return questionName;
        }

        public void setQuestionName(String questionName) {
            this.questionName = questionName;
        }

        public String getQuestionMark() {
            return questionMark;
        }

        public void setQuestionMark(String questionMark) {
            this.questionMark = questionMark;
        }

        public String getQuestionHint() {
            return questionHint;
        }

        public void setQuestionHint(String questionHint) {
            this.questionHint = questionHint;
        }

        public String getOption1() {
            return option1;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }

        public String getMcqAnswer() {
            return mcqAnswer;
        }

        public void setMcqAnswer(String mcqAnswer) {
            this.mcqAnswer = mcqAnswer;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getFillAnswer() {
            return fillAnswer;
        }

        public void setFillAnswer(String fillAnswer) {
            this.fillAnswer = fillAnswer;
        }

        public String getTrueFalse() {
            return trueFalse;
        }

        public void setTrueFalse(String trueFalse) {
            this.trueFalse = trueFalse;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

    }
}
