package com.vrinsoft.emsat.apis.api.category.subcategory_list;




import com.vrinsoft.emsat.activity.home.model.category.BeanNotificationList;

import java.util.ArrayList;

/**
 * Created by dorji on 29/7/17.
 */

public interface OnCatList {
    public void getResponse(boolean isSuccess, ArrayList<BeanNotificationList> beanNotificationList, String errorMsgSystem);
}
