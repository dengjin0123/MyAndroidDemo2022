package com.xiangke.base.mvp;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.com.xiangke.base.R;
import com.xiangke.AppManager;
import com.xiangke.base.receiver.NetworkReceiver;
import com.xiangke.base.ui.ErrorPanel;
import com.xiangke.base.ui.TitlePanel;
import com.xiangke.base.ui.activity.BaseSimpleActivity;

import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.base.mvp.view.IBaseView;

/**
 * @Author lester
 * @Date 2018/7/24
 */
public abstract class BaseMvpActivity<T extends IBasePresenter> extends BaseSimpleActivity implements IBaseView, NetworkReceiver.CallBack {
    protected T mPresenter;
    /**
     * 标题栏统一处理
     */
    protected TitlePanel titlePanel;
    /**
     * 数据加载出错统一处理
     */
    protected ErrorPanel errorPanel;

    protected View contentView;

    protected NetworkReceiver networkReceiver;

    @Override
    protected void onCreateInit(Bundle savedInstanceState) {
        super.onCreateInit(savedInstanceState);
        mPresenter = initPresenter();
    }

    @Override
    protected void initBase(int layoutResID) {
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup baseView = (ViewGroup) inflater.inflate(R.layout.base_content_layout, null);
        titlePanel = new TitlePanel(this, baseView);
        inflater.inflate(layoutResID, baseView, true);
        contentView = baseView.getChildAt(1);
        errorPanel = new ErrorPanel(this, baseView);
        setContentView(baseView);
        if (!isAttachTitle()) {
            titlePanel.setVisibility(View.GONE);
        } else {
            AppManager.setWhiteStatusBar(this, true);
//            StatusBarUtil.setColor(this, getResources().getColor(R.color.main_app_color));
//            AppManager.translucentStatusBar(this, false);
//            AppManager.setCustomStatusBarAndBlackTypeface(this, getResources().getColor(R.color.white),false,true);
//            AppManager.setBarPadding(findViewById(R.id.fl_title_root));
        }
        if (isListenerNetwork()) {
            registerNetworkReceiver();
        } else {
            showNormal();
        }
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
        contentView.setVisibility(View.GONE);
    }

    @Override
    public void showNormal() {
        errorPanel.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public Activity getInstance() {
        return instance;
    }

    protected void registerNetworkReceiver() {
        networkReceiver = new NetworkReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkReceiver, intentFilter);
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
    public void onStop() {
        super.onStop();
        if (!isFinishing()) {
            return;
        }
        if (networkReceiver != null) {
            unregisterReceiver(networkReceiver);
        }
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

}
