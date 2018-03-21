package com.vrinsoft.emsat.apis.model.register;

/**
 * Created by dorji on 29/7/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vrinsoft.emsat.apis.model.signin.BeanLogin;

import java.util.ArrayList;

public class BeanRegister {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ArrayList<Result> result = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("mobile_number")
        @Expose
        private String mobileNo;
        @SerializedName("country_code")
        @Expose
        private String countryCode;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("DOB")
        @Expose
        private String dob;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("current_role")
        @Expose
        private String current_role;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("metro_area")
        @Expose
        private String metroArea;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("age")
        @Expose
        private Integer age;
        @SerializedName("profession")
        @Expose
        private String profession;
        @SerializedName("user_image")
        @Expose
        private String userImage;
        @SerializedName("riding_companion_preferences")
        @Expose
        private String ridingCompanionPreferences;
        @SerializedName("allow_rider")
        @Expose
        private String allowRider;
        @SerializedName("ssn_number")
        @Expose
        private String ssnNumber;
        @SerializedName("emergency_mobile_number")
        @Expose
        private String emergencyMobileNumber;
        @SerializedName("vehicle_model")
        @Expose
        private String vehicleModel;
        @SerializedName("vehicle_year")
        @Expose
        private String vehicleYear;
        @SerializedName("vehicle_state")
        @Expose
        private String vehicleState;
        @SerializedName("vehicle_plate_no")
        @Expose
        private String vehiclePlateNo;
        @SerializedName("is_notification_on")
        @Expose
        private String isNotificationOn;
        @SerializedName("is_ride_ongoing_status")
        @Expose
        private String isRideOngoingStatus;
        @SerializedName("wallet_amount")
        @Expose
        private String walletAmount;
        @SerializedName("is_profile_off")
        @Expose
        private Integer isProfileOff;
        @SerializedName("when_app_has_new_feature")
        @Expose
        private Integer whenAppHasNewFeature;
        @SerializedName("when_new_driver_join")
        @Expose
        private Integer whenNewDriverJoin;
        @SerializedName("when_fav_driver_riding")
        @Expose
        private Integer whenFavDriverRiding;
        @SerializedName("date_format")
        @Expose
        private String date_format;

        public String getDate_format() {
            return date_format;
        }

        public void setDate_format(String date_format) {
            this.date_format = date_format;
        }


        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMetroArea() {
            return metroArea;
        }

        public void setMetroArea(String metroArea) {
            this.metroArea = metroArea;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getRidingCompanionPreferences() {
            return ridingCompanionPreferences;
        }

        public void setRidingCompanionPreferences(String ridingCompanionPreferences) {
            this.ridingCompanionPreferences = ridingCompanionPreferences;
        }

        public String getAllowRider() {
            return allowRider;
        }

        public void setAllowRider(String allowRider) {
            this.allowRider = allowRider;
        }

        public String getSsnNumber() {
            return ssnNumber;
        }

        public void setSsnNumber(String ssnNumber) {
            this.ssnNumber = ssnNumber;
        }

        public String getEmergencyMobileNumber() {
            return emergencyMobileNumber;
        }

        public void setEmergencyMobileNumber(String emergencyMobileNumber) {
            this.emergencyMobileNumber = emergencyMobileNumber;
        }

        public String getVehicleModel() {
            return vehicleModel;
        }

        public void setVehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
        }

        public String getVehicleYear() {
            return vehicleYear;
        }

        public void setVehicleYear(String vehicleYear) {
            this.vehicleYear = vehicleYear;
        }

        public String getVehicleState() {
            return vehicleState;
        }

        public void setVehicleState(String vehicleState) {
            this.vehicleState = vehicleState;
        }

        public String getVehiclePlateNo() {
            return vehiclePlateNo;
        }

        public void setVehiclePlateNo(String vehiclePlateNo) {
            this.vehiclePlateNo = vehiclePlateNo;
        }

        public String getIsNotificationOn() {
            return isNotificationOn;
        }

        public void setIsNotificationOn(String isNotificationOn) {
            this.isNotificationOn = isNotificationOn;
        }

        public String getIsRideOngoingStatus() {
            return isRideOngoingStatus;
        }

        public void setIsRideOngoingStatus(String isRideOngoingStatus) {
            this.isRideOngoingStatus = isRideOngoingStatus;
        }

        public String getWalletAmount() {
            return walletAmount;
        }

        public void setWalletAmount(String walletAmount) {
            this.walletAmount = walletAmount;
        }

        public Integer getIsProfileOff() {
            return isProfileOff;
        }

        public void setIsProfileOff(Integer isProfileOff) {
            this.isProfileOff = isProfileOff;
        }

        public Integer getWhenAppHasNewFeature() {
            return whenAppHasNewFeature;
        }

        public void setWhenAppHasNewFeature(Integer whenAppHasNewFeature) {
            this.whenAppHasNewFeature = whenAppHasNewFeature;
        }

        public Integer getWhenNewDriverJoin() {
            return whenNewDriverJoin;
        }

        public void setWhenNewDriverJoin(Integer whenNewDriverJoin) {
            this.whenNewDriverJoin = whenNewDriverJoin;
        }

        public Integer getWhenFavDriverRiding() {
            return whenFavDriverRiding;
        }

        public void setWhenFavDriverRiding(Integer whenFavDriverRiding) {
            this.whenFavDriverRiding = whenFavDriverRiding;
        }

        public String getCurrent_role() {
            return current_role;
        }

        public void setCurrent_role(String current_role) {
            this.current_role = current_role;
        }
    }
}
