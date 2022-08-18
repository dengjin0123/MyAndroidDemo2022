package com.xiangke.base.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xiangke.myutil.EventBusUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Date:2022/7/8 13:38
 * @Author:dengjin
 * @Name:
 */
public abstract class BaseSimpleFragment extends Fragment {

    /**
     * 判断是否是第一次加载，当数据加载完成时，置为false
     */
    protected boolean isFirst = true;
    /**
     * 判断当前fragment 是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 界面是否初始化完毕
     */
    protected boolean isPrepare;
    /**
     *
     */
    protected Unbinder unbinder;
    /**
     * contentView 方便统一处理view
     */
    protected ViewGroup contentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        contentView = (ViewGroup) inflater.inflate(getContentLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, contentView);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onCreateInit(savedInstanceState);
        initView();
        isFirst = true;
    }

    /**
     * 数据懒加载判断
     */
    public void lazzLodeData() {
        if (isPrepare && isVisibleToUser) {
            allwaysInitData();
        }
        //隐藏状态下或者懒加载非第一次的情况下 不加载数据
        if (!isPrepare || !isVisibleToUser || isLazzLoad() && !isFirst) {
            return;
        }
        initData();
        isFirst = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepare = true;
        isVisibleToUser = true;
        lazzLodeData();
    }


    @Override
    public void onPause() {
        super.onPause();
        isVisibleToUser = false;
        isPrepare = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getActivity() != null) {
            if (isVisibleToUser) {
                // 将所有正等待显示的子Fragment设置为显示状态，并取消等待显示标记
                List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment.getUserVisibleHint()) {
                            childFragment.setUserVisibleHint(isVisibleToUser);
                        }
                    }
                }
            }
        }
        this.isVisibleToUser = isVisibleToUser;
        lazzLodeData();
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

    protected void allwaysInitData() {

    }

    /**
     * 是否懒加载
     *
     * @return
     */
    protected boolean isLazzLoad() {
        return true;
    }

    protected boolean isRegisterEventBus() {
        return false;
    }
}
