package com.vrinsoft.emsat.apis.rest;


import com.vrinsoft.emsat.activity.subcategory.model.BeanNotificationList;
import com.vrinsoft.emsat.apis.model.SubCategory.BinSubCategory;
import com.vrinsoft.emsat.apis.model.category.BinCategory;
import com.vrinsoft.emsat.apis.model.change_password.BeanChangePassword;
import com.vrinsoft.emsat.apis.model.cms.BeanCMS;
import com.vrinsoft.emsat.apis.model.common.BinGeneralApiResp;
import com.vrinsoft.emsat.apis.model.faqs.BeanFaqs;
import com.vrinsoft.emsat.apis.model.forgot_password.BeanForgotPwd;
import com.vrinsoft.emsat.apis.model.register.BeanRegister;
import com.vrinsoft.emsat.apis.model.sign_out.BeanLogOut;
import com.vrinsoft.emsat.apis.model.signin.BeanLogin;
import com.vrinsoft.emsat.apis.model.user_profile.update_profile.BeanUpdateProfile;
import com.vrinsoft.emsat.apis.model.user_profile.view_profile.BeanViewProfile;
import com.vrinsoft.emsat.apis.test_list.BinTestList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @FormUrlEncoded
    @POST("login.php")
    Call<ArrayList<BeanLogin>> login(@Field("email") String email,
                                     @Field("password") String password,
                                     @Field("is_phone") String device_type,
                                     @Field("device_token") String device_token);
    @FormUrlEncoded
    @POST("forgot_password.php")
    Call<ArrayList<BeanForgotPwd>> forgot_password(@Field("email") String email);

    @FormUrlEncoded
    @POST("change_password.php")
    Call<ArrayList<BeanChangePassword>> changePassword(@Field("user_id") String user_id,
                                                       @Field("token") String token,
                                                       @Field("old_password") String old_password,
                                                       @Field("new_password") String new_password
    );

    /*@FormUrlEncoded
    @POST("update_profile.php")
    Call<ArrayList<BeanUpdateProfile>> updateProfile(@Field("user_id") String user_id,
                                                     @Field("name") String name,
                                                     @Field("mobile_number") String mobile_number,
                                                     @Field("email") String email,
                                                     @Field("dob") String dob,
                                                     @Field("image") String image,
                                                     @Field("gender") int gender,
                                                     @Field("token") String token);*/

    @Multipart
    @POST("update_profile.php")
    Call<List<BeanUpdateProfile>> updateProfile(@PartMap() Map<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part file);
    @FormUrlEncoded
    @POST("view_profile.php")
    Call<ArrayList<BeanViewProfile>> viewProfile(@Field("user_id") String user_id,
                                                 @Field("token") String token);

    @FormUrlEncoded
    @POST("view_faqs.php")
    Call<ArrayList<BeanFaqs>> fetchFAQList(@Field("user_id") String user_id,
                                           @Field("token") String token,
                                           @Field("page_no") int page_no
    );

    @FormUrlEncoded
    @POST("cms.php")
    Call<ArrayList<BeanCMS>> cms(@Field("cms_id") String cms_id);

    @FormUrlEncoded
    @POST("logout.php")
    Call<ArrayList<BeanLogOut>> logOut(@Field("user_id") String user_id,
                                       @Field("token") String token
    );

    @FormUrlEncoded
    @POST("category_list.php")
    Call<ArrayList<BinCategory>> getListOfCategories(@Field("user_id") String user_id,
                                                     @Field("token") String token,
                                                     @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("sub_category_list.php")
    Call<ArrayList<BinSubCategory>> getListOfSubCategories(@Field("user_id") String user_id,
                                                        @Field("category_id") String category_id,
                                                        @Field("token") String token
    );

    @FormUrlEncoded
    @POST("test_list.php")
    Call<ArrayList<BinTestList>> getListOfTests(@Field("user_id") String user_id,
                                                @Field("sub_category_id") String category_id,
                                                @Field("token") String token,
                                                @Field("page_no") String page_no
    );

    @FormUrlEncoded
    @POST("add_score.php")
    Call<ArrayList<BinGeneralApiResp>> submitTestScore(@Field("user_id") String user_id,
                                                      @Field("sub_category_id") String category_id,
                                                      @Field("test_id") String test_id,
                                                      @Field("test_score") String test_score,
                                                      @Field("taken_time") String taken_time,
                                                      @Field("is_skip") int is_skip,
                                                      @Field("is_correct") int is_correct,
                                                      @Field("is_wrong") int is_wrong,
                                                      @Field("token") String token
                                                      );
}
