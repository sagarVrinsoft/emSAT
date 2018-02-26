package com.vrinsoft.emsat;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by dorji on 21/2/18.
 */

public class EmsatApplication extends Application {
    private static EmsatApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

    }

    public static EmsatApplication getApplication() {
        return sApplication;
    }
}
