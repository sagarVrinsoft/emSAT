package com.vrinsoft.emsat.activity.signup;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.activity.home.Home;
import com.vrinsoft.emsat.apis.model.register.BeanRegister;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.RegisterBinding;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vrinsoft.emsat.utils.AppConstants.isValidPhoneNumber;
import static com.vrinsoft.emsat.utils.NavigationUtils.finishCurrentActivity;
import static com.vrinsoft.emsat.utils.Validator.checkValidation;
import static com.vrinsoft.emsat.utils.ViewUtils.checkEmail;
import static com.vrinsoft.emsat.utils.ViewUtils.getEtBackground;
import static com.vrinsoft.emsat.utils.ViewUtils.setTextInputLayout;


public class Register extends AppCompatActivity implements View.OnClickListener {

    private Activity mActivity;
    private ViewUtils viewUtils;
    private RegisterBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.register);
        mActivity = this;
        viewUtils = new ViewUtils(mActivity);
        setUIConfig();
    }

    public void setUIConfig() {
        setUiListeners();
        addTextChangeListeners();
    }

    private void setUiListeners() {
        mBinding.txtSignIn.setOnClickListener(this);
    }

    private void addTextChangeListeners() {
        mBinding.etFN.addTextChangedListener(new MyTextWatcher(mBinding.etFN));
        mBinding.etMobileNo.addTextChangedListener(new MyTextWatcher(mBinding.etMobileNo));
        mBinding.etPassword.addTextChangedListener(new MyTextWatcher(mBinding.etPassword));
        mBinding.etEmail.addTextChangedListener(new MyTextWatcher(mBinding.etEmail));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBarConfig();
    }

    private void setToolBarConfig() {
        mBinding.toolBar.imgHome.setVisibility(View.GONE);
        mBinding.toolBar.txtTitle.setText(getString(R.string.sign_up));
        mBinding.toolBar.imgBack.setVisibility(View.VISIBLE);
        mBinding.toolBar.imgBack.setOnClickListener(this);
    }


    public boolean validation() {
        if (checkValidation(mActivity, mBinding.etFN,
                getString(R.string.please_enter_name))) {
            return false;
        } else if (checkValidation(mActivity, mBinding.etMobileNo,
                getString(R.string.please_enter_mobile_no))) {
            return false;
        } else if (!isValidPhoneNumber(mBinding.etMobileNo.getText().toString().trim()) ||
                mBinding.etMobileNo.getText().toString().length() < 10 ||
                !TextUtils.isDigitsOnly(mBinding.etMobileNo.getText().toString().trim())) {
            ViewUtils.showToast(mActivity,
                    getString(R.string.please_enter_valid_mobile_no), mBinding.etMobileNo);
            return false;
        }
        if (checkValidation(mActivity, mBinding.etEmail, getString(R.string.please_enter_email))) {
            return false;
        } else if (!checkEmail(mBinding.etEmail.getText().toString().trim())) {
            ViewUtils.showToast(mActivity, getString(R.string.please_enter_valid_email)
                    , mBinding.etEmail);
            return false;
        } else if (checkValidation(mActivity, mBinding.etPassword, getString(R.string.please_enter_password))) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtSignIn:
                if(validation())
                {
                    ViewUtils.showDialog(mActivity, false);
                    final String name = mBinding.etFN.getText().toString().trim();
                    final String email = mBinding.etEmail.getText().toString().trim();
                    final String phone = mBinding.etMobileNo.getText().toString().trim();
                    final String password = mBinding.etPassword.getText().toString().trim();

                    Call<ArrayList<BeanRegister>> listCall =
                            ApiClient.getApiInterface().register(name, phone, email, password, "", AppConstants.GENDER.MALE);

                    listCall.enqueue(new Callback<ArrayList<BeanRegister>>() {
                        @Override
                        public void onResponse(Call<ArrayList<BeanRegister>> call, Response<ArrayList<BeanRegister>> response) {
                            ArrayList<BeanRegister> beanRegister = response.body();
                            ViewUtils.showDialog(mActivity, true);
                            if (beanRegister.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {
                                Pref.setValue(mActivity, AppPreference.USER_INFO.NAME, name);
                                Pref.setValue(mActivity, AppPreference.USER_INFO.EMAIL_ID, email);
                                Pref.setValue(mActivity, AppPreference.USER_INFO.PASSWORD, password);
                                Pref.setValue(mActivity, AppPreference.USER_INFO.MOBILE_NO, phone);
                                NavigationUtils.startActivityWithClearStack(mActivity, Home.class, null);
                            } else {
                                ViewUtils.showToast(mActivity, beanRegister.get(0).getMessage(), null);
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<BeanRegister>> call, Throwable t) {
                            ViewUtils.showDialog(mActivity, true);
                            ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
                        }
                    });
                }
                break;
            case R.id.imgBack:
                finishCurrentActivity(mActivity);
                break;
        }
    }

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
                case R.id.etFN:
                    if (s.length() > 0) {
                        setTextInputLayout(mBinding.etFN, null, mBinding.llFN, getEtBackground(mActivity), mBinding.txtFN, View.VISIBLE, mBinding.vFN, View.GONE);
                    } else {
                        setTextInputLayout(mBinding.etFN,
                                getString(R.string.name),
                                mBinding.llFN, null, mBinding.txtFN, View.GONE, mBinding.vFN, View.VISIBLE);
                    }
                    break;
                case R.id.etMobileNo:
                    if (s.length() > 0) {
                        setTextInputLayout(mBinding.etMobileNo, null, mBinding.llMobileNo, getEtBackground(mActivity), mBinding.txtMobileNo, View.VISIBLE, mBinding.vMobileNo, View.GONE);
                    } else {
                        setTextInputLayout(mBinding.etMobileNo,
                                getString(R.string.mobile_no), mBinding.llMobileNo, null, mBinding.txtMobileNo, View.GONE, mBinding.vMobileNo, View.VISIBLE);
                    }
                    break;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishCurrentActivity(mActivity);
    }
}
