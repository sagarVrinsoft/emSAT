package com.vrinsoft.emsat.apis.model.notification_list;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BeanNotificationList {

@SerializedName("code")
@Expose
private Integer code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("total_count")
@Expose
private Integer totalCount;
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

public Integer getTotalCount() {
return totalCount;
}

public void setTotalCount(Integer totalCount) {
this.totalCount = totalCount;
}

public ArrayList<Result> getResult() {
return result;
}

public void setResult(ArrayList<Result> result) {
this.result = result;
}

}
