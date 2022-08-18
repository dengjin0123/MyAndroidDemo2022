package com.xiangke.myandroiddemo;

import android.app.Application;

import com.xiangke.utilcode.Utils;

/**
 * @Date:2022/7/12 17:01
 * @Author:dengjin
 * @Name:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
