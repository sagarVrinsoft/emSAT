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
import com.vrinsoft.emsat.activity.signup.Register;
import com.vrinsoft.emsat.databinding.DialogForgotPasswordBinding;
import com.vrinsoft.emsat.databinding.SignInBinding;
import com.vrinsoft.emsat.robinhood.router.Director;
import com.vrinsoft.emsat.utils.NavigationUtils;
import com.vrinsoft.emsat.utils.Validator;
import com.vrinsoft.emsat.utils.ViewUtils;
import com.vrinsoft.emsat.widget.CustomEditTextView;
import com.vrinsoft.emsat.widget.CustomTextView;

import static com.vrinsoft.emsat.utils.NavigationUtils.finishCurrentActivity;
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
        mBinding.txtSignUpNavigation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSignIn:

                if (validation()) {
                    ViewUtils.showDialog(mActivity, false);
                    NavigationUtils.startActivityWithClearStack(mActivity, Home.class, null);

                }
                break;
            case R.id.txtForgotPassword:
                showForgotPasswordDialog();
            case R.id.txtSignUpNavigation:
                NavigationUtils.startActivity(this, Register.class, null);
                break;
        }
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishCurrentActivity(mActivity);
    }
}
