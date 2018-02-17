package com.vrinsoft.emsat.apis.api.cms;

import com.vrinsoft.emsat.apis.model.cms.BeanCMS;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CMSApiHandler {

    public void cmsData(String cmsID, final OnCMS onCMS) {

        Call<ArrayList<BeanCMS>> listCall = ApiClient.getApiInterface().cms(cmsID);

        listCall.enqueue(new Callback<ArrayList<BeanCMS>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanCMS>> call, Response<ArrayList<BeanCMS>> response) {
                ArrayList<BeanCMS> beanCMS = response.body();
                onCMS.getResponse(true, beanCMS, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanCMS>> call, Throwable t) {
                onCMS.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
