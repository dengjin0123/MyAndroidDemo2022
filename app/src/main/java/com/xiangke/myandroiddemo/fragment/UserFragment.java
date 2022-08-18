package com.xiangke.myandroiddemo.fragment;

import com.xiangke.base.mvp.BaseMvpFragment;
import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.myandroiddemo.R;

/**
 * @Date:2022/8/2 13:50
 * @Author:dengjin
 * @Name:
 */
public class UserFragment extends BaseMvpFragment {
    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_me;
    }
}
