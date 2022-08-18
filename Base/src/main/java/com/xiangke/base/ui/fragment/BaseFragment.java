package com.xiangke.base.ui.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.com.xiangke.base.R;
import com.xiangke.base.receiver.NetworkReceiver;
import com.xiangke.base.ui.ErrorPanel;
import com.xiangke.base.ui.TitlePanel;
import com.xiangke.myutil.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * @Date:2022/7/8 13:37
 * @Author:dengjin
 * @Name:
 */
public abstract class BaseFragment extends BaseSimpleFragment implements NetworkReceiver.CallBack{
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
        StatusBarUtil.setColor(requireActivity(), getResources().getColor(R.color.main_app_color));
        contentView = (ViewGroup) inflater.inflate(R.layout.base_content_layout, null);
        titlePanel = new TitlePanel(getContext(), contentView);
        inflater.inflate(getContentLayoutId(), contentView, true);
        realContentView = contentView.getChildAt(1);
        errorPanel = new ErrorPanel(getContext(), contentView);
        errorPanel.setVisibility(View.GONE);
        if (!isAttachTitle()) {
            titlePanel.setVisibility(View.GONE);
        }
        if (isListenerNetwork()) {
            registerNetworkReceiver();
        }
        unbinder = ButterKnife.bind(this, contentView);
        return contentView;
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
        realContentView.setVisibility(View.GONE);
    }

    protected void showNormal() {
        errorPanel.setVisibility(View.GONE);
        realContentView.setVisibility(View.VISIBLE);
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
    public void onDestroy() {
        super.onDestroy();
        if (networkReceiver != null) {
            getContext().unregisterReceiver(networkReceiver);
        }
    }
}
