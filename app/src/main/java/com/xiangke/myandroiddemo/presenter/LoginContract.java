package com.xiangke.myandroiddemo.presenter;

import android.app.Activity;

import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.base.mvp.view.IBaseView;

/**
 * @Date:2022/8/9 9:56
 * @Author:dengjin
 * @Name:
 */
public interface LoginContract {

    interface View extends IBaseView{
        void loginSuccess();
    }

    interface presenter extends IBasePresenter<View>{
        void toLogin(String name, String psw);
    }
}
