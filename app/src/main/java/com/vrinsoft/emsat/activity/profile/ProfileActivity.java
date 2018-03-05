package com.vrinsoft.emsat.activity.profile;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
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
import com.vrinsoft.emsat.databinding.ActivityProfileBinding;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.Validator;
import com.vrinsoft.emsat.utils.ViewUtils;

import java.util.ArrayList;

import static com.vrinsoft.emsat.utils.Validator.checkValidation;
import static com.vrinsoft.emsat.utils.ViewUtils.getEtBackground;
import static com.vrinsoft.emsat.utils.ViewUtils.setTextInputLayout;

/**
 * Created by komal on 19/2/18.
 */

public class ProfileActivity extends MasterActivity {
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

        profileBinding.rlChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();
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
        masterBinding.toolbar.imgHome.setVisibility(View.VISIBLE);
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

        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (changePasswordValidation()) {
                    ViewUtils.showDialog(mActivity, false);
                    new ChangePasswordApiHandler().changePassword(Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_USER_ID, ""),
                            Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_TOKEN, ""), etPassword.getText().toString().trim(),
                            etConfirmPassword.getText().toString().trim(), new OnChangePassword() {
                                @Override
                                public void getResponse(boolean isSuccess, ArrayList<BeanChangePassword> beanChangePassword, String errorMsgSystem) {
                                    ViewUtils.showDialog(mActivity, true);
                                    if (isSuccess) {
                                        if (beanChangePassword.get(0).getCode() == AppConstants.API_CODE_RESPONSE_SUCCESS) {
                                            ViewUtils.showToast(mActivity, LanguageUtils.getInstance().getValidatedLabel(
                                                    binLabels.getUpdate_successfully(),
                                                    getString(R.string.update_successfully)), null);
                                            dialog.dismiss();
                                            signOut();
                                        } else {
                                            ViewUtils.showToast(mActivity, beanChangePassword.get(0).getMessage(), null);
                                        }
                                    } else {
                                        ViewUtils.showToast(mActivity, errorMsgSystem, null);
                                    }
                                }
                            });
                }*/
                dialog.dismiss();
            }
        });

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
    }

    private boolean changePasswordValidation() {
        if (checkValidation(mActivity, etPassword,
                getString(R.string.please_enter_password))) {
            return false;
        } else if (etPassword.getText().toString().length() < 8) {
            ViewUtils.showToast(mActivity, getString(R.string.old_password_must_contains_atleast_8_characters), null);
            return false;
        } else if (!Validator.isNullEmpty(Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_PASSWORD, "")) &&
                !(etPassword.getText().toString().trim().equals(Pref.getValue(mActivity, AppPreference.USER_INFO.PREF_PASSWORD, "")))) {
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
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
