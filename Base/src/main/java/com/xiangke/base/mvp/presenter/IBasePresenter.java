package com.xiangke.base.mvp.presenter;

import com.xiangke.base.mvp.view.IBaseView;

/**
 * @Author lester
 * @Date 2018/7/25
 */
public interface IBasePresenter<T extends IBaseView> {
    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 回收View
     */
    void detachView();
}
