package com.vrinsoft.emsat.apis.api.register;


import com.vrinsoft.emsat.apis.model.register.BeanRegister;

import java.util.ArrayList;

/**
 * Created by dorji on 29/7/17.
 */

public interface OnRegister {
    public void getResponse(boolean isSuccess, ArrayList<BeanRegister> beanRegister, String errorMsgSystem);
}
