package com.xiangke.myandroiddemo.popwindow;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.flyco.dialog.widget.popup.base.BasePopup;
import com.xiangke.myandroiddemo.R;
import com.xiangke.utilcode.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Date:2022/7/22 10:36
 * @Author:dengjin
 * @Name:
 */
public class SimpleListPopup extends BasePopup<SimpleListPopup> {
    TextView mTvItem1;
    TextView mTvItem2;
    TextView mTvItem3;
    TextView mTvItem4;
    TextView mTvItem5;
    public SimpleListPopup(Context context) {
        super(context);
    }

    @Override
    public View onCreatePopupView() {
        View inflate = View.inflate(mContext, R.layout.test_pop, null);
        mTvItem1 = inflate.findViewById(R.id.tv_item_1);
        mTvItem2 = inflate.findViewById(R.id.tv_item_2);
        mTvItem3 = inflate.findViewById(R.id.tv_item_3);
        mTvItem4 = inflate.findViewById(R.id.tv_item_4);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
       mTvItem1.setOnClickListener(v -> ToastUtils.showShort(mTvItem1.getText()));
       mTvItem2.setOnClickListener(v -> ToastUtils.showShort(mTvItem2.getText()));
       mTvItem3.setOnClickListener(v -> ToastUtils.showShort(mTvItem3.getText()));
       mTvItem4.setOnClickListener(v -> ToastUtils.showShort(mTvItem4.getText()));
    }
}
