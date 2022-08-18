package com.xiangke.myandroiddemo.activity;

import com.xiangke.base.mvp.BaseMvpActivity;
import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.myandroiddemo.R;

/**
 * @Date:2022/5/31 17:10
 * @Author:dengjin
 * @Name:
 */
public class TestMvpActivity1 extends BaseMvpActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected boolean isAttachTitle() {
        return true;
    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }
}
