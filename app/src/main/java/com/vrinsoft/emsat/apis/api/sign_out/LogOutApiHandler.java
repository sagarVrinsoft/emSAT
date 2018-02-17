package com.vrinsoft.emsat.apis.api.sign_out;


import com.vrinsoft.emsat.apis.model.sign_out.BeanLogOut;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogOutApiHandler {

    public void logOut(String userID, String token, final OnLogOut onLogOut) {

        Call<ArrayList<BeanLogOut>> listCall = ApiClient.getApiInterface().logOut(userID, token);

        listCall.enqueue(new Callback<ArrayList<BeanLogOut>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanLogOut>> call, Response<ArrayList<BeanLogOut>> response) {
                ArrayList<BeanLogOut> beanLogout = response.body();
                onLogOut.getResponse(true, beanLogout, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanLogOut>> call, Throwable t) {
                onLogOut.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
