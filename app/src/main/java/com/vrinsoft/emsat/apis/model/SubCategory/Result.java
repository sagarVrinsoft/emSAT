package com.vrinsoft.emsat.apis.model.SubCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("sub_category_id")
@Expose
private String subCategoryId;
@SerializedName("sub_category_name")
@Expose
private String subCategoryName;
@SerializedName("category_id")
@Expose
private String categoryId;
@SerializedName("category_name")
@Expose
private String categoryName;
@SerializedName("is_apply")
@Expose
private String isApply;

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

public String getIsApply() {
return isApply;
}

public void setIsApply(String isApply) {
this.isApply = isApply;
}

}