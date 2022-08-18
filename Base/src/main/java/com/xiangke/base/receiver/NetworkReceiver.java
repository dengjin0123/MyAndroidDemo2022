package com.xiangke.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xiangke.utilcode.NetworkUtils;

public class NetworkReceiver extends BroadcastReceiver {
    private CallBack callBack;

    public NetworkReceiver(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (callBack == null) {
                return;
            }
            if (NetworkUtils.isConnected()) {
                callBack.connectNetwork();
                if (NetworkUtils.isWifiConnected()) {
                    callBack.connectWifi();
                }
            } else {
                callBack.disConnectNetwork();
            }
        }

    }

    public interface CallBack {
        void connectNetwork();

        void connectWifi();

        void disConnectNetwork();
    }

}
