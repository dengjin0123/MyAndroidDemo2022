package com.xiangke.http.interceptor;

import com.xiangke.constant.CommonConstants;
import com.xiangke.http.INetworkRequiredInfo;
import com.xiangke.myutil.DateUtil;
import com.xiangke.utilcode.AppUtils;
import com.xiangke.utilcode.LogUtils;
import com.xiangke.utilcode.SPUtils;

import java.io.IOException;
import java.io.PipedReader;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求拦截器
 * @author llw
 */
public class RequestInterceptor implements Interceptor {
    private static final String TAG = "RequestInterceptor";
    /**
     * 网络请求信息
     */
    public RequestInterceptor(){
    }

    /**
     * 拦截
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        String nowDateTime = DateUtil.getNowDateTime();
        //构建器
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", "application/json;charset=utf-8");
        builder.addHeader("Accept", "application/json");
        builder.addHeader("Connection", "keep-alive");
        //添加使用环境
        builder.addHeader("os","android");
        //添加版本号
        builder.addHeader("appVersionCode", AppUtils.getAppVersionCode()+"");
        //添加版本名
        builder.addHeader("appVersionName",AppUtils.getAppVersionName());
        //添加日期时间
        builder.addHeader("datetime",nowDateTime);
        // 增加token
        builder.header("satoken", SPUtils.getInstance().getString(CommonConstants.TOKEN));

        Request request = chain.request();
        String s = request.toString();
        LogUtils.i(TAG, "request = "+ s);
        //返回
        return chain.proceed(builder.build());
    }
}
