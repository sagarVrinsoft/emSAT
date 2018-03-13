package com.vrinsoft.emsat.apis.model.SubCategory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BinSubCategory {

@SerializedName("code")
@Expose
private int code;
@SerializedName("message")
@Expose
private String message;
@SerializedName("result")
@Expose
private List<Result> result = null;

public int getCode() {
return code;
}

public void setCode(int code) {
this.code = code;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<Result> getResult() {
return result;
}

public void setResult(List<Result> result) {
this.result = result;
}

}
