package com.vrinsoft.emsat.apis.multipart_entity.profile;


import com.vrinsoft.emsat.apis.model.user_profile.update_profile.BeanUpdateProfile;

public interface OnProfileUpdate {
//    public void getResponse(boolean isSuccess, BinProfileUpdate binProfileUpdate, String errorMsgSystem);
    public void getResponse(boolean isSuccess, BeanUpdateProfile binProfileUpdate, String errorMsgSystem);
}
