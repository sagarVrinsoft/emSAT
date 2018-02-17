package com.vrinsoft.emsat.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.vrinsoft.emsat.R;


public class SystemPermissionsUtils {
    public static final int REQUEST_PERMISSION_SETTING = 1000;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1001;
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1005;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1006;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1002;
    public static final int MY_PERMISSIONS_CAMERA = 1003;
    public static final int MY_PERMISSIONS_STORAGE = 1004;
    public static Snackbar snackbar;
    static String[] PERMISSIONS_LOCATION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    public static void openDeviceSettings(Activity context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
    }

    public static void setUpCall(final Activity context, String cellNo) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + cellNo));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(callIntent);
    }

    public static boolean checkReadContactsPermission(final Activity context , final View view) {
        boolean isPermissionGranted = false;
        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Pref.setNeverAskAgainForReadContacts(context, true);
                if (snackbar != null && snackbar.isShownOrQueued())
                    snackbar.dismiss();

                snackbar = Snackbar.make(view, R.string.permission_read_contacts_rationale,
                        Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                        ActivityCompat.requestPermissions(context,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    }
                })
                        .show();
            } else {
                if (Pref.isNeverAskAgainForReadContacts(context, false)) {
                    if (snackbar != null && snackbar.isShownOrQueued())
                        snackbar.dismiss();

                    snackbar = Snackbar.make(view, R.string.permission_read_contacts_settings,
                            Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                            openDeviceSettings(context);
                        }
                    })
                            .show();
                } else {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }
        }} else {
            isPermissionGranted = true;
        }
        return isPermissionGranted;
    }

    public static boolean checkCallPermissions(final Activity context, String cellNo, final View view) {
        boolean isPermissionGranted = false;
        if (cellNo != null && !TextUtils.isEmpty(cellNo)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                        Manifest.permission.CALL_PHONE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Pref.setNeverAskAgainForContact(context, true);
                    if (snackbar != null && snackbar.isShownOrQueued())
                        snackbar.dismiss();
                    if(context.getCurrentFocus() != null || view != null)
                    {
                        snackbar = Snackbar.make(view==null?context.getCurrentFocus():view,
                                R.string.permission_contacts_rationale,
                                Snackbar.LENGTH_SHORT);
                        snackbar.setAction(R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(context,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                            }
                        })
                                .show();
                    }

                } else {
                    if (Pref.isNeverAskAgainForContact(context, false)) {
                        if (snackbar != null && snackbar.isShownOrQueued())
                            snackbar.dismiss();

                        if(context.getCurrentFocus() != null || view != null) {
                            snackbar = Snackbar.make(view==null?context.getCurrentFocus():view, R.string.permission_contacts_settings,
                                    Snackbar.LENGTH_SHORT);
                            snackbar.setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    openDeviceSettings(context);
                                }
                            })
                                    .show();
                        }
                    } else {
                        ActivityCompat.requestPermissions(context,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }
                }
            } else {
                isPermissionGranted = true;
            }
        }
        return isPermissionGranted;
    }

    public static boolean checkCameraStoragePermission(final Activity context, View view) {

        boolean isCameraPermissionGranted = false;
        boolean isPermissionGranted = false;

        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                Pref.setNeverAskAgainForCamera(context, true);
                if (snackbar != null && snackbar.isShownOrQueued())
                    snackbar.dismiss();
                snackbar = Snackbar.make(view, R.string.permission_storage_rationale,
                        Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                        ActivityCompat.requestPermissions(context,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_CAMERA);
                    }
                })
                        .show();
            } else {
                if (Pref.isNeverAskAgainForStorage(context, false)) {
                    if (snackbar != null && snackbar.isShownOrQueued())
                        snackbar.dismiss();

                    snackbar = Snackbar.make(view, R.string.permission_camera_settings,
                            Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                            openDeviceSettings(context);
                        }
                    })
                            .show();
                } else {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_CAMERA);
                }
            }
        } else {
            isPermissionGranted = true;
            if (isPermissionGranted) {
                if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(context,
                            Manifest.permission.CAMERA))) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        Pref.setNeverAskAgainForCamera(context, true);
                        if (snackbar != null && snackbar.isShownOrQueued())
                            snackbar.dismiss();
                        snackbar = Snackbar.make(view, R.string.permission_camera_rationale,
                                Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction(R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_CAMERA);
                            }
                        })
                                .show();
                    } else if (Pref.isNeverAskAgainForCamera(context, false)) {
                        if (snackbar != null && snackbar.isShownOrQueued())
                            snackbar.dismiss();

                        snackbar = Snackbar.make(view, R.string.permission_camera_settings,
                                Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction(R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                                openDeviceSettings(context);
                            }
                        })
                                .show();
                    } else {
                        ActivityCompat.requestPermissions(context,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_CAMERA);
                    }
                } else {
                    isCameraPermissionGranted = true;
                }
            }
        }
        return isCameraPermissionGranted;
    }

    public static boolean checkStoragePermission(final Activity context, View view) {
        boolean isPermissionGranted = false;
        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                Pref.setNeverAskAgainForStorage(context, true);
                if (snackbar != null && snackbar.isShownOrQueued())
                    snackbar.dismiss();
                snackbar = Snackbar.make(view, R.string.permission_storage_rationale,
                        Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                        ActivityCompat.requestPermissions(context,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_STORAGE);
                    }
                })
                        .show();
            } else {
                if (Pref.isNeverAskAgainForStorage(context, false)) {
                    if (snackbar != null && snackbar.isShownOrQueued())
                        snackbar.dismiss();

                    snackbar = Snackbar.make(view, R.string.permission_storage_settings,
                            Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                            openDeviceSettings(context);
                        }
                    })
                            .show();
                } else {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_STORAGE);
                }
            }
        } else {
            isPermissionGranted = true;
        }
        return isPermissionGranted;
    }

}
