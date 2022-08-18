package com.xiangke.http;

import static com.xiangke.constant.CommonConstants.CONNECT_TIMEOUT;

import android.util.Log;

import com.xiangke.common.BuildConfig;
import com.xiangke.http.interceptor.RequestInterceptor;
import com.xiangke.http.interceptor.ResponseInterceptor;
import com.xiangke.utilcode.AppUtils;
import com.xiangke.utilcode.LogUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络API
 *
 * @author llw
 */
public class RequestHelper {
    private static final String TAG = "RequestHelper";

    private static RequestHelper instance;
    //OkHttp客户端
    private static OkHttpClient okHttpClient;
    //retrofitHashMap
    private static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();
    //API访问地址
    private static String mBaseUrl;

    private RequestHelper() {
    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            synchronized (RequestHelper.class) {
                if (instance == null) {
                    instance = new RequestHelper();
                    init();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     */
    private static void init() {
        //当初始化这个NetworkApi时，会判断当前App的网络环境
        //是否为测试环境
        LogUtils.i(TAG, "init IsDebug = "+ AppUtils.isAppDebug());
        if (AppUtils.isAppDebug()) {
            //测试环境
            mBaseUrl = ApiUrl.BASE_URL_DEBUG;
            LogUtils.i(TAG,"init 测试环境");
        } else {
            //正式环境
            mBaseUrl = ApiUrl.BASE_URL_RELEASE;
            LogUtils.i(TAG, "init 正式环境");
        }
    }

    public String getBaseUrl(){
        return mBaseUrl;
    }

    /**
     * 创建serviceClass的实例
     */
    public  <T> T createService(Class<T> serviceClass) {
        return getRetrofit(serviceClass).create(serviceClass);
    }

    /**
     * 配置OkHttp
     *
     * @return OkHttpClient
     */
    private static OkHttpClient getOkHttpClient() {
        //不为空则说明已经配置过了，直接返回即可。
        if (okHttpClient == null) {
            //OkHttp构建器
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //设置网络请求超时时长，这里设置为60s
            builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
            builder.writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
            //添加请求拦截器，如果接口有请求头的话，可以放在这个拦截器里面
            builder.addInterceptor(new RequestInterceptor());
            //添加返回拦截器，可用于查看接口的请求耗时，对于网络优化有帮助
            builder.addInterceptor(new ResponseInterceptor());
            //当程序在debug过程中则打印数据日志，方便调试用。
            // debug状态下则初始化日志拦截器
//            if (mIsDebug) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                //设置要打印日志的内容等级，BODY为主要内容，还有BASIC、HEADERS、NONE。
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //将拦截器添加到OkHttp构建器中
                builder.addInterceptor(httpLoggingInterceptor);
//            }
            //OkHttp配置完成
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    /**
     * 配置Retrofit
     *
     * @param serviceClass 服务类
     * @return Retrofit
     */
    private static Retrofit getRetrofit(Class serviceClass) {
        if (retrofitHashMap.get(mBaseUrl + serviceClass.getName()) != null) {
            //刚才上面定义的Map中键是String，值是Retrofit，当键不为空时，必然有值，有值则直接返回。
            return retrofitHashMap.get(mBaseUrl + serviceClass.getName());
        }
        //初始化Retrofit  Retrofit是对OKHttp的封装，通常是对网络请求做处理，也可以处理返回数据。
        //Retrofit构建器
        Retrofit.Builder builder = new Retrofit.Builder();
        //设置访问地址
        builder.baseUrl(mBaseUrl);
        //设置OkHttp客户端，传入上面写好的方法即可获得配置后的OkHttp客户端。
        builder.client(getOkHttpClient());
        //设置数据解析器 会自动把请求返回的结果（json字符串）通过Gson转化工厂自动转化成与其结构相符的实体Bean
        builder.addConverterFactory(GsonConverterFactory.create());
        //设置请求回调，使用RxJava 对网络返回进行处理
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        //retrofit配置完成
        Retrofit retrofit = builder.build();
        //放入Map中
        retrofitHashMap.put(mBaseUrl + serviceClass.getName(), retrofit);
        //最后返回即可
        return retrofit;
    }
}
