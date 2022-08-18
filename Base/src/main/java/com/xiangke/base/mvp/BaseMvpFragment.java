package com.xiangke.base.mvp;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.com.xiangke.base.R;
import com.xiangke.AppManager;
import com.xiangke.base.ui.ErrorPanel;
import com.xiangke.base.ui.TitlePanel;
import com.xiangke.base.ui.fragment.BaseSimpleFragment;

import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.base.mvp.view.IBaseView;
import com.xiangke.base.receiver.NetworkReceiver;
import com.xiangke.myutil.EventBusUtil;

import butterknife.ButterKnife;

public abstract class BaseMvpFragment<T extends IBasePresenter> extends BaseSimpleFragment implements IBaseView, NetworkReceiver.CallBack {
    protected T mPresenter;
    /**
     * 标题栏处理
     */
    protected TitlePanel titlePanel;
    /**
     * 数据加载出错统一处理
     */
    protected ErrorPanel errorPanel;

    protected View realContentView;

    protected NetworkReceiver networkReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        contentView = (ViewGroup) inflater.inflate(R.layout.base_content_layout, null);
        titlePanel = new TitlePanel(getContext(), contentView);
        inflater.inflate(getContentLayoutId(), contentView, true);
        realContentView = contentView.getChildAt(1);
        errorPanel = new ErrorPanel(getContext(), contentView);
        if (!isAttachTitle()) {
            titlePanel.setVisibility(View.GONE);
        } else {
            AppManager.setWhiteStatusBar(requireActivity(), true);
//            StatusBarUtil.setColor(requireActivity(), getResources().getColor(R.color.main_app_color));
//            AppManager.setBarPadding(titlePanel.findViewById(R.id.fl_title_root));
        }
        if (isListenerNetwork()) {
            registerNetworkReceiver();
        } else {
            showNormal();
        }
        unbinder = ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    protected void onCreateInit(Bundle savedInstanceState) {
        super.onCreateInit(savedInstanceState);
        mPresenter = initPresenter();
    }

    @Override
    protected void initView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void initData() {

    }

    protected abstract T initPresenter();

    /**
     * 是否添加公共title
     *
     * @return
     */
    protected boolean isAttachTitle() {
        return false;
    }

    /**
     * 是否监听网络
     *
     * @return
     */
    protected boolean isListenerNetwork() {
        return false;
    }

    @Override
    public void showError() {
        errorPanel.setVisibility(View.VISIBLE);
        realContentView.setVisibility(View.GONE);
    }

    @Override
    public void showNormal() {
        errorPanel.setVisibility(View.GONE);
        realContentView.setVisibility(View.VISIBLE);
    }

    @Override
    public Activity getInstance() {
        return getActivity();
    }

    protected void registerNetworkReceiver() {
        networkReceiver = new NetworkReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getContext().registerReceiver(networkReceiver, intentFilter);
    }

    @Override
    public void connectNetwork() {
        showNormal();
    }

    @Override
    public void connectWifi() {

    }

    @Override
    public void disConnectNetwork() {
        showError();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (networkReceiver != null) {
            getContext().unregisterReceiver(networkReceiver);
        }
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }
}
