package com.vrinsoft.emsat.apis.model.common;

/**
 * Created by komal on 8/6/17.
 */

public interface OnGenApiResponse {
    public void getResponse(boolean isSuccess, BinGeneralApiResp binGeneralApiResp, String errorMsgSystem);
}
