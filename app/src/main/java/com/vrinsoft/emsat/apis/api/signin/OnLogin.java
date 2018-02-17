package com.vrinsoft.emsat.apis.api.signin;


import com.vrinsoft.emsat.apis.model.signin.BeanLogin;

import java.util.ArrayList;

/**
 * Created by dorji on 29/7/17.
 */

public interface OnLogin {
    public void getResponse(boolean isSuccess, ArrayList<BeanLogin> beanLogin, String errorMsgSystem);
}
