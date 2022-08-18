package com.xiangke.myandroiddemo.http;

import android.annotation.SuppressLint;
import android.content.Context;

import com.xiangke.dialog.WaitingDialogUtils;
import com.xiangke.http.BaseApiManager;
import com.xiangke.http.RequestHelper;
import com.xiangke.http.observer.BaseObserver;
import com.xiangke.myandroiddemo.bean.LoginBean;

import okhttp3.RequestBody;

/**
 * @Date:2022/8/10 15:37
 * @Author:dengjin
 * @Name:
 */
public class LoginApiManager extends BaseApiManager {

    @SuppressLint("CheckResult")
    public static void requestLogin(Context context, String json, BaseObserver<LoginBean> observer) {
        RequestBody requestBody = toRequestBody(json);
        RequestHelper.getInstance()
                .createService(LoginApi.class)
                .login(requestBody)
                .compose(applySchedulers(observer));
        // show加载框
        WaitingDialogUtils.showWaitDialog(context, "加载中...");
    }
}
