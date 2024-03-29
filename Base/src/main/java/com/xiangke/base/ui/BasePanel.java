package com.xiangke.base.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 * 模拟activity方法的view处理父类
 *
 * @author liubin
 * @date 2016/12/2
 */
public abstract class BasePanel {

    protected Context context;
    protected View contentView;

    public BasePanel(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public void setContentView(@LayoutRes int layoutID) {
        contentView = LayoutInflater.from(context).inflate(layoutID, null);
    }

    /**
     * inflate 布局，并将inflate 得到的View 根据（attachToRoot）是否添加到ViewGroup（parentView）
     *
     * @param layoutID     布局ID
     * @param parentView   容器，使参数生效 为null时 inflate参数不生效
     * @param attachToRoot 是否将 inflate 得到View添加到 添加到ViewGroup
     */
    public void setContentView(@LayoutRes int layoutID, ViewGroup parentView, boolean attachToRoot) {
        contentView = LayoutInflater.from(context).inflate(layoutID, parentView, attachToRoot);
    }

    public View getContentView() {
        return contentView;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        if (contentView == null) {
            return null;
        }
        return contentView.findViewById(id);
    }

    /**
     * 设置显示
     * 当调用inflate方法attachToRoot参数未true的时候contentView为parentView
     * 需要重写该方法
     */
    public void setVisibility(int visibility) {
        if (contentView != null) {
            contentView.setVisibility(visibility);
        }
    }
}
