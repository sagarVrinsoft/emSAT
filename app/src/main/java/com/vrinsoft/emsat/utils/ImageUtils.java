package com.vrinsoft.emsat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vrinsoft.emsat.R;
import com.vrinsoft.emsat.utils.file_chooser.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ImageUtils {

    public static Bitmap getCircularBitmapImage(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        squaredBitmap.recycle();
        return bitmap;
    }

    public static File getReducedSizeFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static Uri getURIProfileImage(Context context, String IMAGE_NAME, Uri photoURI,
                                         ImageView imgProfile) {

        // Actual File
        File file = FileUtils.getFileForAndroid_O_Pattern(context, photoURI, IMAGE_NAME);
        // Reduced File
        File fileReduced = ImageUtils.getReducedSizeFile(file);

//        try {
//            Glide.with(context).
//                    load(photoURI).
//                    asBitmap().
//                    centerCrop().
//                    transform(new CircleTransform(context)).
//                    placeholder(R.drawable.chat_profile).
//                    into(imgProfile);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // Return Uri of reduced Image Size to Upload
        if (fileReduced != null)
            return Uri.fromFile(fileReduced);
        else
            return null;
    }

    public static void loadDriverImage(Context context, String url,
                                       ImageView imgProfile, int placeholder, final ProgressBar mProgress) {

        try {
            mProgress.setVisibility(View.VISIBLE);
            Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }
            }).placeholder(placeholder).crossFade().transform(new CircleTransform(context)).into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadProfileImage(Context context, String url,
                                        ImageView imgProfile, final ProgressBar mProgress) {
        try {

           // Glide.with(context).load(url).transform(new CircleTransform(context)).into(imgProfile);

            mProgress.setVisibility(View.VISIBLE);
            Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }
            }).placeholder(R.drawable.ic_user_profile).crossFade().transform(new CircleTransform(context)).into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadRideHistoryImage(Context context, String url,
                                            ImageView imgProfile, final ProgressBar mProgress) {
        try {

            mProgress.setVisibility(View.VISIBLE);
            Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }
            }).placeholder(R.drawable.ic_user_profile).transform(new CircleTransform(context)).crossFade().into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadDiscussionBoardImage(Context context, String url,
                                                ImageView imgProfile, final ProgressBar mProgress) {
        try {

            mProgress.setVisibility(View.VISIBLE);
            Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }
            }).placeholder(R.drawable.ic_user_profile).crossFade().transform(new CircleTransform(context)).into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadAcceptRejectImage(Context context, String url,
                                             ImageView imgProfile, final ProgressBar mProgress) {
        try {

            mProgress.setVisibility(View.VISIBLE);
            Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mProgress.setVisibility(View.GONE);
                    return false;
                }
            }).placeholder(R.drawable.ic_user_profile).crossFade().transform(new CircleTransform(context)).into(imgProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}