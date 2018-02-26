package com.vrinsoft.emsat.apis.handler_interface;

import com.google.gson.JsonArray;

public interface OnJsonResponse {
    public void getJsonResponse(boolean isSuccess, JsonArray mJsonArray, String errorMsgSystem);
}
