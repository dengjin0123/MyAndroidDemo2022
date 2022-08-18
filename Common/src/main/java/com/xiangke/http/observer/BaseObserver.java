package com.xiangke.http.observer;

import android.accounts.NetworkErrorException;

import androidx.annotation.NonNull;

import com.xiangke.dialog.WaitingDialogUtils;
import com.xiangke.http.BaseResponse;
import com.xiangke.http.errorhandler.ExceptionHandle;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 自定义Observer
 * @author llw
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    //开始
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(@NonNull BaseResponse<T> tBaseResponse) {
        if (tBaseResponse.isSuccess()) {
            onSuccess(tBaseResponse);
        } else {
            onCodeError(tBaseResponse);
        }
        WaitingDialogUtils.dismissWaitDialog();
    }

    //异常
    @Override
    public void onError(Throwable e) {
        WaitingDialogUtils.dismissWaitDialog();
        ExceptionHandle.ResponseThrowable response;
        if (e instanceof ExceptionHandle.ResponseThrowable) {
            response = (ExceptionHandle.ResponseThrowable)e;
            try {
                onFailure(response);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            response = new ExceptionHandle.ResponseThrowable(e, -1000);
            try {
                onFailure(response);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    //完成
    @Override
    public void onComplete() {

    }

    //请求成功且返回码为200的回调方法,这里抽象方法申明
    public abstract void onSuccess(BaseResponse<T> tBaseReponse);

    //请求成功但返回的code码不是200的回调方法,这里抽象方法申明
    public abstract void onCodeError(BaseResponse<T> tBaseReponse);

    //请求失败回调方法,这里抽象方法申明
    public abstract void onFailure(ExceptionHandle.ResponseThrowable throwable) throws Exception;
}
