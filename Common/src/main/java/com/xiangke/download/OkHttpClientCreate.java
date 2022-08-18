package com.xiangke.download;

import com.xiangke.constant.CommonConstants;
import com.xiangke.utilcode.SPUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @Date:2022/3/23 14:57
 * @Author:dengjin
 * @Name:
 */
public class OkHttpClientCreate {
    private static final boolean IS_RETRY = false;//失败是否重连
    private static final int CONNECT_TIME = 10;//设置连接超时时间 单位:秒
    private static final int READ_TIME = 10;//设置读取超时时间
    private static final int WRITE_TIME = 10;//设置写入超时间
    private static OkHttpClient mOkHttpClient;
    public static OkHttpClient CreateClient(){
        if (mOkHttpClient == null){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return mOkHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)//连接超时
                    .readTimeout(READ_TIME,TimeUnit.SECONDS)//读取超时
                    .writeTimeout(WRITE_TIME,TimeUnit.SECONDS)//写入超时
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(new MyOnInterceptor())
//                    .callTimeout()//呼叫超时,设置此参数为整体流程请求的超时时间
//                    .addInterceptor() //设置拦截器
//                    .authenticator() //设置认证器
//                    .proxy()//设置代理
                    .build();

        }
        return mOkHttpClient;
    }

    private static class MyOnInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            String token = SPUtils.getInstance().getString(CommonConstants.TOKEN, "");
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .addHeader("Accept", "application/json")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("satoken", SPUtils.getInstance().getString(CommonConstants.TOKEN, ""))
                    .build();
            return chain.proceed(request);
        }
    }

    public static void destroy(){
        mOkHttpClient = null;
    }
}
