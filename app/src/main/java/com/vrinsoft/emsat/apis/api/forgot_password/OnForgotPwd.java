package com.vrinsoft.emsat.apis.api.forgot_password;


import com.vrinsoft.emsat.apis.model.forgot_password.BeanForgotPwd;

import java.util.ArrayList;

/**
 * Created by dorji on 29/7/17.
 */

public interface OnForgotPwd {
    public void getResponse(boolean isSuccess, ArrayList<BeanForgotPwd> beanForgotPwd, String errorMsgSystem);
}
