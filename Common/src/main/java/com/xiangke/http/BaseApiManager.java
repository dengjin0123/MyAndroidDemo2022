package com.xiangke.http;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.xiangke.dialog.WaitingDialogUtils;
import com.xiangke.http.errorhandler.ExceptionHandle;
import com.xiangke.http.errorhandler.HttpErrorHandler;
import com.xiangke.http.errorhandler.HttpErrorHandler1;
import com.xiangke.http.observer.BaseObserver;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Date:2022/8/10 14:21
 * @Author:dengjin
 * @Name:
 */
public class BaseApiManager {

    public void request() {
        
    }

    @SuppressLint("CheckResult")
    public void get(Context context, String url, BaseObserver<JsonObject> observer){
        String requestUrl = RequestHelper.getInstance().getBaseUrl()+url;
        RequestHelper.getInstance().createService(RequestApi.class).getRequest(requestUrl).compose(applySchedulers(observer));
        // show加载框
        WaitingDialogUtils.showWaitDialog(context, "加载中...");
    }

    @SuppressLint("CheckResult")
    public void post(Context context, String url, String jsonString, BaseObserver<JsonObject> observer) {
        String requestUrl = RequestHelper.getInstance().getBaseUrl()+url;
        RequestBody body = toRequestBody(jsonString);
        RequestHelper.getInstance().createService(RequestApi.class).postRequest(requestUrl, body).compose(applySchedulers(observer));
        // show加载框
        WaitingDialogUtils.showWaitDialog(context, "加载中...");
    }

    public static RequestBody toRequestBody(String content) {
        return RequestBody.create(content, MediaType.parse("application/json; charset=utf-8"));
    }

    private RequestBody toRequestBodyFile(File file) {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("image/png")))// text/plain
                .build();
    }

    /**
     *
     * 配置RxJava 完成线程的切换，如果是Kotlin中完全可以直接使用协程
     *
     * @param observer 这个observer要注意不要使用lifecycle中的Observer
     * @param <T>      泛型
     * @return Observable
     */
    public static <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @NonNull
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                Observable<T> observable = upstream
                        .subscribeOn(Schedulers.io())//线程订阅
                        .observeOn(AndroidSchedulers.mainThread())//观察Android主线程
                        .map(getAppErrorHandler())//判断有没有500的错误，有则进入getAppErrorHandler
                        .onErrorResumeNext(new HttpErrorHandler<T>());//判断有没有400的错误
                //这里还少了对异常
                //订阅观察者
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    /**
     * 错误码处理
     */
    protected static <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(@NonNull T response) throws Exception {
                //当response返回出现500之类的错误时
                if (response instanceof BaseResponse && ((BaseResponse) response).getRet() >= 500) {
                    //通过这个异常处理，得到用户可以知道的原因
                    ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                    exception.code = ((BaseResponse) response).getRet();
                    exception.message = ((BaseResponse) response).getMessage() != null ? ((BaseResponse) response).getMessage() : "";
                    throw exception;
                }
                return response;
            }
        };
    }
}
