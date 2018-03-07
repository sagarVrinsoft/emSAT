package com.vrinsoft.emsat.apis.api.faqs;


import com.vrinsoft.emsat.apis.model.faqs.BeanFaqs;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FAQApiHandler {

    public void fetchFAQList(String user_id, String token, int pageNo, final OnFAQ onFAQ) {

//        Call<ArrayList<BeanFaqs>> listCall = ApiClient.getApiInterface().fetchFAQList(user_id, token, pageNo);
        Call<ArrayList<BeanFaqs>> listCall = ApiClient.getApiInterface().fetchFAQList(user_id, token, pageNo);

        listCall.enqueue(new Callback<ArrayList<BeanFaqs>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanFaqs>> call, Response<ArrayList<BeanFaqs>> response) {
                ArrayList<BeanFaqs> beanFaq = response.body();
                onFAQ.getResponse(true, beanFaq, null);
            }

            @Override
            public void onFailure(Call<ArrayList<BeanFaqs>> call, Throwable t) {
                onFAQ.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
}
