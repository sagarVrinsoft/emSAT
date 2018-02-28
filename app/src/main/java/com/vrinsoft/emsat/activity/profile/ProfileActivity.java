package com.vrinsoft.emsat.activity.profile;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.apis.model.change_password.BeanChangePassword;
import com.vrinsoft.emsat.apis.model.user_profile.view_profile.BeanViewProfile;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.apis.model.change_password.BeanChangePassword;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.ActivityProfileBinding;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.Validator;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vrinsoft.emsat.utils.Validator.checkValidation;
import static com.vrinsoft.emsat.utils.ViewUtils.getEtBackground;
import static com.vrinsoft.emsat.utils.ViewUtils.setTextInputLayout;

/**
 * Created by komal on 19/2/18.
 */

public class ProfileActivity extends MasterActivity implements View.OnClickListener {
    ActivityProfileBinding profileBinding;
    Activity mActivity;
    TextView txtPassword, txtNewPassword, txtConfirmPassword, txtChangePassword, txtUpdate;
    LinearLayout llPassword, llNewPassword, llConfirmPassword;
    View vPassword, vNewPassword, vConfirmPassword;
    EditText etPassword, etNewPassword, etConfirmPassword;
    ImageView imgClose;

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public View getContentLayout() {
        profileBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_profile, null, false);
        return profileBinding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setProfileData();
        setListeners();
        profileBinding.rlChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();
            }
        });
    }

    private void setListeners() {
        profileBinding.ctMale.setOnClickListener(this);
        profileBinding.ctFemale.setOnClickListener(this);
        profileBinding.etFN.addTextChangedListener(new MyTextWatcher(profileBinding.etFN));
        profileBinding.etEmail.addTextChangedListener(new MyTextWatcher(profileBinding.etEmail));
        profileBinding.etMobileNo.addTextChangedListener(new MyTextWatcher(profileBinding.etMobileNo));
        profileBinding.txtDOB.addTextChangedListener(new MyTextWatcher(profileBinding.txtDOB));
    }

    private void setProfileData() {
        ViewUtils.showDialog(mActivity, false);
        Call<ArrayList<BeanViewProfile>> listCall =
                ApiClient.getApiInterface().viewProfile(
                        Pref.getValue(mActivity, AppPreference.USER_INFO.USER_ID, AppPreference.DEFAULT_STR),
                        Pref.getValue(mActivity, AppPreference.USER_INFO.TOKEN, AppPreference.DEFAULT_STR));

        listCall.enqueue(new Callback<ArrayList<BeanViewProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanViewProfile>> call, Response<ArrayList<BeanViewProfile>> response) {
                ArrayList<BeanViewProfile> beanViewProfile = response.body();
                ViewUtils.showDialog(mActivity, true);
                if (beanViewProfile.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {
                    ArrayList<BeanViewProfile.Result> mArrayList = beanViewProfile.get(0).getResult();
                    if(mArrayList.size() > 0)
                    {
                        profileBinding.etFN.setText(mArrayList.get(0).getName());
                        profileBinding.etMobileNo.setText(mArrayList.get(0).getMobileNo());
                        profileBinding.etEmail.setText(mArrayList.get(0).getEmail());
                        profileBinding.txtDOB.setText(mArrayList.get(0).getDob());
                        setGender(mArrayList.get(0).getGender());
                    }
                    else
                    {
                        ViewUtils.showToast(mActivity, getString(R.string.no_data_found), null);
                    }
                }
                else
                {
                    ViewUtils.showToast(mActivity, beanViewProfile.get(0).getMessage(), null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BeanViewProfile>> call, Throwable t) {
                ViewUtils.showDialog(mActivity, true);
                ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBarConfig();
    }

    public void setToolBarConfig() {
        masterBinding.toolbar.txtTitle.setVisibility(View.VISIBLE);
        masterBinding.toolbar.txtTitle.setText(getString(R.string.menu_profile));
        masterBinding.toolbar.txtTitle.setTextColor(Color.WHITE);
        masterBinding.toolbar.imgHome.setVisibility(View.VISIBLE);
        masterBinding.toolbar.imgHome.setImageResource(R.drawable.ic_home);
        masterBinding.toolbar.imgBack.setVisibility(View.GONE);
        masterBinding.toolbar.rlNotification.setVisibility(View.GONE);
        masterBinding.toolbar.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });
    }

    public void showChangePasswordDialog() {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_DeviceDefault_Dialog);
        dialog.setContentView(R.layout.dialog_change_password);
        dialog.getWindow().setWindowAnimations(R.style.CustomDialogStyle2);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        txtChangePassword = (TextView) dialog.findViewById(R.id.txtChangePassword);
        txtUpdate = (TextView) dialog.findViewById(R.id.txtUpdate);

        llPassword = (LinearLayout) dialog.findViewById(R.id.llPassword);
        llNewPassword = (LinearLayout) dialog.findViewById(R.id.llNewPassword);
        llConfirmPassword = (LinearLayout) dialog.findViewById(R.id.llCPassword);

        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);

        txtPassword = (TextView) dialog.findViewById(R.id.txtPassword);
        txtNewPassword = (TextView) dialog.findViewById(R.id.txtNewPassword);
        txtConfirmPassword = (TextView) dialog.findViewById(R.id.txtCPassword);

        vPassword = (View) dialog.findViewById(R.id.vPassword);
        vNewPassword = (View) dialog.findViewById(R.id.vNewPassword);
        vConfirmPassword = (View) dialog.findViewById(R.id.vCPassword);

        etPassword = (EditText) dialog.findViewById(R.id.etPassword);
        etNewPassword = (EditText) dialog.findViewById(R.id.etNewPassword);
        etConfirmPassword = (EditText) dialog.findViewById(R.id.etCPassword);

        etPassword.addTextChangedListener(new MyTextWatcher(etPassword));
        etNewPassword.addTextChangedListener(new MyTextWatcher(etNewPassword));
        etConfirmPassword.addTextChangedListener(new MyTextWatcher(etConfirmPassword));

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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


        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (changePasswordValidation())
                {
                    ViewUtils.showDialog(mActivity, false);
                    String userId = Pref.getValue(mActivity, AppPreference.USER_INFO.USER_ID, AppPreference.DEFAULT_STR);
                    String token = Pref.getValue(mActivity, AppPreference.USER_INFO.TOKEN, AppPreference.DEFAULT_STR);
                    String old_password = etPassword.getText().toString().trim();
                    String new_password = etConfirmPassword.getText().toString().trim();

                    Call<ArrayList<BeanChangePassword>> listCall =
                            ApiClient.getApiInterface().changePassword
                                    (userId, token, old_password, new_password);
                    listCall.enqueue(new Callback<ArrayList<BeanChangePassword>>() {
                        @Override
                        public void onResponse(Call<ArrayList<BeanChangePassword>> call,
                                               Response<ArrayList<BeanChangePassword>> response) {
                            ArrayList<BeanChangePassword> beanChangePassword = response.body();
                            ViewUtils.showDialog(mActivity, true);
                            if (beanChangePassword.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS)
                            {
                                ViewUtils.showToast(mActivity, getString(R.string.password_changed_successfully), null);
                                dialog.dismiss();
                                signOut();
                            } else {
                                ViewUtils.showToast(mActivity, beanChangePassword.get(0).getMessage(), null);
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<BeanChangePassword>> call,
                                              Throwable t) {
                            ViewUtils.showDialog(mActivity, true);
                            ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
                        }
                    });
                }
            }
        });
    }

    private boolean changePasswordValidation() {
        if (checkValidation(mActivity, etPassword,
                getString(R.string.please_enter_password))) {
            return false;
        } else if (etPassword.getText().toString().length() < 8) {
            ViewUtils.showToast(mActivity, getString(R.string.old_password_must_contains_atleast_8_characters), null);
            return false;
        } else if (!Validator.isNullEmpty(Pref.getValue(mActivity, AppPreference.USER_INFO.PASSWORD, "")) &&
                !(etPassword.getText().toString().trim().equals(Pref.getValue(mActivity, AppPreference.USER_INFO.PASSWORD, "")))) {
            ViewUtils.showToast(mActivity, getString(R.string.password_invalid), etPassword);
            return false;
        } else if (checkValidation(mActivity, etNewPassword,
                getString(R.string.please_enter_new_password))) {
            return false;
        } else if (etNewPassword.getText().toString().length() < 8) {
            ViewUtils.showToast(mActivity, getString(R.string.new_password_must_contains_atleast_8_characters), null);
            return false;
        } else if (checkValidation(mActivity, etConfirmPassword,
                getString(R.string.please_enter_confirm_password))) {
            return false;
        } else if (etConfirmPassword.getText().toString().length() < 8) {
            ViewUtils.showToast(mActivity, getString(R.string.confirm_password_must_contains_atleast_8_characters), null);
            return false;
        } else if (!etConfirmPassword.getText().toString().trim().equals(etNewPassword.getText().toString().trim())) {
            etConfirmPassword.requestFocus();
            ViewUtils.showToast(mActivity,
                    getString(R.string.new_password_confirm_password_must_be_same), etConfirmPassword);
            return false;
        } else {
            return true;
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
                case R.id.etPassword:
                    if (s.length() > 0) {
                        setTextInputLayout(etPassword, null, llPassword, getEtBackground(mActivity), txtPassword, View.VISIBLE, vPassword, View.GONE);
                    } else {
                        setTextInputLayout(etPassword,
                                getString(R.string.password_star), llPassword, null, txtPassword, View.GONE, vPassword, View.VISIBLE);
                    }
                    break;
                case R.id.etNewPassword:
                    if (s.length() > 0) {
                        setTextInputLayout(etNewPassword, null, llNewPassword, getEtBackground(mActivity), txtNewPassword, View.VISIBLE, vNewPassword, View.GONE);
                    } else {
                        setTextInputLayout(etNewPassword,
                                getString(R.string.new_password_star), llNewPassword, null, txtNewPassword, View.GONE, vNewPassword, View.VISIBLE);
                    }
                    break;
                case R.id.etCPassword:
                    if (s.length() > 0) {
                        setTextInputLayout(etConfirmPassword, null, llConfirmPassword, getEtBackground(mActivity), txtConfirmPassword, View.VISIBLE, vConfirmPassword, View.GONE);
                    } else {
                        setTextInputLayout(etConfirmPassword,
                                getString(R.string.confirm_password_star), llConfirmPassword, null, txtConfirmPassword, View.GONE, vConfirmPassword, View.VISIBLE);
                    }
                    break;
                case R.id.etFN:
                    if (s.length() > 0) {
                        setTextInputLayout(profileBinding.etFN, null, profileBinding.llFN, getEtBackground(mActivity), profileBinding.txtFN, View.VISIBLE, profileBinding.vFN, View.GONE);
                    } else {
                        setTextInputLayout(profileBinding.etFN,
                                getString(R.string.name), profileBinding.llFN, null, profileBinding.txtFN, View.GONE, profileBinding.vFN, View.VISIBLE);
                    }
                    break;
                case R.id.etEmail:
                    if (s.length() > 0) {
                        setTextInputLayout(profileBinding.etEmail, null, profileBinding.llEmail, getEtBackground(mActivity), profileBinding.txtEmail, View.VISIBLE, profileBinding.vEmail, View.GONE);
                    } else {
                        setTextInputLayout(profileBinding.etEmail,
                                getString(R.string.email_hint), profileBinding.llEmail, null, profileBinding.txtEmail, View.GONE, profileBinding.vEmail, View.VISIBLE);
                    }
                    break;
                case R.id.etMobileNo:
                    if (s.length() > 0) {
                        setTextInputLayout(profileBinding.etMobileNo, null, profileBinding.llMobileNo, getEtBackground(mActivity), profileBinding.txtMobileNo, View.VISIBLE, profileBinding.vMobileNo, View.GONE);
                    } else {
                        setTextInputLayout(profileBinding.etMobileNo,
                                getString(R.string.mobile_no), profileBinding.llMobileNo, null, profileBinding.txtMobileNo, View.GONE, profileBinding.vMobileNo, View.VISIBLE);
                    }
                    break;
                case R.id.txtDOB:
                    if (s.length() > 0) {
                        setTextInputLayout(profileBinding.txtDOB, null,
                                profileBinding.llDOB, getEtBackground(mActivity), profileBinding.txtLabelDOB, View.VISIBLE, profileBinding.vDOB, View.GONE);
                    } else {
                        setTextInputLayout(profileBinding.txtDOB,
                                getString(R.string.date_of_birth), profileBinding.llDOB, null, profileBinding.txtLabelDOB, View.GONE, profileBinding.vDOB, View.VISIBLE);
                    }
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    int mGenderSel = AppConstants.GENDER.MALE;
    private void setGender(String gender)
    {
        mGenderSel = Integer.parseInt(gender);
        switch (mGenderSel)
        {
            case AppConstants.GENDER.MALE:
                profileBinding.ctMale.setChecked(true);
                break;
            case AppConstants.GENDER.FEMALE:
                profileBinding.ctFemale.setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ctMale:
                if (!profileBinding.ctMale.isChecked()) {
                    ViewUtils.setCheckedTextView(AppConstants.NUM_OF_CHECKED_VIEWS.TWO, profileBinding.ctMale, profileBinding.ctFemale, null, null);
                    mGenderSel = AppConstants.GENDER.MALE;
                }
                break;
            case R.id.ctFemale:
                if (!profileBinding.ctFemale.isChecked()) {
                    ViewUtils.setCheckedTextView(AppConstants.NUM_OF_CHECKED_VIEWS.TWO, profileBinding.ctFemale, profileBinding.ctMale, null, null);
                    mGenderSel = AppConstants.GENDER.FEMALE;
                }
                break;
        }
    }
}
