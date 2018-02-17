package com.vrinsoft.emsat.apis.model.notification_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("ride_id")
    @Expose
    private String rideId;
    @SerializedName("pickup_location")
    @Expose
    private String pickupLocation;
    @SerializedName("destination_location")
    @Expose
    private String destinationLocation;
    @SerializedName("pickup_latitude")
    @Expose
    private String pickupLatitude;
    @SerializedName("pickup_longitude")
    @Expose
    private String pickupLongitude;
    @SerializedName("destination_latitude")
    @Expose
    private String destinationLatitude;
    @SerializedName("destination_longitude")
    @Expose
    private String destinationLongitude;
    @SerializedName("ride_status")
    @Expose
    private String rideStatus;
    @SerializedName("requestee_id")
    @Expose
    private Object requesteeId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("to_user_id")
    @Expose
    private String toUserId;
    @SerializedName("to_user_name")
    @Expose
    private String toUserName;
    @SerializedName("to_gender")
    @Expose
    private String toGender;
    @SerializedName("to_age")
    @Expose
    private Integer toAge;
    @SerializedName("to_mobile_no")
    @Expose
    private String toMobileNo;
    @SerializedName("to_profession")
    @Expose
    private String toProfession;
    @SerializedName("to_image_url")
    @Expose
    private String toImageUrl;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("isBroadcast")
    @Expose
    private String isBroadcast;
    @SerializedName("broadcastMsg")
    @Expose
    private String broadcastMsg;
    @SerializedName("request_type")
    @Expose
    private String requestType;

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public String getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(String pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public String getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(String pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public String getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(String destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public String getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(String destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public String getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(String rideStatus) {
        this.rideStatus = rideStatus;
    }

    public Object getRequesteeId() {
        return requesteeId;
    }

    public void setRequesteeId(Object requesteeId) {
        this.requesteeId = requesteeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToGender() {
        return toGender;
    }

    public void setToGender(String toGender) {
        this.toGender = toGender;
    }

    public Integer getToAge() {
        return toAge;
    }

    public void setToAge(Integer toAge) {
        this.toAge = toAge;
    }

    public String getToMobileNo() {
        return toMobileNo;
    }

    public void setToMobileNo(String toMobileNo) {
        this.toMobileNo = toMobileNo;
    }

    public String getToProfession() {
        return toProfession;
    }

    public void setToProfession(String toProfession) {
        this.toProfession = toProfession;
    }

    public String getToImageUrl() {
        return toImageUrl;
    }

    public void setToImageUrl(String toImageUrl) {
        this.toImageUrl = toImageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsBroadcast() {
        return isBroadcast;
    }

    public void setIsBroadcast(String isBroadcast) {
        this.isBroadcast = isBroadcast;
    }

    public String getBroadcastMsg() {
        return broadcastMsg;
    }

    public void setBroadcastMsg(String broadcastMsg) {
        this.broadcastMsg = broadcastMsg;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}