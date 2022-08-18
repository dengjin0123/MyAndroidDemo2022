package com.xiangke.http.interceptor;

import com.xiangke.utilcode.LogUtils;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 返回拦截器(响应拦截器)
 *
 * @author llw
 */
public class ResponseInterceptor implements Interceptor {

    private static final String TAG = "ResponseInterceptor";

    /**
     * 拦截
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        LogUtils.i(TAG, "requestSpendTime=" + (System.currentTimeMillis() - requestTime) + "ms");
        // response.body().string只能调用一次，重新获取新对象再打印原始请求结果
        Response response1 = chain.proceed(chain.request());
        LogUtils.i(TAG, "Response body = " + Objects.requireNonNull(response1.body()).string());
        return response;
    }
}