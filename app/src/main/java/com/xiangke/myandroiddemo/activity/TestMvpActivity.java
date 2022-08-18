package com.xiangke.myandroiddemo.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.popup.BubblePopup;
import com.xiangke.AppManager;
import com.xiangke.base.mvp.BaseMvpActivity;
import com.xiangke.base.mvp.presenter.IBasePresenter;
import com.xiangke.base.ui.NoDoubleClickListener;
import com.xiangke.helper.DialogHelper;
import com.xiangke.myandroiddemo.R;
import com.xiangke.myandroiddemo.popwindow.PopupHelper;
import com.xiangke.utilcode.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Date:2022/5/31 17:10
 * @Author:dengjin
 * @Name:
 */
public class TestMvpActivity extends BaseMvpActivity {
    @BindView(R.id.test_btn_pop)
    Button btnPop;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initBase(int layoutResID) {
        super.initBase(layoutResID);
    }

    @Override
    protected void initView() {
        super.initView();
        titlePanel.setTitleBackgroundColor(getResources().getColor(R.color.main_app_color));
        AppManager.setStatusBarColor(this, getResources().getColor(R.color.main_app_color), true);
        errorPanel.setErrorListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                Toast.makeText(TestMvpActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected boolean isAttachTitle() {
        return true;
    }

    public void jumpPage1(View view) {
        Intent intent = new Intent(this, TestMvpActivity1.class);
        startActivity(intent);
    }

    public void clickDialog1(View view) {
        NormalDialog dialog = DialogHelper.createTitleNormalDialog(this, "标题", 2, "内容", "取消", "确定");
        dialog.setOnBtnClickL(
                () -> ToastUtils.showShort("left"),
                () -> ToastUtils.showShort("right"));
    }

    public void clickDialog2(View view) {
        NormalDialog dialog = DialogHelper.createNoTitleNormalDialog(this, 2, "内容", "取消", "确定");
        dialog.setOnBtnClickL(
                () -> ToastUtils.showShort("left"),
                () -> ToastUtils.showShort("right"));
    }

    public void clickDialog3(View view) {
        MaterialDialog materialDialog = DialogHelper.createMaterialDialog(this, 2, "内容", "取消", "确定");
        materialDialog.setOnBtnClickL(
                () -> ToastUtils.showShort("left"),
                () -> ToastUtils.showShort("right"));
    }

    public void clickPop(View view) {
//        View inflate = View.inflate(mContext, R.layout.popup_bubble_text, null);
//        BubblePopup bubblePopup = new BubblePopup(mContext, inflate);
//        bubblePopup.anchorView(view)
//                .gravity(Gravity.BOTTOM)
//                .show();
//        BubblePopup popup = DialogHelper.createCusBubPopup(this, R.layout.test_pop, view);
        PopupHelper.createCusPopup(this, view);
//        popup.showAnim(new FadeEnter())
//                .dismissAnim(new FadeExit())
//                .dimEnabled(true)
//                .anchorView(mTvBottomRight)
//                .offset(-10, -5)
//                .dimEnabled(false)
//                .gravity(Gravity.TOP)
//                .show();
    }

    @Override

    protected IBasePresenter initPresenter() {
        return null;
    }
}
