package com.xiangke.myandroiddemo.activity;

import com.xiangke.base.ui.activity.BaseActivity;
import com.xiangke.myandroiddemo.R;
import com.xiangke.utilcode.LogUtils;

/**
 * @Date:2022/5/27 15:33
 * @Author:dengjin
 * @Name:
 */
public class TestActivity extends BaseActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected boolean isAttachTitle() {
        return true;
    }

    @Override
    protected void initView() {
        LogUtils.i("Test", "initView");
    }

    @Override
    protected void initData() {

    }
}