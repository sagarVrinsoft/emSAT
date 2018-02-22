package com.vrinsoft.emsat.apis.api.subcategory_list;


import com.vrinsoft.emsat.activity.subcategory.model.BeanNotificationList;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryListApiHandler {

    public void fetchSubCatData(String userId, String token, int pageNo, int userType, final OnSubCatList onSubCatList) {

        Call<ArrayList<BeanNotificationList>> listCall =
                ApiClient.getApiInterface().fetchNotificationList(userId, token, pageNo, userType);

        listCall.enqueue(new Callback<ArrayList<BeanNotificationList>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanNotificationList>> call, Response<ArrayList<BeanNotificationList>> response) {
                ArrayList<BeanNotificationList> beanNotificationLists = response.body();
                onSubCatList.getResponse(true, beanNotificationLists, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanNotificationList>> call, Throwable t) {
                onSubCatList.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
