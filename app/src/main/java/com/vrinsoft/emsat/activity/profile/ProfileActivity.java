package com.vrinsoft.emsat.activity.profile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vrinsoft.emsat.BuildConfig;
import com.vrinsoft.emsat.MasterActivity;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.apis.model.change_password.BeanChangePassword;
import com.vrinsoft.emsat.apis.model.user_profile.update_profile.BeanUpdateProfile;
import com.vrinsoft.emsat.apis.model.user_profile.view_profile.BeanViewProfile;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.apis.rest.NetworkConstants;
import com.vrinsoft.emsat.databinding.ActivityProfileBinding;
import com.vrinsoft.emsat.utils.AppConstants;
import com.vrinsoft.emsat.utils.AppPreference;
import com.vrinsoft.emsat.utils.ImageUtils;
import com.vrinsoft.emsat.utils.Pref;
import com.vrinsoft.emsat.utils.SystemPermissionsUtils;
import com.vrinsoft.emsat.utils.Validator;
import com.vrinsoft.emsat.utils.ViewUtils;
import com.vrinsoft.emsat.utils.file_chooser.FileUtils;

import java.io.File;
import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;
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
    public static final int CAMERA_PIC_REQUEST = 1000;
    private static final int LIBRARY_REQUEST = 1400;
    public static String IMAGE_NAME = "";
    ActivityProfileBinding profileBinding;
    Activity mActivity;
    TextView txtPassword, txtNewPassword, txtConfirmPassword, txtChangePassword, txtUpdate;
    LinearLayout llPassword, llNewPassword, llConfirmPassword;
    View vPassword, vNewPassword, vConfirmPassword;
    EditText etPassword, etNewPassword, etConfirmPassword;
    ImageView imgClose;
    String selectedImagePath;
    Uri photoURI;
    int mGenderSel = AppConstants.GENDER.MALE;
    View v;
    boolean isFromGallery;
    private File finalFile;

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
        profileBinding.imgTop.setOnClickListener(this);
        profileBinding.txtSubmit.setOnClickListener(this);
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
                    if (mArrayList.size() > 0) {
                        profileBinding.etFN.setText(mArrayList.get(0).getName());
                        profileBinding.etMobileNo.setText(mArrayList.get(0).getMobileNo());
                        profileBinding.etEmail.setText(mArrayList.get(0).getEmail());
                        profileBinding.txtDOB.setText(mArrayList.get(0).getDob());
                        boolean isMale = mArrayList.get(0).getGender().equalsIgnoreCase(AppConstants.GENDER.MALE_str);
                        setGender(isMale);
                    } else {
                        ViewUtils.showToast(mActivity, getString(R.string.no_data_found), null);
                    }
                } else {
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
            public void onClick(View v) {
                if (changePasswordValidation()) {
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
                            if (beanChangePassword.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {
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

    private void setGender(boolean isMale) {
        mGenderSel = isMale ? AppConstants.GENDER.MALE : AppConstants.GENDER.FEMALE;
        profileBinding.ctMale.setChecked(isMale);
        profileBinding.ctFemale.setChecked(!isMale);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.txtSubmit:
                callApiToUpdateProfile();
                break;
            case R.id.imgTop:
                alertPhoto(v);
                break;
        }
    }

    public void alertPhoto(final View view) {
        v = view;
        final CharSequence[] items = {
                getString(R.string.take_photo),
                getString(R.string.choose_from_library),
                getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(getString(R.string.select_image));
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    isFromGallery = false;
                    if (SystemPermissionsUtils.checkCameraStoragePermission(mActivity, view)) {
                        cameraIntent();
                    }
                } else if (items[item].equals(getString(R.string.choose_from_library))) {
                    isFromGallery = true;
                    if (SystemPermissionsUtils.checkStoragePermission(mActivity, view)) {
                        galleryIntent();
                    }
                } else if (items[item].equals(
                        getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), LIBRARY_REQUEST);
    }

    private void cameraIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            IMAGE_NAME = System.currentTimeMillis() + ".jpg";
            File photoFile = FileUtils.createImageFile(IMAGE_NAME);
            if (Build.VERSION.SDK_INT >= 24) {
                photoURI = FileProvider.getUriForFile(mActivity,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
            } else {
                photoURI = Uri.fromFile(FileUtils.createImageFile(IMAGE_NAME));
            }
            selectedImagePath = photoFile.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            switch (permissions[i]) {
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    if (SystemPermissionsUtils.checkStoragePermission(mActivity, v)) {
                        if (isFromGallery) {
                            galleryIntent();
                        } else {
                            SystemPermissionsUtils.checkCameraStoragePermission(mActivity, v);
                        }
                    }
                    break;
                case Manifest.permission.CAMERA:
                    if (SystemPermissionsUtils.checkCameraStoragePermission(mActivity, v)) {
                        cameraIntent();
                    }
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LIBRARY_REQUEST)
                onSelectFromGalleryResult(data);
            else if (requestCode == CAMERA_PIC_REQUEST)
                onCaptureImageResult(data);
        } else {
            photoURI = null;// prevent from crashing while canceling capture feature by uploading wrong URI
        }
    }

    private void onCaptureImageResult(Intent data) {

        photoURI = ImageUtils.getURIProfileImage(mActivity,
                IMAGE_NAME,
                photoURI,
                profileBinding.imgTop);
        ImageUtils.loadProfileImageLocal
                (mActivity, photoURI, profileBinding.imgTop, profileBinding.mProgress);

        ImageUtils.loadBigBlurImage
                (mActivity, R.drawable.bg_profile_top_banner, photoURI,
                        profileBinding.imgBlurred, profileBinding.mProgress);

        finalFile = new File(photoURI.getPath());
        if (finalFile != null) {
            // do upload file logic here
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        final Uri uri = data.getData();
        IMAGE_NAME = System.currentTimeMillis() + ".jpg";
        // here assigning value to photoURI is imporatant bcoz this uri will
        // use to upload image in Multipart Entity.
        photoURI = ImageUtils.getURIProfileImage(mActivity,
                IMAGE_NAME,
                uri,
                profileBinding.imgTop);

        ImageUtils.loadProfileImageLocal
                (mActivity, photoURI, profileBinding.imgTop, profileBinding.mProgress);

        ImageUtils.loadBigBlurImage
                (mActivity, R.drawable.bg_profile_top_banner, photoURI,
                        profileBinding.imgBlurred, profileBinding.mProgress);

        finalFile = new File(photoURI.getPath());

        if (finalFile != null) {
            // do upload file logic here
        }
    }

    private void callApiToUpdateProfile() {
        ViewUtils.showDialog(mActivity, false);
        String name = profileBinding.etFN.getText().toString().trim();
        String mobile = profileBinding.etMobileNo.getText().toString().trim();
        String email = profileBinding.etEmail.getText().toString().trim();
        String dob = profileBinding.txtDOB.getText().toString().trim();
        int gender = mGenderSel;

        Call<ArrayList<BeanUpdateProfile>> listCall =
                ApiClient.getApiInterface().updateProfile
                        (Pref.getUserId(mActivity),
                                name,
                                mobile,
                                email,
                                dob,
                                Pref.getProfileImage(mActivity),
                                gender,
                                Pref.getToken(mActivity));

        listCall.enqueue(new Callback<ArrayList<BeanUpdateProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<BeanUpdateProfile>> call, Response<ArrayList<BeanUpdateProfile>> response) {
                ArrayList<BeanUpdateProfile> beanUpdateProfile = response.body();

                ViewUtils.showDialog(mActivity, true);
                if (beanUpdateProfile.get(0).getCode() == NetworkConstants.API_CODE_RESPONSE_SUCCESS) {
                    ViewUtils.showToast(mActivity, beanUpdateProfile.get(0).getMessage(), null);
                    finish();
                } else {
                    ViewUtils.showToast(mActivity, beanUpdateProfile.get(0).getMessage(), null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BeanUpdateProfile>> call, Throwable t) {
                ViewUtils.showDialog(mActivity, true);
                ViewUtils.showToast(mActivity, ApiErrorUtils.getErrorMsg(t), null);
            }
        });
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
}
