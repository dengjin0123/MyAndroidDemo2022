package com.xiangke.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.flyco.animation.FadeEnter.FadeEnter;
import com.flyco.animation.FadeExit.FadeExit;
import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.popup.BubblePopup;
import com.xiangke.common.R;
import com.xiangke.listener.OnNeutralListener;
import com.xiangke.listener.OnPositiveListener;
import com.xiangke.utilcode.ActivityUtils;
import com.xiangke.utilcode.LogUtils;
import com.xiangke.utilcode.PermissionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Date:2022/7/20 15:56
 * @Author:dengjin
 * @Name: 基础弹框帮助类
 */
public final class DialogHelper {

    private DialogHelper() {
    }

    /**
     * 创建有标题的dialog
     * @param context 上下文
     * @param title 标题
     * @param btnNum 按钮数量
     * @param content 内容
     * @param btnTexts 按钮文本
     */
    public static NormalDialog createTitleNormalDialog(Context context, String title, int btnNum, String content, String... btnTexts) {
        NormalDialog normalDialog = new NormalDialog(context);
        normalDialog.isTitleShow(true)
                .title(title)
                .btnNum(btnNum)
                .content(content)
                .btnText(btnTexts)
                .showAnim(new FadeEnter())
                .dismissAnim(new FadeExit())
                .show();
        return normalDialog;
    }

    /**
     * 创建没有标题的dialog
     * @param context 上下文
     * @param btnNum 按钮数量
     * @param content 内容
     * @param btnTexts 按钮文本
     */
    public static NormalDialog createNoTitleNormalDialog(Context context, int btnNum, String content, String... btnTexts) {
        NormalDialog normalDialog = new NormalDialog(context);
        normalDialog.isTitleShow(false)
                .btnNum(btnNum)
                .content(content)
                .btnText(btnTexts)
                .showAnim(new FadeEnter())
                .dismissAnim(new FadeExit())
                .show();
        return normalDialog;
    }

    /**
     * 创建MaterialDialog
     * @param context 上下文
     * @param btnNum 按钮数量
     * @param content 内容
     * @param btnTexts 按钮文本
     */
    public static MaterialDialog createMaterialDialog(Context context, int btnNum, String content, String... btnTexts){
        MaterialDialog materialDialog = new MaterialDialog(context);
        materialDialog.btnNum(btnNum)
                .content(content)
                .btnText(btnTexts)
                .showAnim(new FadeEnter())
                .dismissAnim(new FadeExit())
                .show();
        return materialDialog;
    }

    /**
     * 创建自定义布局的带三角的popwindow
     * @param context 上下文
     * @param layoutResId 布局id
     * @param anchorView anchorView
     */
    public static BubblePopup createCusBubPopup(Context context, int layoutResId, View anchorView){
        BubblePopup popup = createBubPopup(context, layoutResId);
        popup.anchorView(anchorView)
                .gravity(Gravity.BOTTOM)
                .showAnim(new SlideLeftEnter())
                .dismissAnim(new SlideRightExit())
                .autoDismiss(true)
                .autoDismissDelay(3500)
                .show();
        return popup;
    }

    /**
     * 生成 Popup
     * @param context 上下文
     * @param layoutResId 布局id
     * @return
     */
    public static BubblePopup createBubPopup(Context context, int layoutResId) {
        View inflate = View.inflate(context, layoutResId, null);
        return new BubblePopup(context, inflate);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static ProgressDialog createProgress(Context context, String title) {
        ProgressDialog progressDialog = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressDrawable(context.getResources().getDrawable(R.drawable.update_progress_bg));
//            progressDialog.setMax(100);
        progressDialog.setProgressNumberFormat("%1d KB/%2d KB");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle(title);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 时间选择dialog
     * @param context 上下文
     * @param onPositiveListener 确认
     * @param onNeutralListener 中间按钮（现在）
     * @return dialog
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static AlertDialog createPickDateDialog(Context context,
                                                   OnPositiveListener onPositiveListener,
                                                   OnNeutralListener onNeutralListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_pick_date, null);
        DatePicker datePicker = inflate.findViewById(R.id.picker_date);
        TimePicker timePicker = inflate.findViewById(R.id.picker_time);
        timePicker.setIs24HourView(true);

        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),null);
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));

        builder.setView(inflate);
        builder.setTitle("选择时间");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault());
        builder.setPositiveButton("确定", (dialog, which) -> {
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int dayOfMonth = datePicker.getDayOfMonth();

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            Calendar pickCal = Calendar.getInstance();
            pickCal.set(year, month, dayOfMonth, hour, minute, 0);
            Date time = pickCal.getTime();
            String format = simpleDateFormat.format(time);

            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String dateTime = format1.format(time);
            if (onPositiveListener != null) {
                onPositiveListener.OnPositive(dateTime, format);
            }

            LogUtils.i("tag", "选择的时间=" +dateTime);

            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.setNeutralButton("现在", (dialog, which) -> {
            Date date1= new Date();
            date1.setTime(System.currentTimeMillis());
            String format = simpleDateFormat.format(date1);
            if (onNeutralListener != null) {
                onNeutralListener.OnNeutral(format);
            }
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public static void showRationaleDialog(final PermissionUtils.OnRationaleListener.ShouldRequest shouldRequest) {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shouldRequest.again(true);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shouldRequest.again(false);
                    }
                })
                .setCancelable(false)
                .create()
                .show();

    }

    public static void showOpenAppSettingDialog() {
        Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.launchAppDetailsSettings();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

}