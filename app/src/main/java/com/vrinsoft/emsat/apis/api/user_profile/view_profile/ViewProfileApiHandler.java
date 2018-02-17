package com.vrinsoft.emsat.apis.api.user_profile.view_profile;


import com.vrinsoft.emsat.apis.model.user_profile.view_profile.BeanViewProfile;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileApiHandler {

    public void viewProfile(String userId, String token, final OnViewProfile onViewProfile) {

        Call<ArrayList<BeanViewProfile>> listCall = ApiClient.getApiInterface().viewProfile(userId, token);

        listCall.enqueue(new Callback<ArrayList<BeanViewProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanViewProfile>> call, Response<ArrayList<BeanViewProfile>> response) {
                ArrayList<BeanViewProfile> beanViewProfile = response.body();
                onViewProfile.getResponse(true, beanViewProfile, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanViewProfile>> call, Throwable t) {
                onViewProfile.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
