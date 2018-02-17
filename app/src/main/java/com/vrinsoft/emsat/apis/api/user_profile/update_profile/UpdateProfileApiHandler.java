package com.vrinsoft.emsat.apis.api.user_profile.update_profile;


import com.vrinsoft.emsat.apis.model.user_profile.update_profile.BeanUpdateProfile;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileApiHandler {

    public void updateProfile(String userId, String f_name, String l_name, String profession, String dob, String metroArea,
                              String mobileNo, String userImg, int gender, int prefersRide, int allowRider, String
                                      emergencyMobileNo, String vehicleModel, String vehicleYear, String vehiclePlateNo,
                              String vehicleState, String token, final OnProfileUpdate onProfileUpdate) {

        Call<ArrayList<BeanUpdateProfile>> listCall = ApiClient.getApiInterface().updateProfile(userId, f_name, l_name, profession, dob, metroArea, mobileNo, userImg, gender, prefersRide, allowRider, emergencyMobileNo, vehicleModel, vehicleYear, vehiclePlateNo, vehicleState, token);

        listCall.enqueue(new Callback<ArrayList<BeanUpdateProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanUpdateProfile>> call, Response<ArrayList<BeanUpdateProfile>> response) {
                ArrayList<BeanUpdateProfile> beanUpdateProfile = response.body();
                onProfileUpdate.getResponse(true, beanUpdateProfile, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanUpdateProfile>> call, Throwable t) {
                onProfileUpdate.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
