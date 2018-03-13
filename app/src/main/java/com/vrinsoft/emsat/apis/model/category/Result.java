package com.vrinsoft.emsat.apis.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("category_id")
@Expose
private String categoryId;
@SerializedName("category_name")
@Expose
private String categoryName;
@SerializedName("price")
@Expose
private String price;
@SerializedName("currency")
@Expose
private String currency;
@SerializedName("is_purchase")
@Expose
private String isPurchase;

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

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

public String getCurrency() {
return currency;
}

public void setCurrency(String currency) {
this.currency = currency;
}

public String getIsPurchase() {
return isPurchase;
}

public void setIsPurchase(String isPurchase) {
this.isPurchase = isPurchase;
}

}