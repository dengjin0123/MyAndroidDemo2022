package com.xiangke.base.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xiangke.AppManager;
import com.xiangke.myutil.EventBusUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * @Date:2022/7/8 13:43
 * @Author:dengjin
 * @Name:
 */
public abstract class BaseSimpleActivity extends SwipeBackActivity {
    private static final String TAG = BaseSimpleActivity.class.getSimpleName();

    protected Activity instance;
    protected Intent intent;
    protected String GETINTENT_BUNDLE = "bundle_data";

    protected Context mContext;

    protected AnimationDrawable voiceAnimation;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        instance = this;
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        onCreateInit(savedInstanceState);
        initBase(getContentLayoutId());
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        unbinder = ButterKnife.bind(this);
//        ARouter.getInstance().inject(this);
        AppManager.getInstance().addActivity(this);
        mContext = this;
        initView();
        initData();
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        unbinder.unbind();
        AppManager.getInstance().finishActivity(this);
        if (voiceAnimation != null) {
            voiceAnimation.stop();
            voiceAnimation.selectDrawable(0);
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    protected void initBase(int layoutResID) {
        setContentView(layoutResID);
    }

    /**
     * onCreateInit  Bundle处理
     *
     * @param savedInstanceState
     */
    protected void onCreateInit(Bundle savedInstanceState) {
    }

    /**
     * 布局id
     * 需要子类重写
     *
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    protected boolean isRegisterEventBus() {
        return false;
    }

    protected void toActivity(Class<?> cls, Bundle bundle, boolean isClosed) {
        intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtra(GETINTENT_BUNDLE, bundle);
        }
        startActivity(intent);
        if (isClosed) {
            finish();
        }
    }

    protected void toActivityForResult(Class<?> cls, Bundle bundle, boolean isClosed, int requestCode) {
        intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtra(GETINTENT_BUNDLE, bundle);
        }
        if (requestCode == 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
        if (isClosed) {
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected Bundle getIntentBundle() {
        if (getIntent() != null) {
            return getIntent().getBundleExtra(GETINTENT_BUNDLE);
        }
        return null;
    }

    protected void toActivityByName(String clsName, Bundle bundle, boolean isClosed) {
        intent = new Intent();
        intent.setClassName(mContext, clsName);
        if (bundle != null) {
            intent.putExtra(GETINTENT_BUNDLE, bundle);
        }
        startActivity(intent);
        if (isClosed) {
            finish();
        }
    }

    protected int getResourcesColor(int colorID) {
        return ContextCompat.getColor(mContext, colorID);
    }
}
