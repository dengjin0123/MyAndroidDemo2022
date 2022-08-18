package com.xiangke.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.xiangke.common.R;

/**
 * 倒计时控件
 *
 * @Author lester
 * @Date 2018/7/30
 */
public class CountDownTextView extends AppCompatTextView {
    private OnCountDownListener mListener;
    private CountDownTimer mTimer;
    private long mCountDownTime;    // 倒计时间，单位毫秒
    private long mSecond;

    public CountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCountDownTime(context, attrs);
    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initCountDownTime(context, attrs);
    }

    public CountDownTextView(Context context) {
        super(context);
    }

    private void initCountDownTime(Context context, AttributeSet attrs) {
        @SuppressLint({"CustomViewStyleable", "Recycle"}) TypedArray attribute = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        mCountDownTime = (long) attribute.getFloat(R.styleable.CountDownView_countDownTime, 0);
    }

    public void setCountDownTimes(long countDownTime) {
        mCountDownTime = countDownTime+300;
    }

    public void start() {
        if (mCountDownTime < 0) mCountDownTime = 0;

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        int countDownInterval = 1000;
        mTimer = new CountDownTimer(mCountDownTime, countDownInterval) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                CountDownTextView.this.setClickable(false);
                CountDownTextView.this.setEnabled(false);
                CountDownTextView.this.setTextColor(Color.parseColor("#999999"));
                mSecond = millisUntilFinished/1000;
                CountDownTextView.this.setText(mSecond+"s");
                if (mListener != null) {
                    mListener.onTick(mSecond);
                }
            }

            @Override
            public void onFinish() {
                CountDownTextView.this.setText("重新获取");
                CountDownTextView.this.setClickable(true);
                CountDownTextView.this.setEnabled(true);
                CountDownTextView.this.setTextColor(Color.parseColor("#3485FE"));
                if (mListener != null)
                    mListener.onFinish();
            }
        };
        mTimer.start();
    }

    /**
     * 计时关闭
     */
    public void cancel() {
        if (mTimer != null)
            mTimer.cancel();
    }

    public void setCountDownListener(OnCountDownListener listener) {
        this.mListener = listener;
    }

    public interface OnCountDownListener {
        void onTick(long sec);   // 倒计时中

        void onFinish(); // 倒计时完成
    }
}

