package com.vrinsoft.emsat.apis.api.user_profile.update_profile;


import com.vrinsoft.emsat.apis.model.user_profile.update_profile.BeanUpdateProfile;

import java.util.ArrayList;


public interface OnProfileUpdate {
    public void getResponse(boolean isSuccess, ArrayList<BeanUpdateProfile> beanUpdateProfile, String errorMsgSystem);
}
