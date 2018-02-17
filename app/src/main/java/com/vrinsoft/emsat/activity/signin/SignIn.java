package com.vrinsoft.emsat.activity.signin;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.Home;
import com.vrinsoft.emsat.databinding.DialogForgotPasswordBinding;
import com.vrinsoft.emsat.databinding.SignInBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Validator;
import com.vrinsoft.emsat.utils.ViewUtils;
import com.vrinsoft.emsat.widget.CustomEditTextView;
import com.vrinsoft.emsat.widget.CustomTextView;

import static com.vrinsoft.emsat.utils.Validator.checkValidation;
import static com.vrinsoft.emsat.utils.ViewUtils.checkEmail;
import static com.vrinsoft.emsat.utils.ViewUtils.getEtBackground;
import static com.vrinsoft.emsat.utils.ViewUtils.setTextInputLayout;


public class SignIn extends AppCompatActivity implements View.OnClickListener {

    Bundle bundle = null;
    String TAG = SignIn.this.getClass().getSimpleName();
    private SignInBinding mBinding;
    private Activity mActivity;
    private Director director;
    private String DEVICE_TYPE = "2"; // 2 for android, 1 for iphone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.sign_in);

        mActivity = this;
        director = new Director(this);
        setUIConfig();
    }

    public void setUIConfig() {
        setUiListeners();
        addTextChangeListeners();
    }

    private void addTextChangeListeners() {
        mBinding.etEmail.addTextChangedListener(new MyTextWatcher(mBinding.etEmail));
        mBinding.etPassword.addTextChangedListener(new MyTextWatcher(mBinding.etPassword));
    }

    private void setUiListeners() {
        mBinding.txtSignIn.setOnClickListener(this);
        mBinding.txtForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSignIn:

                if (validation()) {
                    ViewUtils.showDialog(mActivity, false);
                    NavigationUtils.startActivityWithClearStack(mActivity, Home.class, null);

                    /*loginApiHandler.requestLogin
                            (mBinding.etMobileNo.getText().toString(),
                                    mBinding.txtMobileCode.getText().toString(),
                                    mBinding.etPassword.getText().toString(),DEVICE_TYPE,Pref.getValue(mActivity,AppPreference.FCM_PREF_REG_ID,""),
                                    new OnLogin() {
                                        @Override
                                        public void getResponse(boolean isSuccess, ArrayList<BeanLogin> beanLogin, String errorMsgSystem) {
                                            if (isSuccess) {
                                                if (beanLogin.get(0).getCode() == AppConstants.API_CODE_RESPONSE_SUCCESS) {
                                                    ViewUtils.showDialog(mActivity, true);
                                                    //region Set Preference
                                                    Pref.setValue(mActivity, AppPreference.PREF_LOGIN, "1");

                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_USER_ID, beanLogin.get(0).getResult().get(0).getUserId());
                                                    TripUtils.setCurrentUserType(mActivity, Integer.parseInt(beanLogin.get(0).getResult().get(0).getCurrent_role()));
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_USER_TYPE_ACTUAL, Integer.parseInt(beanLogin.get(0).getResult().get(0).getUserType()));
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_MOBILE_NO, beanLogin.get(0).getResult().get(0).getMobileNo());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_PASSWORD, mBinding.etPassword.getText().toString());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_EMERGENCY_NO, beanLogin.get(0).getResult().get(0).getEmergencyMobileNumber());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_TOKEN, beanLogin.get(0).getResult().get(0).getToken());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_AGE, beanLogin.get(0).getResult().get(0).getAge());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_PROFESSION, beanLogin.get(0).getResult().get(0).getProfession());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_FIRST_NAME, beanLogin.get(0).getResult().get(0).getFirstName());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_LAST_NAME, beanLogin.get(0).getResult().get(0).getLastName());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_COUNTRY_CODE, beanLogin.get(0).getResult().get(0).getCountryCode());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_GENDER, beanLogin.get(0).getResult().get(0).getGender());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_DOB, beanLogin.get(0).getResult().get(0).getDob());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_EMAIL_ID, beanLogin.get(0).getResult().get(0).getEmail());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_METRO_AREA, beanLogin.get(0).getResult().get(0).getMetroArea());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_USER_PROFILE, beanLogin.get(0).getResult().get(0).getUserImage());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_SSN_NUMBER, beanLogin.get(0).getResult().get(0).getSsnNumber());

                                                    Pref.setValue(mActivity, AppPreference.PREF_IS_RIDE_ONGOING_STATUS, beanLogin.get(0).getResult().get(0).getIsRideOngoingStatus());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_RIDING_COMPANION, beanLogin.get(0).getResult().get(0).getRidingCompanionPreferences());
                                                    Pref.setValue(mActivity, AppPreference.USER_INFO.PREF_ALLOWED_RIDE, beanLogin.get(0).getResult().get(0).getAllowRider());

                                                    Pref.setValue(mActivity, AppPreference.VEHICLE_INFO.PREF_VEHICLE_MODEL, beanLogin.get(0).getResult().get(0).getVehicleModel());
                                                    Pref.setValue(mActivity, AppPreference.VEHICLE_INFO.PREF_VEHICLE_YEAR, beanLogin.get(0).getResult().get(0).getVehicleYear());
                                                    Pref.setValue(mActivity, AppPreference.VEHICLE_INFO.PREF_VEHICLE_STATE, beanLogin.get(0).getResult().get(0).getVehicleState());
                                                    Pref.setValue(mActivity, AppPreference.VEHICLE_INFO.PREF_VEHICLE_PLATE_NO, beanLogin.get(0).getResult().get(0).getVehiclePlateNo());

                                                    Pref.setValue(mActivity, AppPreference.USER_SETTINGS.PREF_IS_NOTIFICATION_ON, beanLogin.get(0).getResult().get(0).getIsNotificationOn());
                                                    Pref.setValue(mActivity, AppPreference.USER_SETTINGS.PREF_APP_NEW_FEATURES, beanLogin.get(0).getResult().get(0).getWhenAppHasNewFeature());
                                                    Pref.setValue(mActivity, AppPreference.USER_SETTINGS.PREF_NEW_DRIVER_JOIN, beanLogin.get(0).getResult().get(0).getWhenNewDriverJoin());
                                                    Pref.setValue(mActivity, AppPreference.USER_SETTINGS.PREF_FAV_DRIVER_RIDING, beanLogin.get(0).getResult().get(0).getWhenFavDriverRiding());

                                                    Pref.setValue(mActivity, AppPreference.PAYMENT.PREF_WALLET_AMOUNT, beanLogin.get(0).getResult().get(0).getWalletAmount());
                                                    //endregion

                                                    NavigationUtils.startActivityWithClearStack(mActivity, Home.class, null);
                                                } else {
                                                    ViewUtils.showDialog(mActivity, true);
                                                    ViewUtils.showToast(mActivity, beanLogin.get(0).getMessage(), null);
                                                }
                                            } else {
                                                ViewUtils.showDialog(mActivity, true);
                                                ViewUtils.showToast(mActivity, errorMsgSystem, null);
                                            }
                                        }
                                    });*/
                }
                break;
            case R.id.txtForgotPassword:
                showForgotPasswordDialog();
                break;
        }
    }

    //region Validation
    public boolean validation() {
        if (checkValidation(mActivity, mBinding.etEmail, getString(R.string.please_enter_email))) {
            return false;
        } else if (!checkEmail(mBinding.etEmail.getText().toString().trim())) {
            ViewUtils.showToast(mActivity,getString(R.string.please_enter_valid_email)
                    , mBinding.etEmail);
            return false;
        }
        else if (checkValidation(mActivity, mBinding.etPassword, getString(R.string.please_enter_password))) {
            return false;
        }else {
            return true;
        }
    }

    public void showForgotPasswordDialog() {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_DeviceDefault_Dialog);
        final DialogForgotPasswordBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_forgot_password, null, false);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding.etEmail.addTextChangedListener(new MyTextWatcher(binding.etEmail));
        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        binding.txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validator.isEmptyStr(binding.etEmail.getText().toString().trim())) {
                    ViewUtils.showToast(mActivity,
                            getString(R.string.please_enter_email), null);
                    binding.etEmail.requestFocus();
                }else if (!checkEmail(binding.etEmail.getText().toString().trim())) {
                    ViewUtils.showToast(mActivity,getString(R.string.please_enter_valid_email)
                            , binding.etEmail);
                } else {
//                    ViewUtils.showDialog(mActivity, false);
                    /*forgotPwdApiHandler.requestForgotPwd(etMobileNo.getText().toString().trim(), new OnForgotPwd() {
                        @Override
                        public void getResponse(boolean isSuccess, ArrayList<BeanForgotPwd> beanForgotPwd, String errorMsgSystem) {
                            if (isSuccess) {
                                if (beanForgotPwd.get(0).getCode() == AppConstants.API_CODE_RESPONSE_SUCCESS) {
                                    ViewUtils.showDialog(mActivity, true);
                                    ViewUtils.showToast(mActivity, beanForgotPwd.get(0).getMessage(), null);
                                } else {
                                    ViewUtils.showDialog(mActivity, true);
                                    ViewUtils.showToast(mActivity, beanForgotPwd.get(0).getMessage(), null);
                                }
                            } else {
                                ViewUtils.showDialog(mActivity, true);
                                ViewUtils.showToast(mActivity, errorMsgSystem, null);
                            }
                        }
                    });*/
                    dialog.dismiss();
                }
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //region Custom Text Watcher
    public class MyTextWatcher implements TextWatcher {

        View v;

        public MyTextWatcher(View v) {
            this.v = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (v.getId()) {
                case R.id.etPassword:
                    if (s.length() > 0) {
                        setTextInputLayout(mBinding.etPassword, null, mBinding.llPassword, getEtBackground(mActivity), mBinding.txtPassword, View.VISIBLE, mBinding.vPassword, View.GONE);
                    } else {
                        setTextInputLayout(mBinding.etPassword, getString(R.string.password), mBinding.llPassword, null, mBinding.txtPassword, View.GONE, mBinding.vPassword, View.VISIBLE);
                    }
                    break;
                case R.id.etEmail:
                    if (s.length() > 0) {
                        setTextInputLayout(mBinding.etEmail, null, mBinding.llEmail, getEtBackground(mActivity), mBinding.txtEmail, View.VISIBLE, mBinding.vEmail, View.GONE);
                    } else {
                        setTextInputLayout(mBinding.etEmail, getString(R.string.email_hint), mBinding.llEmail, null, mBinding.txtEmail, View.GONE, mBinding.vEmail, View.VISIBLE);
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
