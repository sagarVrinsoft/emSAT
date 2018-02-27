package com.vrinsoft.emsat.apis.rest;


import com.vrinsoft.emsat.activity.subcategory.model.BeanNotificationList;
import com.vrinsoft.emsat.apis.model.change_password.BeanChangePassword;
import com.vrinsoft.emsat.apis.model.cms.BeanCMS;
import com.vrinsoft.emsat.apis.model.faqs.BeanFaqs;
import com.vrinsoft.emsat.apis.model.forgot_password.BeanForgotPwd;
import com.vrinsoft.emsat.apis.model.register.BeanRegister;
import com.vrinsoft.emsat.apis.model.sign_out.BeanLogOut;
import com.vrinsoft.emsat.apis.model.signin.BeanLogin;
import com.vrinsoft.emsat.apis.model.user_profile.update_profile.BeanUpdateProfile;
import com.vrinsoft.emsat.apis.model.user_profile.view_profile.BeanViewProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.vrinsoft.emsat.apis.rest.NetworkConstants.QUESTION.METHOD_TEST_QUESTION;

public interface ApiInterface {

    //Check server time
//    http://ec2-34-231-167-130.compute-1.amazonaws.com/api/check_server_time.php

    @FormUrlEncoded
    @POST(METHOD_TEST_QUESTION)
    Call<ResponseBody> getQuestions(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("register.php")
    Call<ArrayList<BeanRegister>> register(@Field("name") String name,
                                           @Field("mobile_number") String mobile_number,
                                           @Field("email") String country_code,
                                           @Field("password") String password,
                                           @Field("dob") String date_of_birth,
                                           @Field("gender") int gender);

    //region Login
    @FormUrlEncoded
    @POST("login.php")
    Call<ArrayList<BeanLogin>> login(@Field("email") String email,
                                     @Field("password") String password,
                                     @Field("is_phone") String device_type,
                                     @Field("device_token") String device_token);
    //region Forgot_password
    @FormUrlEncoded
    @POST("forgot_password.php")
    Call<ArrayList<BeanForgotPwd>> forgot_password(@Field("email") String email);
    //endregion

    //region Update Profile
    @FormUrlEncoded
    @POST("update_profile.php")
    Call<ArrayList<BeanUpdateProfile>> updateProfile(@Field("user_id") String user_id,
                                                     @Field("f_name") String f_name,
                                                     @Field("l_name") String l_name,
                                                     @Field("profession") String profession,
                                                     @Field("dob") String dob,
                                                     @Field("metro_area") String metro_area,
                                                     @Field("mobile_no") String mobile_no,
                                                     @Field("image") String image,
                                                     @Field("gender") int gender,
                                                     @Field("prefers_ride") int prefers_ride,
                                                     @Field("allow_rider") int allow_rider,
                                                     @Field("emergecy_mobile_number") String emergecy_mobile_number,
                                                     @Field("vehicle_model") String vehicle_model,
                                                     @Field("vehicle_year") String vehicle_year,
                                                     @Field("vehicle_plate_no") String vehicle_plate_no,
                                                     @Field("vehicle_state") String vehicle_state,
                                                     @Field("token") String token);
    //endregion

    //region View Profile
    @FormUrlEncoded
    @POST("view_profile.php")
    Call<ArrayList<BeanViewProfile>> viewProfile(@Field("user_id") String user_id,
                                                 @Field("token") String token);
    //endregion

    // region Notification List
    @FormUrlEncoded
    @POST("notifications_list.php")
    Call<ArrayList<BeanNotificationList>> fetchNotificationList(@Field("user_id") String user_id,
                                                                @Field("token") String token,
                                                                @Field("page_no") int page_no,
                                                                @Field("login_roll") int login_roll
    );

    // region Notification List
    @FormUrlEncoded
    @POST("notifications_list.php")
    Call<ArrayList<com.vrinsoft.emsat.activity.home.model.category.BeanNotificationList>> fetchCategoryList(@Field("user_id") String user_id,
                                                                                                            @Field("token") String token,
                                                                                                            @Field("page_no") int page_no,
                                                                                                            @Field("login_roll") int login_roll
    );
    //endregion

    @FormUrlEncoded
    @POST("view_faqs.php")
    Call<ArrayList<BeanFaqs>> fetchFAQList(@Field("user_id") String user_id,
                                           @Field("token") String token,
                                           @Field("page_no") int page_no
    );

    // region CMS
    @FormUrlEncoded
    @POST("cms.php")
    Call<ArrayList<BeanCMS>> cms(@Field("cms_id") String cms_id);
    //endregion


    // region Change Password
    @FormUrlEncoded
    @POST("change_password.php")
    Call<ArrayList<BeanChangePassword>> changePassword(@Field("user_id") String user_id,
                                                       @Field("token") String token,
                                                       @Field("old_password") String old_password,
                                                       @Field("new_password") String new_password
    );
    //endregion

    // region Log Out
    @FormUrlEncoded
    @POST("logout.php")
    Call<ArrayList<BeanLogOut>> logOut(@Field("user_id") String user_id,
                                       @Field("token") String token
    );
}
