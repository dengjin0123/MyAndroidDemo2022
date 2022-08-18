package com.xiangke.myandroiddemo.presenter;

import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.base.mvp.view.IBaseView;

/**
 * @Date:2022/8/9 9:56
 * @Author:dengjin
 * @Name:
 */
public interface HomeContract {

    interface View extends IBaseView{
        void requestSuccess();
    }

    interface presenter extends IBasePresenter<View>{
        void toRequest(String name, String psw);
    }
}
