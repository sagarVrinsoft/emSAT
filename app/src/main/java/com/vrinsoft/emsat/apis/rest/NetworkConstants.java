package com.vrinsoft.emsat.apis.rest;

/**
 * Created by dorji on 21/2/18.
 */

public class NetworkConstants {
    public static final int API_CODE_RESPONSE_SUCCESS = 1;
    public static final int API_CODE_RESPONSE_FAIL = 0;
    public static final int API_CODE_RESPONSE_INVALID_TOKEN = -3;
    public static final int API_CODE_RESPONSE_INVALID_ACCOUNT = -2;

    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_PAGE_NO = "page_no";
    public static final String API_REGISTER = "register.php";

    public interface QUESTION {
        String METHOD_TEST_QUESTION = "test_question.php";
        String KEY_TEST_ID = "test_id";
        String TEMP_TEST_ID = "1";
        String TEMP_TOKEN = "dHDOsK";
        String TEMP_USERid = "1";
    }
}
