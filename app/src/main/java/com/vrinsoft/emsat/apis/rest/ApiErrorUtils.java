package com.vrinsoft.emsat.apis.rest;

import android.util.Log;


import java.io.IOException;
import java.net.SocketException;

/**
 * Created by komal on 8/6/17.
 */

public class ApiErrorUtils {

    static String TAG = ApiErrorUtils.class.getSimpleName();
    public static final String ERROR_NETWORK = "NETWORK PROBLEM";
    public static final String CONNECTION_TIMEOUT = "CONNECTION TIMEOUT";
    public static final String SOMETHING_WENT_WRONG = "INTERNAL ERROR";

    public static String getErrorMsg(Throwable t)
    {
//        FirebaseCrash.report(t);
        if(t instanceof IOException)
        {
//            FirebaseCrash.log(ERROR_NETWORK);
            Log.e(TAG, ERROR_NETWORK);
            return ERROR_NETWORK;
        }
        else if(t instanceof SocketException)
        {
//            FirebaseCrash.log(CONNECTION_TIMEOUT);
            Log.e(TAG, CONNECTION_TIMEOUT);
            return CONNECTION_TIMEOUT;
        }
        else
        {
//            FirebaseCrash.log(SOMETHING_WENT_WRONG);
            Log.e(TAG, t.toString());
            return SOMETHING_WENT_WRONG;
        }
    }
}
