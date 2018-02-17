package com.vrinsoft.emsat.apis.api.cms;


import com.vrinsoft.emsat.apis.model.cms.BeanCMS;

import java.util.ArrayList;

/**
 * Created by dorji on 29/7/17.
 */

public interface OnCMS {
    public void getResponse(boolean isSuccess, ArrayList<BeanCMS> cms, String errorMsgSystem);
}
