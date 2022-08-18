package com.xiangke.dialog;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.xiangke.common.R;

import java.util.Calendar;
import java.util.Date;

/**
 * @Date:2022/8/15 14:20
 * @Author:dengjin
 * @Name:
 */
public class PickDateDialog extends AlertDialog {

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected PickDateDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pick_date);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init(Context context) {
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

        setView(inflate);
        setTitle("选择时间");

    }

}
