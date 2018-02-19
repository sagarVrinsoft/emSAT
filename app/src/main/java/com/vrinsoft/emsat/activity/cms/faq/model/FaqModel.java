package com.vrinsoft.emsat.activity.cms.faq.model;

import java.util.ArrayList;

/**
 * Created by andorid on 13/4/17.
 */

public class FaqModel {

    private String category_id = "",category_name = "",
            image_url = "",question = "";

    public ArrayList<SubCatagoryModel> mSubCatagoryList = null;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
