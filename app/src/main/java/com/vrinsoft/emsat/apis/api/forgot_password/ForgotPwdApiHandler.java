package com.vrinsoft.emsat.apis.api.forgot_password;


import com.vrinsoft.emsat.apis.model.forgot_password.BeanForgotPwd;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwdApiHandler {
    public void requestForgotPwd(String email, final OnForgotPwd onForgotPwd) {

        Call<ArrayList<BeanForgotPwd>> listCall = ApiClient.getApiInterface().forgot_password(email);

        listCall.enqueue(new Callback<ArrayList<BeanForgotPwd>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanForgotPwd>> call, Response<ArrayList<BeanForgotPwd>> response) {
                ArrayList<BeanForgotPwd> beanForgotPwd = response.body();
                onForgotPwd.getResponse(true, beanForgotPwd, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanForgotPwd>> call, Throwable t) {
                onForgotPwd.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
