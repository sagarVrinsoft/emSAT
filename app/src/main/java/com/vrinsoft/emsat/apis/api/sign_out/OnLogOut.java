package com.vrinsoft.emsat.apis.api.sign_out;


import com.vrinsoft.emsat.apis.model.sign_out.BeanLogOut;

import java.util.ArrayList;


public interface OnLogOut {
    public void getResponse(boolean isSuccess, ArrayList<BeanLogOut> beanLogOut, String errorMsgSystem);
}
