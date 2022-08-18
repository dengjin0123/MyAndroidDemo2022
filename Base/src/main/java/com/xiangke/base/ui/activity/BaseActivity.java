package com.xiangke.base.ui.activity;

import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.com.xiangke.base.R;
import com.xiangke.base.receiver.NetworkReceiver;
import com.xiangke.base.ui.ErrorPanel;
import com.xiangke.base.ui.TitlePanel;
import com.xiangke.myutil.StatusBarUtil;

/**
 * @Date:2022/7/8 13:43
 * @Author:dengjin
 * @Name:
 */
public abstract class BaseActivity extends BaseSimpleActivity implements NetworkReceiver.CallBack{
    /**
     * 数据加载出错统一处理
     */
    protected ErrorPanel errorPanel;

    protected View contentView;

    protected NetworkReceiver networkReceiver;
    private TitlePanel titlePanel;

    @Override
    protected void initBase(int layoutResID) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.main_app_color));
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup baseView = (ViewGroup) inflater.inflate(R.layout.base_content_layout, null);
        titlePanel = new TitlePanel(this, baseView);
        inflater.inflate(layoutResID, baseView, true);
        contentView = baseView.getChildAt(1);
        errorPanel = new ErrorPanel(this, baseView);
        setContentView(baseView);
        if (!isAttachTitle()) {
            titlePanel.setVisibility(View.GONE);
        }
        if (isListenerNetwork()) {
            registerNetworkReceiver();
        }
    }


    /**
     * 设置整个页面的背景
     * @param resId
     */
    protected void setMainBg(int resId) {
        findViewById(R.id.base_content).setBackgroundResource(resId);
    }

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

    protected void showError() {
        errorPanel.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    protected void showNormal() {
        errorPanel.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
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
    protected void onStop() {
        super.onStop();
        if (!isFinishing()) {
            return;
        }
        if (networkReceiver != null) {
            unregisterReceiver(networkReceiver);
        }
    }
}
