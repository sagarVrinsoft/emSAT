package com.vrinsoft.emsat.utils;


/**
 * Created by komal on 8/6/17.
 */

public class API_Constants {
    public static final int API_CODE_TOKEN_INVALID = -1;
    public static final int API_CODE_RESPONSE_SUCCESS = 1;
    public static final int API_CODE_RESPONSE_FAIL = 0;
    public static final int ADD_NEW_CAR = 0;
    public static final int SET_CAR_DEFAULT = 1;
    public static final int SET_CAR_DEFAULT_NOT = 0;
    public static final int SKIP_USER = 1;

    public interface GOOGLE_API{
        public static String MODE_TRAVEL = "driving";
        public static String STATUS_OK = "OK";

        public interface STATIC_MAP
        {
            public interface MARKER
            {
                public static String SIZE_TINY = "tiny";
                public static String SIZE_MID = "mid";
                public static String SIZE_SMALL = "small";
            }
//            public static String markerSize = "tiny";
            public static String markerColor = "black";
            public static String pathColor = "0x000000ff";
            public static String path_thickness = "1";
            public static String scale = "2";// resolution of Image
            public static String map_width= "512";
            public static String map_height= "256";
        }
    }

    public interface USER_TYPE{
        public static int TYPE_DRIVER = 2;
//        public static int TYPE_PASSENGER = 1;
    }
}
