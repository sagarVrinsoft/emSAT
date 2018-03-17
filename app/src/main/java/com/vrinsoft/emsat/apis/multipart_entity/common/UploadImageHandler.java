package com.vrinsoft.emsat.apis.multipart_entity.common;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;


import com.vrinsoft.emsat.apis.model.user_profile.update_profile.BeanUpdateProfile;
import com.vrinsoft.emsat.apis.multipart_entity.profile.OnProfileUpdate;
import com.vrinsoft.emsat.apis.rest.ApiClient;
import com.vrinsoft.emsat.apis.rest.ApiErrorUtils;
import com.vrinsoft.emsat.utils.Validator;
import com.vrinsoft.emsat.utils.file_chooser.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class UploadImageHandler {

    MultipartBody.Part fileBody = null;

    public void requestProfileUpdate(Context context, String userId, String name,
                                     String mobile_number, String email,
                                     String dob, String token,String gender,
                                     final Uri fileUri,
                                     final OnProfileUpdate onProfileUpdate) {

        HashMap<String, RequestBody> mMapBodyPart = new HashMap<>();
        mMapBodyPart.put("user_id", createPartFromString(userId));
        mMapBodyPart.put("name", createPartFromString(name));
        mMapBodyPart.put("mobile_number", createPartFromString(mobile_number));
        mMapBodyPart.put("email", createPartFromString(email));
        mMapBodyPart.put("dob", createPartFromString(dob));
        mMapBodyPart.put("gender", createPartFromString(gender));
        mMapBodyPart.put("token", createPartFromString(token));

        if (fileUri != null && !Validator.isEmptyStr(fileUri.toString())) {
            fileBody = prepareFilePart(context, "image", fileUri);
        }

        Call<List<BeanUpdateProfile>> binFileUpload = ApiClient.getApiInterface()
                .updateProfile(mMapBodyPart, fileBody);

        binFileUpload.enqueue(new Callback<List<BeanUpdateProfile>>() {
            @Override
            public void onResponse(Call<List<BeanUpdateProfile>> call,
                                   Response<List<BeanUpdateProfile>> response) {

                List<BeanUpdateProfile> list = (List<BeanUpdateProfile>) response.body();
                BeanUpdateProfile binProfileUpdate = new BeanUpdateProfile();
                if (list != null && list.size() > 0) {
                    binProfileUpdate = list.get(0);
                }

                onProfileUpdate.getResponse(true, binProfileUpdate, null);
            }

            @Override
            public void onFailure(Call<List<BeanUpdateProfile>> call, Throwable t) {
                onProfileUpdate.getResponse(false, null, ApiErrorUtils.getErrorMsg(t));
            }
        });
    }
    @NonNull
    private MultipartBody.Part prepareFilePart(Context context, String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(context, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getMimeType(context, fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);
    }

    public String getMimeType(Context context, Uri uri) {
//        https://stackoverflow.com/a/31691791/2054348
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

}
