package com.vrinsoft.emsat.apis.model.submodules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("test_id")
@Expose
private String testId;
@SerializedName("test_name")
@Expose
private String testName;
@SerializedName("module_name")
@Expose
private String moduleName;
@SerializedName("is_apply")
@Expose
private String isApply;

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

public String getModuleName() {
return moduleName;
}

public void setModuleName(String moduleName) {
this.moduleName = moduleName;
}

public String getIsApply() {
return isApply;
}

public void setIsApply(String isApply) {
this.isApply = isApply;
}

}