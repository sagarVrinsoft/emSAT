package com.vrinsoft.emsat.apis.api.category.subcategory_list;


import com.vrinsoft.emsat.activity.home.model.category.BeanNotificationList;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListApiHandler {

    public void fetchCatData(String userId, String token, int pageNo, int userType, final OnCatList onCatList) {

        Call<ArrayList<BeanNotificationList>> listCall =
                ApiClient.getApiInterface().fetchCategoryList(userId, token, pageNo, userType);

        listCall.enqueue(new Callback<ArrayList<BeanNotificationList>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanNotificationList>> call,
                                   Response<ArrayList<BeanNotificationList>> response) {
                ArrayList<BeanNotificationList> beanNotificationLists = response.body();
                onCatList.getResponse(true, beanNotificationLists, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanNotificationList>> call, Throwable t) {
                onCatList.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
