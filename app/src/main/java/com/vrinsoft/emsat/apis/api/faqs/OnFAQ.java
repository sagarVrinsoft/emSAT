package com.vrinsoft.emsat.apis.api.faqs;


import com.vrinsoft.emsat.apis.model.faqs.BeanFaqs;

import java.util.ArrayList;

/**
 * Created by dorji on 29/7/17.
 */

public interface OnFAQ {
    public void getResponse(boolean isSuccess, ArrayList<BeanFaqs> beanFaq, String errorMsgSystem);
}
