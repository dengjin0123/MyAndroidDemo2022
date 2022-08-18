package com.xiangke.myutil;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xiangke.common.R;
import com.xiangke.utilcode.Utils;

import java.io.File;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class GlideUtil {

    public static void glideLoadImage(ImageView iv, String imageUrl) {
        Glide.with(Utils.getApp()).load(imageUrl).into(iv);
    }
    public static void glideLoadImage(ImageView iv, String imageUrl,int resourceId) {
        Glide.with(Utils.getApp())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(resourceId)
                .error(resourceId)
                .into(iv);
    }

    /**
     * 占位图和网络图比例不一致的时候做的处理
     * @param iv
     * @param imageUrl
     * @param resourceId
     */
    public static void glideLoadDifferSizeImage(final ImageView iv, String imageUrl, int resourceId){
        Glide.with(Utils.getApp())
                .load(imageUrl)
//                .placeholder(resourceId)
                .error(resourceId)
                .into(iv);
    }

    public static void glideLoadImageCircle(final ImageView iv, byte[] imageUrl) {
        Glide.with(Utils.getApp())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<
                            ? super Drawable> transition) {
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {
                        iv.setImageDrawable(drawable);
                    }
                });
    }

    public static void glideLoadImageCircle(final ImageView iv, String imageUrl) {
        Glide.with(Utils.getApp())
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<
                            ? super Drawable> transition) {
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable drawable) {
                        iv.setImageDrawable(drawable);
                    }
                });
    }
    public static void glideLoadImageCircle(final ImageView iv, String imageUrl, int resourceId) {
        if (resourceId > 0) {
            Glide.with(Utils.getApp())
                    .load(imageUrl)
//                    .apply(RequestOptions.bitmapTransform(new CropCircleTransformation(Utils.getApp())))
                    .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(resourceId)
                    .error(resourceId)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<
                                ? super Drawable> transition) {
                            iv.setImageDrawable(drawable);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable drawable) {
                            iv.setImageDrawable(drawable);
                        }
                    });
        } else {
            Glide.with(Utils.getApp())
                    .load(imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<
                                ? super Drawable> transition) {
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable drawable) {
                            iv.setImageDrawable(drawable);
                        }
                    });
        }
    }

    public static void glideLoadImageRound(ImageView iv, String imageUrl,
                                           int roundSize) {
        File file = new File(Environment.getExternalStorageDirectory() + "/sqjw/image/");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (TextUtils.isEmpty(imageUrl)) {
            return;
        } else {
            Glide.with(Utils.getApp()).load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideRoundTransform(Utils.getApp(), roundSize))
                    .into(iv);
        }
    }

    public static void glideLoadImageRoundNew(ImageView iv, String imageUrl, int roundSize) {
        GlideRoundTransform glideRoundTransform = new GlideRoundTransform(Utils.getApp(), roundSize);
        Glide.with(Utils.getApp())
                .asBitmap()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .transform(new CenterCrop(), glideRoundTransform)
                .into(iv);
    }

    public static void glideLoadImageRoundNew(ImageView iv, String imageUrl, int resourceId, int roundSize) {
        if (resourceId > 0) {
            GlideRoundTransform glideRoundTransform = new GlideRoundTransform(Utils.getApp(), roundSize);
            Glide.with(Utils.getApp())
                    .asBitmap()
                    .load(imageUrl)
                    .placeholder(resourceId)
                    .error(resourceId)
                    .transform(new CenterCrop(), glideRoundTransform)
                    .into(iv);
        } else {
            GlideRoundTransform glideRoundTransform = new GlideRoundTransform(Utils.getApp(), roundSize);
            Glide.with(Utils.getApp())
                    .asBitmap()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .transform(new CenterCrop(), glideRoundTransform)
                    .into(iv);
        }

    }

    public static void glideLoadLocalImage(ImageView iv, String path) {
        File file = new File(path);
        Glide.with(Utils.getApp()).load(file).into(iv);
    }

    public static void glideLoadLocalImageByFile(ImageView iv, File file) {
        Glide.with(Utils.getApp()).load(file).into(iv);
    }
}
