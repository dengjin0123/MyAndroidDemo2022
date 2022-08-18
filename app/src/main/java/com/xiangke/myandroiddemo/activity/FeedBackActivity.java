package com.xiangke.myandroiddemo.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

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
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.engine.VideoPlayerEngine;
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
import com.xiangke.base.mvp.BaseMvpActivity;
import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.callback.ComCallback;
import com.xiangke.helper.PictureSelectorHelper;
import com.xiangke.image.ImageLoaderUtils;
import com.xiangke.listener.DragListener;
import com.xiangke.myandroiddemo.R;
import com.xiangke.image.GlideEngine;
import com.xiangke.image.GridImageAdapter;
import com.xiangke.myandroiddemo.layoutmanager.FullyGridLayoutManager;
import com.xiangke.utilcode.LogUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Date:2021/11/08 11:21
 * @Author:dengjin
 * @Name: 意见反馈界面
 */
public class FeedBackActivity extends BaseMvpActivity {
    private final static String TAG = FeedBackActivity.class.getSimpleName();

    private GridImageAdapter mAdapter;
    private int maxSelectNum = 9;
    private int maxSelectVideoNum = 1;

    private final List<LocalMedia> mData = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcherResult;
    private ImageEngine imageEngine;
    private RecyclerView mRecyclerView;


    @Override
    protected boolean isAttachTitle() {
        return true;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        super.initView();
        titlePanel.setTitle("意见反馈");
        titlePanel.setBackView();

        // 注册需要写在onCreate或Fragment onAttach里，否则会报java.lang.IllegalStateException异常
        launcherResult = createActivityResultLauncher();

        mRecyclerView = (RecyclerView) findViewById(R.id.feedback_recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        RecyclerView.ItemAnimator itemAnimator = mRecyclerView.getItemAnimator();
        if (itemAnimator != null) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                DensityUtil.dip2px(this, 8), false));
        mAdapter = new GridImageAdapter(mContext, mData);
        mAdapter.setSelectMax(maxSelectNum + maxSelectVideoNum);
        mRecyclerView.setAdapter(mAdapter);

        imageEngine = GlideEngine.createGlideEngine();

        mAdapter.setOnItemClickListener(onItemClickListener);
    }

    private final GridImageAdapter.OnItemClickListener onItemClickListener = new GridImageAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            // 预览图片、视频、音频
            PictureSelectorHelper.createPictureSelect(FeedBackActivity.this,
                    imageEngine,
                    mRecyclerView,
                    new MyExternalPreviewEventListener(),
                    mAdapter,
                    position);
        }

        @Override
        public void openPicture() {
            // 进入相册
            PictureSelectorHelper.openPicture(mContext, imageEngine, maxSelectNum,
                maxSelectVideoNum, mAdapter, new ComCallback() {
                    @Override
                    public void onResult(int errorCode, String msg, Object object) {
                        if (object instanceof PictureSelectionModel) {
                            PictureSelectionModel model = (PictureSelectionModel)object;
                            model.forResult(launcherResult);
                        }
                    }
                });
        }
    };

    /**
     * 创建一个ActivityResultLauncher
     *
     * @return
     */
    private ActivityResultLauncher<Intent> createActivityResultLauncher() {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        if (resultCode == RESULT_OK) {
                            ArrayList<LocalMedia> selectList = PictureSelector.obtainSelectorList(result.getData());
                            PictureSelectorHelper.analyticalSelectResults(selectList, mContext, mAdapter);
                        } else if (resultCode == RESULT_CANCELED) {
                            LogUtils.i(TAG, "onActivityResult PictureSelector Cancel");
                        }
                    }
                });
    }

    /**
     * 外部预览监听事件
     */
    private class MyExternalPreviewEventListener implements OnExternalPreviewEventListener {

        @Override
        public void onPreviewDelete(int position) {
            mAdapter.remove(position);
            mAdapter.notifyItemRemoved(position);
        }

        @Override
        public boolean onLongPressDownload(LocalMedia media) {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST || requestCode == PictureConfig.REQUEST_CAMERA) {
                ArrayList<LocalMedia> result = PictureSelector.obtainSelectorList(data);
                PictureSelectorHelper.analyticalSelectResults(result, mContext, mAdapter);
            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.i(TAG, "onActivityResult PictureSelector Cancel");
        }
    }
}
