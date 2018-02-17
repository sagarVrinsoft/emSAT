
package com.vrinsoft.emsat.apis.api.register;


import com.vrinsoft.emsat.apis.model.register.BeanRegister;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterApiHandler {

    public void register(String first_name, String last_name, String mobile_no, String country_code,
                         String password, int gender, String date_of_birth, int user_type, String metro_area,
                         String ssn_number, String email, String vehicleModel, String vehicleYear, String vehiclePlateNo, String vehicleState, final OnRegister onRegister) {

        Call<ArrayList<BeanRegister>> listCall = ApiClient.getApiInterface().register(first_name, last_name, mobile_no,
                country_code, password, gender, date_of_birth, user_type, metro_area, ssn_number, email,vehicleModel,vehicleYear,vehiclePlateNo,vehicleState);

        listCall.enqueue(new Callback<ArrayList<BeanRegister>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanRegister>> call, Response<ArrayList<BeanRegister>> response) {
                ArrayList<BeanRegister> beanRegister = response.body();
                onRegister.getResponse(true, beanRegister, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanRegister>> call, Throwable t) {
                onRegister.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
