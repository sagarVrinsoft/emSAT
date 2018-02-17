package com.vrinsoft.emsat.apis.model.cms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BeanCMS {
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

    public class Result {
        @SerializedName("cms_id")
        @Expose
        private String cms_id;
        @SerializedName("page_name")
        @Expose
        private String page_name;
        @SerializedName("description")
        @Expose
        private String description;

        public String getCms_id() {
            return cms_id;
        }

        public void setCms_id(String cms_id) {
            this.cms_id = cms_id;
        }

        public String getPage_name() {
            return page_name;
        }

        public void setPage_name(String page_name) {
            this.page_name = page_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
