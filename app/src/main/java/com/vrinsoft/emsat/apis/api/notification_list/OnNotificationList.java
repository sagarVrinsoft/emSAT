package com.vrinsoft.emsat.apis.api.notification_list;


import com.vrinsoft.emsat.apis.model.notification_list.BeanNotificationList;

import java.util.ArrayList;

/**
 * Created by dorji on 29/7/17.
 */

public interface OnNotificationList {
    public void getResponse(boolean isSuccess, ArrayList<BeanNotificationList> beanNotificationList, String errorMsgSystem);
}
