package com.vrinsoft.emsat.apis.test_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {


    @SerializedName("is_lock_unlock")
    @Expose
    private String is_lock_unlock;
    @SerializedName("test_id")
    @Expose
    private String testId;
    @SerializedName("test_name")
    @Expose
    private String testName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("test_type")
    @Expose
    private String testType;
    @SerializedName("is_purchase")
    @Expose
    private String isPurchase;
    @SerializedName("is_apply")
    @Expose
    private String isApply;
    @SerializedName("total_score")
    @Expose
    private String totalScore;
    @SerializedName("obtain_score")
    @Expose
    private String obtainScore;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getIsPurchase() {
        return isPurchase;
    }

    public void setIsPurchase(String isPurchase) {
        this.isPurchase = isPurchase;
    }

    public String getIsApply() {
        return isApply;
    }

    public void setIsApply(String isApply) {
        this.isApply = isApply;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getObtainScore() {
        return obtainScore;
    }

    public void setObtainScore(String obtainScore) {
        this.obtainScore = obtainScore;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getIs_lock_unlock() {
        return is_lock_unlock;
    }

    public void setIs_lock_unlock(String is_lock_unlock) {
        this.is_lock_unlock = is_lock_unlock;
    }
}