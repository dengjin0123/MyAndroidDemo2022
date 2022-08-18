package com.xiangke.myandroiddemo.popwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.flyco.animation.FadeEnter.FadeEnter;
import com.flyco.animation.FadeExit.FadeExit;

/**
 * @Date:2022/7/22 10:38
 * @Author:dengjin
 * @Name:
 */
public class PopupHelper {
    public static SimpleListPopup createCusPopup(Context context, View anchorView) {
        SimpleListPopup popup = new SimpleListPopup(context);
        popup.anchorView(anchorView)
                .gravity(Gravity.BOTTOM)
                .offset(0,-15)
                .showAnim(new FadeEnter())
                .dismissAnim(new FadeExit())
                .show();
        return popup;
    }
}
