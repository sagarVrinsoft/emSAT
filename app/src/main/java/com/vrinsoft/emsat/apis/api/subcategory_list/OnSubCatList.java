package com.vrinsoft.emsat.apis.api.subcategory_list;



import com.vrinsoft.emsat.activity.subcategory.model.BeanNotificationList;

import java.util.ArrayList;

/**
 * Created by dorji on 29/7/17.
 */

public interface OnSubCatList {
    public void getResponse(boolean isSuccess, ArrayList<BeanNotificationList> beanNotificationList, String errorMsgSystem);
}
