package com.vrinsoft.emsat.activity.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.signin.SignIn;
import com.vrinsoft.emsat.utils.NavigationUtils;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chooseAction();

            }
        }, 1500);
    }

    private void chooseAction() {
        /*if (!Pref.getValue(Splash.this, AppPreference.PREF_LOGIN, "").equals("1")) {
            NavigationUtils.startActivityWithClearStack(this, SignIn.class, null);
        } else {
            NavigationUtils.startActivityWithClearStack(this, Home.class, null);
        }*/
        NavigationUtils.startActivityWithClearStack(this, SignIn.class, null);
    }
}
