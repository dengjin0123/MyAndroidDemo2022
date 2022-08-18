package com.xiangke.myutil;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Date:2022/7/13 15:57
 * @Author:dengjin
 * @Name: 时间工具类
 */
public class DateTimeUtils {
    private static final String TAG = "DateTimeUtils";

    /**
     *
     * @param pattern 时间格式
     * @return
     */
    public static String getTimeStrForPatCur(String pattern) {
        return getTimeStrForPat(pattern, System.currentTimeMillis());
    }

    /**
     *
     *
     * @param pattern 时间格式
     * @return
     */
    public static String getTimeStrForPat(String pattern, long time) {
        SimpleDateFormat df = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return df.format(time);
    }

    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",
                Locale.getDefault());
        return df.format(System.currentTimeMillis());
    }

    public static String getSimpleTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        return df.format(System.currentTimeMillis());
    }

    public static String getCurrentTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss",
                Locale.getDefault());
        return df.format(System.currentTimeMillis());
    }

    public static String getCurrentTimeYMDDHM() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault());
        return df.format(System.currentTimeMillis());
    }

    /**
     * 获取总秒值
     * @param datetime
     * @return
     */
    public static long getTimeToLong(String datetime){
        if (TextUtils.isEmpty(datetime)) {
            return -1;
        }
        long time=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        try {
            Date date = format.parse(datetime);
            time = date.getTime()/1000;
        } catch (ParseException e) {
            Log.d(TAG, "ParseException = "+ e.getMessage());
        }
        return time;
    }

    public static String getHMSString(int h$m$s) {
        String hourString = h$m$s + "";
        if (h$m$s >= 0 && h$m$s < 10) {
            hourString = "0" + h$m$s;
        }
        return hourString;
    }

    public static String getTimeStrForElevenLen(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat df1 = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss:.SSS", Locale.getDefault());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault());
            Date date = new Date();
            try {
                date = df1.parse(time);
            } catch (ParseException e) {
                Log.d(TAG, e.getMessage());
            }
            return df.format(date);
        } else {
            return "";
        }
    }

    public static String getTimeStrYMDHM(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat df1 = new SimpleDateFormat(
                    "yyyyMMddHHmm", Locale.getDefault());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                    Locale.getDefault());
            Date date = new Date();
            try {
                date = df1.parse(time);
            } catch (ParseException e) {
                Log.d(TAG, e.getMessage());
            }
            return df.format(date);
        } else {
            return "";
        }
    }

    public static String getTimeStr(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat df = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = new Date();
            try {
                date = df.parse(time);
            } catch (ParseException e) {
                Log.d(TAG, e.getMessage());
            }
            return df.format(date);
        } else {
            return "";
        }
    }

//	public static String getTimeStrYMDHM(String time) {
//		if (!TextUtils.isEmpty(time)) {
//			SimpleDateFormat df = new SimpleDateFormat(
//					"yyyyMMddHHmm", Locale.getDefault());
//			Date date = new Date();
//			try {
//				date = df.parse(time);
//			} catch (ParseException e) {
//				Log.d(TAG, e.getMessage());
//			}
//			return df.format(date);
//		} else {
//			return "";
//		}
//	}

    public static String getTimeStrYMD(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat df = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            try {
                date = df.parse(time);
            } catch (ParseException e) {
                Log.d(TAG, e.getMessage());
            }
            return df.format(date);
        } else {
            return "";
        }
    }

    /**
     * 获取时
     * @param time
     * @return
     */
    public static String getTimeStrH(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat df = new SimpleDateFormat(
                    "HH", Locale.getDefault());
            Date date = new Date();
            try {
                date = df.parse(time);
            } catch (ParseException e) {
                Log.d(TAG, e.getMessage());
            }
            return df.format(date);
        } else {
            return "";
        }
    }

    /**
     * 获取分
     * @param time
     * @return
     */
    public static String getStrM(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat df1 = new SimpleDateFormat(
                    "HH:mm", Locale.getDefault());
            SimpleDateFormat df = new SimpleDateFormat("mm",
                    Locale.getDefault());
            Date date = new Date();
            try {
                date = df1.parse(time);
            } catch (ParseException e) {
                Log.d(TAG, e.getMessage());
            }
            return df.format(date);
        } else {
            return "";
        }
    }

    public static String getTimeStrYMDHMS(String time) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat df1 = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm", Locale.getDefault());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault());
            Date date = new Date();
            try {
                date = df1.parse(time);
            } catch (ParseException e) {
                Log.d(TAG, e.getMessage());
            }
            return df.format(date);
        } else {
            return "";
        }
    }

    public static String getTimeStrMS(String time){
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat df = new SimpleDateFormat("mm:ss",
                    Locale.getDefault());
            Date date = new Date();
            try {
                date = df.parse(time);
            } catch (ParseException e) {
                Log.d(TAG, e.getMessage());
            }
            return df.format(date);
        } else {
            return "";
        }
    }

//	/**
//	 * 获取时间差(时分)
//	 * @param startTimeString
//	 * @param endTimeString
//	 * @return
//	 */
//	public static long getDifflong(String startTimeString,
//			String endTimeString) {
//
//		DateTimeFormatter format = DateTimeFormat
//				.forPattern("yyyy-MM-dd");
//		DateTime startTime = null;
//		DateTime endTime = null;
//		try {
//			startTime = DateTime.parse(startTimeString, format); // 开始时间
//			endTime = DateTime.parse(endTimeString, format);// 结束时间
//		} catch (Exception e) {
//			Log.d(TAG, e.getMessage());
//		}
//		long diff = endTime.getMillis() - startTime.getMillis();// 获得时间差
//		return diff;
//	}

//	/**
//	 * 获取时间差(时分)
//	 * @param startTimeString
//	 * @param endTimeString
//	 * @return
//	 */
//	public static long getDifflongForHM(String startTimeString,
//			String endTimeString) {
//
//		DateTimeFormatter format = DateTimeFormat
//				.forPattern("HH:mm");
//		DateTime startTime = null;
//		DateTime endTime = null;
//		try {
//			startTime = DateTime.parse(startTimeString, format); // 开始时间
//			endTime = DateTime.parse(endTimeString, format);// 结束时间
//		} catch (Exception e) {
//			Log.d(TAG, e.getMessage());
//		}
//		long diff = endTime.getMillis() - startTime.getMillis();// 获得时间差
//		return diff;
//	}

    public static String getStringForlong(long millis){

        String dateTime="";

        if(millis >0){

            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");

            Date date=new Date(millis);

            dateTime= dateFormat.format(date);

        }

        return dateTime;
    }

    public static String getStringForlongYHDHMS(long millis){

        String dateTime="";

        if(millis >0){

            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date=new Date(millis);

            dateTime= dateFormat.format(date);

        }

        return dateTime;
    }

    public static String getStringForDate(Date date){

        String dateTime="";

        if(date !=null){

            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");

            dateTime= dateFormat.format(date);

        }

        return dateTime;
    }

    /**
     * 判断指定字符串是否符合日期的格式
     *
     * @param dateStr
     * @return DateTimeUtil.isDateStr("1990-01-32 00:00:00") = false
     *         DateTimeUtil.isDateStr("1990-01 00:00") = false
     *         DateTimeUtil.isDateStr("1990-1-1 00:00:00") = true
     *         DateTimeUtil.isDateStr("1990-01-3") = true
     *         DateTimeUtil.isDateStr("1990-21-31 00:00:00") = false
     *         DateTimeUtil.isDateStr("1990年01月3日 00时00分00秒") = false
     */
    public static boolean isDateStr(final String dateStr,final String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setLenient(false);
        try {
            format.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 将一个字符串解析为一个日期对象。字符串格式由调用方法指定
     *
     * @param dateStr
     *            要解析的日期字符串
     * @param pattern
     *            日期格式的模式字符串
     * @return 日期对象
     *
     *         不能被解析的日期格式的模式字符串
     */
    public static Date parseStringToDate(final String dateStr,
                                         final String pattern) {
        if (TextUtils.isEmpty(dateStr)) {
            return null;
        }
        if (TextUtils.isEmpty(pattern)) {
            return parseStringToDate(dateStr);
        }
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将一个字符串解析为一个日期对象。字符串格式为：yyyy-MM-dd
     *
     * @param dateStr
     *            要解析的日期字符串
     * @return 日期对象
     *
     */
    public static Date parseStringToDate(final String dateStr) {
        if (TextUtils.isEmpty(dateStr)) {
            return null;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
