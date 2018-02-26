package com.vrinsoft.emsat.apis.handler_interface;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;

public interface OnResponse {
    public void getResponse(boolean isSuccess, Response<ResponseBody> response, JSONObject jsonObject, String errorMsgSystem);
}
