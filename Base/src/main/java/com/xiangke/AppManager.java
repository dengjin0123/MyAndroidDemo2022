package com.xiangke;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.view.ViewCompat;

import java.util.Stack;

public class AppManager {

    private static Stack<Activity> activityStack;   // Activity栈  ， 先进后出
    private static AppManager instance;

    public static Application globalApplication;

    /**
     * 单例模式实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public static void init(Application application) {
        globalApplication = application;
        //ARouter配置
//        if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
//            ARouter.openLog();     // 打印日志
//            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
//        ARouter.init(application); // 尽可能早，推荐在Application中初始化
//        //初始化DBFLOW
//        FlowManager.init(application);
    }

    /**
     * 设置状态栏颜色
     * 字体颜色为白色
     *
     * @param statusColor      状态栏颜色
     * @param fitSystemWindows 根布局是否设置fitSystemWindows
     */
    public static void setStatusBarColor(Activity activity, int statusColor, boolean fitSystemWindows) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusColor);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, fitSystemWindows);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }

    public static void setWhiteStatusBar(Activity activity, boolean fitSystemWindows) {
        setWhiteStatusBar(activity, fitSystemWindows, true);
    }

    /**
     * 设置状态栏为白色
     * 字体颜色为黑色
     *
     * @param isFullScreen     根布局是否充满屏幕
     * @param fitSystemWindows 根布局是否设置fitSystemWindows
     */
    public static void setWhiteStatusBar(Activity activity, boolean fitSystemWindows, boolean isFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (isFullScreen) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            window.setStatusBarColor(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? Color.WHITE : Color.TRANSPARENT);
            ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, fitSystemWindows);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }

    /**
     * 设置状态栏为透明
     * 字体颜色为黑色
     *
     * @param isFullScreen     根布局是否充满屏幕
     * @param fitSystemWindows 根布局是否设置fitSystemWindows
     */
    public static void setTransparentStatusBarAndBlackTypeface(Activity activity, boolean fitSystemWindows, boolean isFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (isFullScreen) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
            ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, fitSystemWindows);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }


    /**
     * 把状态栏图标设成深色
     */
    public static void setLightStatusBar(Activity activity,boolean lightStatusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 设置浅色状态栏时的界面显示
            View decor = activity.getWindow().getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (lightStatusBar) {
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }
    }

    /**
     * 设置状态栏为自定义颜色
     * 字体颜色为黑色
     * @param colors    "#f4f7fa"
     * @param isFullScreen     根布局是否充满屏幕
     * @param fitSystemWindows 根布局是否设置fitSystemWindows
     */
    public static void setCustomStatusBarAndBlackTypeface(Activity activity,int colors, boolean fitSystemWindows, boolean isFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (isFullScreen) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            window.setStatusBarColor(colors);
            ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, fitSystemWindows);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }

    public static void setLightAndTransStatusBar(Activity activity, boolean fitSystemWindows, boolean isFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (isFullScreen) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
            ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, fitSystemWindows);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }

    /**
     * 设置状态栏背景为透明
     * @param fitSystemWindows 根布局是否设置fitSystemWindows
     */
    public static void translucentStatusBar(Activity activity, boolean fitSystemWindows) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, fitSystemWindows);
                ViewCompat.requestApplyInsets(mChildView);
            }
        }
    }

    public static int getStatuBarSize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//当api<19的时候，状态栏高度返回0，否则返回真实的statusBarHeight
            int statusBarsId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
            return Resources.getSystem().getDimensionPixelSize(statusBarsId);
        }
        return 0;
    }

    public static void setBarPadding(final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {  //解决状态栏高度为warp_content或match_parent问题
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        lp.height = view.getHeight() + getStatuBarSize();
                        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatuBarSize(),
                                view.getPaddingRight(), view.getPaddingBottom());
                    }
                });
            } else {
                lp.height += getStatuBarSize();
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatuBarSize(),
                        view.getPaddingRight(), view.getPaddingBottom());
            }
        }
    }

    public static void setBarMargin(final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin + getStatuBarSize(),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
        }
    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

}
