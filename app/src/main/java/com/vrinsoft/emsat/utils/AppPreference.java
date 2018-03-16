package com.vrinsoft.emsat.utils;

public class AppPreference {

    public static String DATE_FORMAT = "date_format";
    public static String DEF_DATE_FORMAT = AppConstants.DATE_FORMAT.API_DD_MM_YYYY;
    public static String DEFAULT_STR = "";
    public static int DEFAULT_INT = 0;
    public static String LOGIN = "login";
    public static String FCM_REG_ID = "regId";
    public static String IS_DEVICE_REGISTER_FCM = "IS_DEVICE_REGISTER_FCM";
    public static String CURRENCY_SYMBOL = "currency_symbol";
    public static String CURRENCY_DECIMAL = "currency_decimal";
//    ====================================
    public interface LANGUAGE {
        public static String LANGUAGE_NAME = "LANGUAGE_NAME";//string value
        public static String LANGUAGE_ID = "LANGUAGE_ID";//string value
        public static String DEFAULT_LANGUAGE_ID = "1";//string value
        public static String DEFAULT_LANGUAGE_LABELS = "DEFAULT_LANGUAGE_LABELS";//ENGLISH
    }

    public interface USER_INFO {
        public static String USER_ID = "USERID";
        public static String NAME = "NAME";
        public static String EMAIL_ID = "EMAIL";
        public static String COUNTRY_CODE = "COUNTRY_CODE";
        public static String MOBILE_NO = "MOBILENO";
        public static String PASSWORD = "PASSWORD";
        public static String DOB = "DOB";
        public static String GENDER = "GENDER";
        public static String TOKEN = "TOKEN";
        public static String USER_PROFILE = "USER_PROFILE";
    }

    public interface USER_STATUS{
        public static int LOGON = 1;
        public static int LOGOFF = 0;
    }

}
