package com.xiangke.helper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.basic.IBridgeViewLifecycle;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.InjectResourceSource;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.config.SelectLimitType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener;
import com.luck.picture.lib.interfaces.OnInjectLayoutResourceListener;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnMediaEditInterceptListener;
import com.luck.picture.lib.interfaces.OnQueryFilterListener;
import com.luck.picture.lib.interfaces.OnRecordAudioInterceptListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.interfaces.OnSelectLimitTipsListener;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.permissions.PermissionConfig;
import com.luck.picture.lib.permissions.PermissionResultCallback;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.PictureFileUtils;
import com.luck.picture.lib.utils.SandboxTransformUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.luck.picture.lib.utils.ToastUtils;
import com.luck.picture.lib.widget.MediumBoldTextView;
import com.xiangke.callback.ComCallback;
import com.xiangke.common.R;
import com.xiangke.image.GridImageAdapter;
import com.xiangke.image.ImageLoaderUtils;
import com.xiangke.utilcode.ThreadUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import java.io.File;
import java.util.ArrayList;

/**
 * @Date:2022/8/17 10:32
 * @Author:dengjin
 * @Name:
 */
public class PictureSelectorHelper {
    private static final String TAG = "PictureSelectorHelper";
    private final static String TAG_EXPLAIN_VIEW = "TAG_EXPLAIN_VIEW";

    private static int aspect_ratio_x = -1, aspect_ratio_y = -1;

    private final static int ACTIVITY_RESULT = 1;
    private final static int CALLBACK_RESULT = 2;
    private final static int LAUNCHER_RESULT = 3;

    private static int resultMode = LAUNCHER_RESULT;

    private PictureSelectorHelper(){}

    public static void createPictureSelect(AppCompatActivity activity,
                                           ImageEngine imageEngine,
                                           RecyclerView recyclerView,
                                           OnExternalPreviewEventListener eventListener,
                                           GridImageAdapter adapter, int position) {
        // 预览图片、视频、音频
        PictureSelector.create(activity)
                .openPreview()
                .setImageEngine(imageEngine)
                .setVideoPlayerEngine(null)
                .setSelectorUIStyle(new PictureSelectorStyle())
                .setLanguage(LanguageConfig.UNKNOWN_LANGUAGE)
                .isAutoVideoPlay(false)
                .isLoopAutoVideoPlay(false)
                .isPreviewFullScreenMode(true)
                .isVideoPauseResumePlay(false)
                .isPreviewZoomEffect(true, recyclerView)
                .setAttachViewLifecycle(new IBridgeViewLifecycle() {
                    @Override
                    public void onViewCreated(Fragment fragment, View view, Bundle savedInstanceState) {}

                    @Override
                    public void onDestroy(Fragment fragment) {}
                })
                .setInjectLayoutResourceListener(new OnInjectLayoutResourceListener() {
                    @Override
                    public int getLayoutResourceId(Context context, int resourceSource) {
                        return resourceSource == InjectResourceSource.PREVIEW_LAYOUT_RESOURCE
                                ? R.layout.ps_custom_fragment_preview
                                : InjectResourceSource.DEFAULT_LAYOUT_RESOURCE;
                    }
                })
                .setExternalPreviewEventListener(eventListener)
//                        .setInjectActivityPreviewFragment(new OnInjectActivityPreviewListener() {
//                            @Override
//                            public PictureSelectorPreviewFragment onInjectPreviewFragment() {
//                                return null;
//                            }
//                        })
                .startActivityPreview(position, true, adapter.getData());
    }

    public static void openPicture(Context context, ImageEngine imageEngine,
                                   int maxSelectNum, int maxSelectVideoNum,
                                   GridImageAdapter adapter, ComCallback callback) {
        PictureSelectionModel selectionModel = PictureSelector.create(context)
                .openGallery(SelectMimeType.ofAll())
                .setSelectorUIStyle(new PictureSelectorStyle())
                .setImageEngine(imageEngine)
                .setVideoPlayerEngine(null)
//                        .setCropEngine(getCropFileEngine())
//                        .setCompressEngine(getCompressFileEngine())
                .setSandboxFileEngine(new MeSandboxFileEngine())
//                        .setCameraInterceptListener(getCustomCameraEvent())
                .setRecordAudioInterceptListener(new MeOnRecordAudioInterceptListener())
                .setSelectLimitTipsListener(new MeOnSelectLimitTipsListener())
                .setEditMediaInterceptListener(getCustomEditMediaEvent(context, new PictureSelectorStyle()))
//                        .setPermissionDescriptionListener(getPermissionDescriptionListener())
//                        .setPreviewInterceptListener(getPreviewInterceptListener())
//                        .setPermissionDeniedListener(getPermissionDeniedListener())// 权限拒绝后回调
//                        .setAddBitmapWatermarkListener(getAddBitmapWatermarkListener())//增加水印
//                        .setVideoThumbnailListener(getVideoThumbnailEventListener())//视频缩略图
//                        .isAutoVideoPlay(cb_auto_video.isChecked())//预览视频自动播放
//                        .isLoopAutoVideoPlay(cb_auto_video.isChecked())
                .isPageSyncAlbumCount(true)
                .setQueryFilterListener(new OnQueryFilterListener() {
                    @Override
                    public boolean onFilter(LocalMedia media) {
                        return false;
                    }
                })
                //.setExtendLoaderEngine(getExtendLoaderEngine())
//                        .setInjectLayoutResourceListener(getInjectLayoutResource())
                .setSelectionMode(SelectModeConfig.MULTIPLE)
                .setLanguage(LanguageConfig.UNKNOWN_LANGUAGE)
                .setQuerySortOrder("")//降序或者升序查询
                .setOutputCameraDir("")
                .setOutputAudioDir("")
                .setQuerySandboxDir("")
                .isDisplayTimeAxis(true)// 显示资源时间轴
                .isOnlyObtainSandboxDir(false)
                .isPageStrategy(true)
                .isOriginalControl(false)
                .isDisplayCamera(true)
                .isOpenClickSound(false)
//                        .setSkipCropMimeType(getNotSupportCrop())
                .isFastSlidingSelect(true)
                //.setOutputCameraImageFileName("luck.jpeg")
                //.setOutputCameraVideoFileName("luck.mp4")
                .isWithSelectVideoImage(true)
                .isPreviewFullScreenMode(true)
                .isVideoPauseResumePlay(false)
                .isPreviewZoomEffect(true)
                .isPreviewImage(true)
                .isPreviewVideo(true)
                .isPreviewAudio(true)
                .setGridItemSelectAnimListener(null)
                .setSelectAnimListener(null)
                //.setQueryOnlyMimeType(PictureMimeType.ofGIF())
                .isMaxSelectEnabledMask(true)
                .isDirectReturnSingle(false)
                .setMaxSelectNum(maxSelectNum)
                .setMaxVideoSelectNum(maxSelectVideoNum)
                .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)
                .isGif(false)
                .setSelectedData(adapter.getData());
        forSelectResult(selectionModel, context, adapter, callback);
    }

    private static void forSelectResult(PictureSelectionModel model, Context context,
                                        GridImageAdapter adapter, ComCallback callback) {
        switch (resultMode) {
            case ACTIVITY_RESULT:
                model.forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case CALLBACK_RESULT:
                model.forResult(new MeOnResultCallbackListener(context, adapter));
                break;
            default:
                callback.onResult(0, "forResult", model);
                break;
        }
    }

    /**
     * 选择结果
     */
    private static class MeOnResultCallbackListener implements OnResultCallbackListener<LocalMedia> {
        private Context mContext;
        private GridImageAdapter mAdapter;
        public MeOnResultCallbackListener(Context context, GridImageAdapter adapter){
            this.mContext = context;
            this.mAdapter = adapter;
        }
        @Override
        public void onResult(ArrayList<LocalMedia> result) {
            analyticalSelectResults(result, mContext, mAdapter);
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "PictureSelector Cancel");
        }
    }

    /**
     * 处理选择结果
     *
     * @param result
     */
    public static void analyticalSelectResults(ArrayList<LocalMedia> result, Context context, GridImageAdapter adapter) {
        for (LocalMedia media : result) {
            if (media.getWidth() == 0 || media.getHeight() == 0) {
                if (PictureMimeType.isHasImage(media.getMimeType())) {
                    MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(context, media.getPath());
                    media.setWidth(imageExtraInfo.getWidth());
                    media.setHeight(imageExtraInfo.getHeight());
                } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
                    MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(context, media.getPath());
                    media.setWidth(videoExtraInfo.getWidth());
                    media.setHeight(videoExtraInfo.getHeight());
                }
            }
            Log.i(TAG, "文件名: " + media.getFileName());
            Log.i(TAG, "是否压缩:" + media.isCompressed());
            Log.i(TAG, "压缩:" + media.getCompressPath());
            Log.i(TAG, "初始路径:" + media.getPath());
            Log.i(TAG, "绝对路径:" + media.getRealPath());
            Log.i(TAG, "是否裁剪:" + media.isCut());
            Log.i(TAG, "裁剪路径:" + media.getCutPath());
            Log.i(TAG, "是否开启原图:" + media.isOriginal());
            Log.i(TAG, "原图路径:" + media.getOriginalPath());
            Log.i(TAG, "沙盒路径:" + media.getSandboxPath());
            Log.i(TAG, "水印路径:" + media.getWatermarkPath());
            Log.i(TAG, "视频缩略图:" + media.getVideoThumbnailPath());
            Log.i(TAG, "原始宽高: " + media.getWidth() + "x" + media.getHeight());
            Log.i(TAG, "裁剪宽高: " + media.getCropImageWidth() + "x" + media.getCropImageHeight());
            Log.i(TAG, "文件大小: " + PictureFileUtils.formatAccurateUnitFileSize(media.getSize()));
            Log.i(TAG, "文件时长: " + media.getDuration());
        }
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean isMaxSize = result.size() == adapter.getSelectMax();
                int oldSize = adapter.getData().size();
                adapter.notifyItemRangeRemoved(0, isMaxSize ? oldSize + 1 : oldSize);
                adapter.getData().clear();

                adapter.getData().addAll(result);
                adapter.notifyItemRangeInserted(0, result.size());
            }
        });
    }

    /**
     * 自定义沙盒文件处理
     */
    private static class MeSandboxFileEngine implements UriToFileTransformEngine {

        @Override
        public void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call) {
            if (call != null) {
                call.onCallback(srcPath, SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType));
            }
        }
    }

    /**
     * 录音回调事件
     */
    private static class MeOnRecordAudioInterceptListener implements OnRecordAudioInterceptListener {

        @Override
        public void onRecordAudio(Fragment fragment, int requestCode) {
            String[] recordAudio = {Manifest.permission.RECORD_AUDIO};
            if (PermissionChecker.isCheckSelfPermission(fragment.getContext(), recordAudio)) {
                startRecordSoundAction(fragment, requestCode);
            } else {
                addPermissionDescription(false, (ViewGroup) fragment.requireView(), recordAudio);
                PermissionChecker.getInstance().requestPermissions(fragment,
                        new String[]{Manifest.permission.RECORD_AUDIO}, new PermissionResultCallback() {
                            @Override
                            public void onGranted() {
                                removePermissionDescription((ViewGroup) fragment.requireView());
                                startRecordSoundAction(fragment, requestCode);
                            }

                            @Override
                            public void onDenied() {
                                removePermissionDescription((ViewGroup) fragment.requireView());
                            }
                        });
            }
        }
    }

    /**
     * 启动录音意图
     *
     * @param fragment
     * @param requestCode
     */
    private static void startRecordSoundAction(Fragment fragment, int requestCode) {
        Intent recordAudioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        if (recordAudioIntent.resolveActivity(fragment.requireActivity().getPackageManager()) != null) {
            fragment.startActivityForResult(recordAudioIntent, requestCode);
        } else {
            ToastUtils.showToast(fragment.getContext(), "The system is missing a recording component");
        }
    }

    /**
     * 添加权限说明
     *
     * @param viewGroup
     * @param permissionArray
     */
    private static void addPermissionDescription(boolean isHasSimpleXCamera, ViewGroup viewGroup, String[] permissionArray) {
        int dp10 = DensityUtil.dip2px(viewGroup.getContext(), 10);
        int dp15 = DensityUtil.dip2px(viewGroup.getContext(), 15);
        MediumBoldTextView view = new MediumBoldTextView(viewGroup.getContext());
        view.setTag(TAG_EXPLAIN_VIEW);
        view.setTextSize(14);
        view.setTextColor(Color.parseColor("#333333"));
        view.setPadding(dp10, dp15, dp10, dp15);

        String title;
        String explain;

        if (TextUtils.equals(permissionArray[0], PermissionConfig.CAMERA[0])) {
            title = "相机权限使用说明";
            explain = "相机权限使用说明\n用户app用于拍照/录视频";
        } else if (TextUtils.equals(permissionArray[0], Manifest.permission.RECORD_AUDIO)) {
            if (isHasSimpleXCamera) {
                title = "麦克风权限使用说明";
                explain = "麦克风权限使用说明\n用户app用于录视频时采集声音";
            } else {
                title = "录音权限使用说明";
                explain = "录音权限使用说明\n用户app用于采集声音";
            }
        } else {
            title = "存储权限使用说明";
            explain = "存储权限使用说明\n用户app写入/下载/保存/读取/修改/删除图片、视频、文件等信息";
        }
        int startIndex = 0;
        int endOf = startIndex + title.length();
        SpannableStringBuilder builder = new SpannableStringBuilder(explain);
        builder.setSpan(new AbsoluteSizeSpan(DensityUtil.dip2px(viewGroup.getContext(), 16)), startIndex, endOf, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(0xFF333333), startIndex, endOf, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        view.setText(builder);
        view.setBackground(ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.ps_demo_permission_desc_bg));

        if (isHasSimpleXCamera) {
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = DensityUtil.getStatusBarHeight(viewGroup.getContext());
            layoutParams.leftMargin = dp10;
            layoutParams.rightMargin = dp10;
            viewGroup.addView(view, layoutParams);
        } else {
            ConstraintLayout.LayoutParams layoutParams =
                    new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.topToBottom = R.id.title_bar;
            layoutParams.leftToLeft = ConstraintSet.PARENT_ID;
            layoutParams.leftMargin = dp10;
            layoutParams.rightMargin = dp10;
            viewGroup.addView(view, layoutParams);
        }
    }

    /**
     * 移除权限说明
     *
     * @param viewGroup
     */
    private static void removePermissionDescription(ViewGroup viewGroup) {
        View tagExplainView = viewGroup.findViewWithTag(TAG_EXPLAIN_VIEW);
        viewGroup.removeView(tagExplainView);
    }

    /**
     * 拦截自定义提示
     */
    private static class MeOnSelectLimitTipsListener implements OnSelectLimitTipsListener {

        @Override
        public boolean onSelectLimitTips(Context context, PictureSelectionConfig config, int limitType) {
            if (limitType == SelectLimitType.SELECT_NOT_SUPPORT_SELECT_LIMIT) {
                ToastUtils.showToast(context, "暂不支持的选择类型");
                return true;
            }
            return false;
        }
    }

    /**
     * 自定义编辑事件
     *
     * @return
     */
    private static OnMediaEditInterceptListener getCustomEditMediaEvent(Context context, PictureSelectorStyle selectorStyle) {
        return new MeOnMediaEditInterceptListener(getSandboxPath(context), buildOptions(context, selectorStyle));
    }

    /**
     * 自定义编辑
     */
    private static class MeOnMediaEditInterceptListener implements OnMediaEditInterceptListener {
        private final String outputCropPath;
        private final UCrop.Options options;

        public MeOnMediaEditInterceptListener(String outputCropPath, UCrop.Options options) {
            this.outputCropPath = outputCropPath;
            this.options = options;
        }

        @Override
        public void onStartMediaEdit(Fragment fragment, LocalMedia currentLocalMedia, int requestCode) {
            String currentEditPath = currentLocalMedia.getAvailablePath();
            Uri inputUri = PictureMimeType.isContent(currentEditPath)
                    ? Uri.parse(currentEditPath) : Uri.fromFile(new File(currentEditPath));
            Uri destinationUri = Uri.fromFile(
                    new File(outputCropPath, DateUtils.getCreateFileName("CROP_") + ".jpeg"));
            UCrop uCrop = UCrop.of(inputUri, destinationUri);
            options.setHideBottomControls(false);
            uCrop.withOptions(options);
            uCrop.setImageEngine(new UCropImageEngine() {
                @Override
                public void loadImage(Context context, String url, ImageView imageView) {
                    if (!ImageLoaderUtils.assertValidRequest(context)) {
                        return;
                    }
                    Glide.with(context).load(url).override(180, 180).into(imageView);
                }

                @Override
                public void loadImage(Context context, Uri url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {
                    Glide.with(context).asBitmap().load(url).override(maxWidth, maxHeight).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (call != null) {
                                call.onCall(resource);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            if (call != null) {
                                call.onCall(null);
                            }
                        }
                    });
                }
            });
            uCrop.startEdit(fragment.requireActivity(), fragment, requestCode);
        }
    }

    /**
     * 创建自定义输出目录
     *
     * @return
     */
    private static String getSandboxPath(Context context) {
        File externalFilesDir = context.getExternalFilesDir("");
        File customFile = new File(externalFilesDir.getAbsolutePath(), "Sandbox");
        if (!customFile.exists()) {
            customFile.mkdirs();
        }
        return customFile.getAbsolutePath() + File.separator;
    }

    /**
     * 配制UCrop，可根据需求自我扩展
     *
     * @return
     */
    private static UCrop.Options buildOptions(Context context, PictureSelectorStyle selectorStyle) {
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(false);
        options.setShowCropFrame(false);
        options.setShowCropGrid(false);
        options.setCircleDimmedLayer(false);
        options.withAspectRatio(aspect_ratio_x, aspect_ratio_y);
        options.setCropOutputPathDir(getSandboxPath(context));
        options.isCropDragSmoothToCenter(false);
//        options.setSkipCropMimeType(getNotSupportCrop());
        options.isForbidCropGifWebp(false);
        options.isForbidSkipMultipleCrop(false);
        options.setMaxScaleMultiplier(100);
        if (selectorStyle != null && selectorStyle.getSelectMainStyle().getStatusBarColor() != 0) {
            SelectMainStyle mainStyle = selectorStyle.getSelectMainStyle();
            boolean isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack();
            int statusBarColor = mainStyle.getStatusBarColor();
            options.isDarkStatusBarBlack(isDarkStatusBarBlack);
            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                options.setStatusBarColor(statusBarColor);
                options.setToolbarColor(statusBarColor);
            } else {
                options.setStatusBarColor(ContextCompat.getColor(context, R.color.ps_color_grey));
                options.setToolbarColor(ContextCompat.getColor(context, R.color.ps_color_grey));
            }
            TitleBarStyle titleBarStyle = selectorStyle.getTitleBarStyle();
            if (StyleUtils.checkStyleValidity(titleBarStyle.getTitleTextColor())) {
                options.setToolbarWidgetColor(titleBarStyle.getTitleTextColor());
            } else {
                options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.ps_color_white));
            }
        } else {
            options.setStatusBarColor(ContextCompat.getColor(context, R.color.ps_color_grey));
            options.setToolbarColor(ContextCompat.getColor(context, R.color.ps_color_grey));
            options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.ps_color_white));
        }
        return options;
    }
}
