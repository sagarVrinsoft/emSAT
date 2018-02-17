package com.vrinsoft.emsat.apis.api.user_profile.view_profile;



import com.vrinsoft.emsat.apis.model.user_profile.view_profile.BeanViewProfile;

import java.util.ArrayList;


public interface OnViewProfile {
    public void getResponse(boolean isSuccess, ArrayList<BeanViewProfile> beanViewProfile, String errorMsgSystem);
}
