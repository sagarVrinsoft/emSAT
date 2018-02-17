package com.vrinsoft.emsat.apis.api.notification_list;


import com.vrinsoft.emsat.apis.model.notification_list.read_notification.BeanReadNotification;

import java.util.ArrayList;

public interface OnNotificationRead {
    public void getResponse(boolean isSuccess, ArrayList<BeanReadNotification> beanReadNotification, String errorMsgSystem);
}
