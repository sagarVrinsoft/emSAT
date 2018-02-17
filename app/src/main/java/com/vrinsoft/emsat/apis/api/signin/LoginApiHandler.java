package com.vrinsoft.emsat.apis.api.signin;

import com.google.gson.Gson;
import com.vrinsoft.emsat.apis.model.signin.BeanLogin;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dorji on 29/7/17.
 */

public class LoginApiHandler
{
    public void requestLogin(String mobile_no, String country_code, String password, String device_type, String device_token, final OnLogin onLogin) {

        Call<ArrayList<BeanLogin>> listCall = ApiClient.getApiInterface().
                login(mobile_no, country_code, password,device_type,device_token);

        listCall.enqueue(new Callback<ArrayList<BeanLogin>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanLogin>> call, Response<ArrayList<BeanLogin>> response) {
                ArrayList<BeanLogin> beanLogin = response.body();
                onLogin.getResponse(true, beanLogin, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanLogin>> call, Throwable t) {
                onLogin.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }

}
