package com.xiangke.myandroiddemo.activity;

import android.view.View;
import android.widget.EditText;

import com.xiangke.base.mvp.BaseMvpActivity;
import com.xiangke.myandroiddemo.R;
import com.xiangke.myandroiddemo.presenter.LoginContract;
import com.xiangke.myandroiddemo.presenter.LoginPresenter;
import com.xiangke.view.CountDownTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Date:2022/8/9 9:46
 * @Author:dengjin
 * @Name:
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.login_name)
    EditText mEdtName;
    @BindView(R.id.login_psw)
    EditText mEdtPsw;
    @BindView(R.id.login_count_tv_code)
    CountDownTextView countDownTv;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        countDownTv.setCountDownTimes(60000);
        countDownTv.setCountDownListener(new CountDownTextView.OnCountDownListener() {
            @Override
            public void onTick(long sec) {}

            @Override
            public void onFinish() {
                if (countDownTv != null) {
                    countDownTv.cancel();
                }
            }
        });
    }

    @OnClick({
            R.id.login_count_tv_code
    })
    public void onViewClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.login_count_tv_code) {
            countDownTv.start();
        }
    }

    public void login(View view) {
        String name = mEdtName.getText().toString();
        String psw = mEdtPsw.getText().toString();
        mPresenter.toLogin(name, psw);
    }

    @Override
    public void loginSuccess() {

    }

    private void cancelCountDownTv(){
        if (countDownTv != null) {
            countDownTv.cancel();
            countDownTv = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelCountDownTv();
    }
}
