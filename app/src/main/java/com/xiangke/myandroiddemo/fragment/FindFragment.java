package com.xiangke.myandroiddemo.fragment;

import android.content.Intent;
import android.view.View;

import com.xiangke.base.mvp.BaseMvpFragment;
import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.myandroiddemo.R;
import com.xiangke.myandroiddemo.activity.FeedBackActivity;

import butterknife.OnClick;

/**
 * @Date:2022/8/2 13:50
 * @Author:dengjin
 * @Name:
 */
public class FindFragment extends BaseMvpFragment {
    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_find;
    }

    @OnClick({
            R.id.find_select_img
    })
    public void onViewClick(View view){
        if (view.getId() == R.id.find_select_img) {
            startActivity(new Intent(getActivity(), FeedBackActivity.class));
        }
    }
}
