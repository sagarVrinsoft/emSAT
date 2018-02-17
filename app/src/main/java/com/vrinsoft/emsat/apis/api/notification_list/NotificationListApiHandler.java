package com.vrinsoft.emsat.apis.api.notification_list;


import com.vrinsoft.emsat.apis.model.notification_list.BeanNotificationList;
import com.vrinsoft.emsat.apis.model.notification_list.read_notification.BeanReadNotification;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationListApiHandler {

    public void fetchNotificationData(String userId, String token, int pageNo, int userType, final OnNotificationList onNotificationList) {

        Call<ArrayList<BeanNotificationList>> listCall =
                ApiClient.getApiInterface().fetchNotificationList(userId, token, pageNo, userType);

        listCall.enqueue(new Callback<ArrayList<BeanNotificationList>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanNotificationList>> call, Response<ArrayList<BeanNotificationList>> response) {
                ArrayList<BeanNotificationList> beanNotificationLists = response.body();
                onNotificationList.getResponse(true, beanNotificationLists, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanNotificationList>> call, Throwable t) {
                onNotificationList.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
