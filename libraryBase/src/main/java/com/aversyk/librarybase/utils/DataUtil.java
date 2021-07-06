package com.aversyk.librarybase.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * 数据转换工具
 *
 * @author Averysk
 */
@SuppressLint("SimpleDateFormat")
public class DataUtil {

    public static String DATA_FORMAT_01 = "yyyy";
    public static String DATA_FORMAT_02 = "yyyyMM";
    public static String DATA_FORMAT_03 = "yyyy-MM";
    public static String DATA_FORMAT_04 = "yyyy.MM";
    public static String DATA_FORMAT_05 = "yyyyMMdd";
    public static String DATA_FORMAT_06 = "yyyy-MM-dd";
    public static String DATA_FORMAT_07 = "yyyy.MM.dd";
    public static String DATA_FORMAT_08 = "yyyyMMddHH";
    public static String DATA_FORMAT_09 = "yyyy-MM-dd HH";
    public static String DATA_FORMAT_10 = "yyyy.MM.dd HH";
    public static String DATA_FORMAT_11 = "yyyyMMddHHmm";
    public static String DATA_FORMAT_12 = "yyyy-MM-dd HH:mm";
    public static String DATA_FORMAT_13 = "yyyy.MM.dd HH:mm";
    public static String DATA_FORMAT_14 = "yyyyMMddHHmmss";
    public static String DATA_FORMAT_15 = "yyyy-MM-dd HH:mm:ss";
    public static String DATA_FORMAT_16 = "yyyy.MM.dd HH:mm:ss";
    public static String DATA_FORMAT_17 = "yyyyMMddHHmmssSSS";
    public static String DATA_FORMAT_18 = "yyyy-MM-dd HH:mm:ss:SSS";
    public static String DATA_FORMAT_19 = "yyyy.MM.dd HH:mm:ss:SSS";
    public static String DATA_FORMAT_20 = "MM";
    public static String DATA_FORMAT_21 = "MMdd";
    public static String DATA_FORMAT_22 = "MM-dd";
    public static String DATA_FORMAT_23 = "MM.dd";
    public static String DATA_FORMAT_24 = "MMddHH";
    public static String DATA_FORMAT_25 = "MM-dd HH";
    public static String DATA_FORMAT_26 = "MM.dd HH";
    public static String DATA_FORMAT_27 = "MMddHHmm";
    public static String DATA_FORMAT_28 = "MM-dd HH:mm";
    public static String DATA_FORMAT_29 = "MM.dd HH:mm";
    public static String DATA_FORMAT_30 = "MMddHHmmss";
    public static String DATA_FORMAT_31 = "MM-dd HH:mm:ss";
    public static String DATA_FORMAT_32 = "MM.dd HH:mm:ss";
    public static String DATA_FORMAT_33 = "MMddHHmmssSSS";
    public static String DATA_FORMAT_34 = "MM-dd HH:mm:ss:SSS";
    public static String DATA_FORMAT_35 = "MM.dd HH:mm:ss:SSS";
    public static String DATA_FORMAT_36 = "dd";
    public static String DATA_FORMAT_37 = "ddHH";
    public static String DATA_FORMAT_38 = "dd HH";
    public static String DATA_FORMAT_39 = "ddHHmm";
    public static String DATA_FORMAT_40 = "dd HH:mm";
    public static String DATA_FORMAT_41 = "ddHHmmss";
    public static String DATA_FORMAT_42 = "dd HH:mm:ss";
    public static String DATA_FORMAT_43 = "ddHHmmssSSS";
    public static String DATA_FORMAT_44 = "dd HH:mm:ss:SSS";
    public static String DATA_FORMAT_45 = "HH";
    public static String DATA_FORMAT_46 = "HHmm";
    public static String DATA_FORMAT_47 = "HH:mm";
    public static String DATA_FORMAT_48 = "HHmmss";
    public static String DATA_FORMAT_49 = "HH:mm:ss";
    public static String DATA_FORMAT_50 = "HHmmssSSS";
    public static String DATA_FORMAT_51 = "HH:mm:ss:SSS";
    public static String DATA_FORMAT_52 = "mm";
    public static String DATA_FORMAT_53 = "mmss";
    public static String DATA_FORMAT_54 = "mm:ss";
    public static String DATA_FORMAT_55 = "mmssSSS";
    public static String DATA_FORMAT_56 = "mm:ss:SSS";
    public static String DATA_FORMAT_57 = "ss";
    public static String DATA_FORMAT_58 = "ssSSS";
    public static String DATA_FORMAT_59 = "ss:SSS";
    public static String DATA_FORMAT_60 = "SSS";


    /**
     * 获取当前系统时间
     *
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return String
     */
    public static String getStringData(String aFormat) {
        return getStringData(new Date(), aFormat);
    }

    /**
     * 将 毫秒 格式的时间转换为 新的 String 形式的日期
     *
     * @param millis  毫秒 格式的日期
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return String
     */
    public static String getStringData(Long millis, String aFormat) {
        Date date = new Date(millis);
        if (date == null) {
            return "";
        }
        SimpleDateFormat sf = new SimpleDateFormat(aFormat);
        // 设置时区，+08是北京时间
        sf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return sf.format(date);
    }

    /**
     * 将 Date 格式的日期转换为 新的 String 形式的日期
     *
     * @param aDate   Date格式的日期
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return
     */
    public static String getStringData(Date aDate, String aFormat) {
        if (aDate == null) {
            return "";
        }
        SimpleDateFormat sf = new SimpleDateFormat(aFormat);
        // 设置时区，+08是北京时间
        sf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return sf.format(aDate);
    }

    /**
     * 将 字符串 形式的日期转换为 新的 String 形式的日期 (字符串格式 - > 字符串格式)
     *
     * @param aString 字符串 形式的日期
     * @param sFormat 字符串 形式的日期的当前格式化类型
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return
     */
    public static String getStringData(String aString, String sFormat, String aFormat) {
        Date date = getDataFromStringData(aString, sFormat);
        if (date == null) {
            return "";
        }
        return getStringData(date, aFormat);
    }

    /**
     * 将 字符串 格式的日期转换为 Date 格式的日期
     *
     * @param aString 字符串 格式的日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getDataFromStringData(String aString, String sFormat) {
        Date date = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat(sFormat);
            date = sf.parse(aString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 将 字符串 形式的日期 加上一定的时间 再转换为 新的 String 形式的日期 (字符串格式 - > 字符串格式)
     *
     * @param aString         字符串 形式的日期
     * @param sFormat         字符串 形式的日期的当前格式化类型
     * @param addMilliseconds 需要加上的时间(毫秒)
     * @return
     */
    public static String getStringDataAddMilliseconds(String aString, String sFormat, long addMilliseconds) {
        SimpleDateFormat sf = new SimpleDateFormat(sFormat);
        try {
            long time = sf.parse(aString).getTime();
            long millis = time + addMilliseconds;
            return sf.format(new Date(millis));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将 字符串 格式的日期转换为 String 格式的星期几
     *
     * @param aString 字符串 形式的日期
     * @param sFormat 字符串 形式的日期的当前格式化类型
     * @return 返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
     */
    public static String getStringDayFoWeek(String aString, String sFormat) {
        Calendar cal = Calendar.getInstance();
        if (aString.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            cal.setTime(new Date(getDataFromStringData(aString, sFormat).getTime()));
        }
        int week = cal.get(Calendar.DAY_OF_WEEK);
        String strResult = "";
        if (week == 1) {
            strResult = "星期日";
        } else if (week == 2) {
            strResult = "星期一";
        } else if (week == 3) {
            strResult = "星期二";
        } else if (week == 4) {
            strResult = "星期三";
        } else if (week == 5) {
            strResult = "星期四";
        } else if (week == 6) {
            strResult = "星期五";
        } else if (week == 7) {
            strResult = "星期六";
        }
        return strResult;
    }

    /**
     * 将 字符串 格式的日期转换为 String 格式的周几
     *
     * @param aString 字符串 形式的日期
     * @param sFormat 字符串 形式的日期的当前格式化类型
     * @return 返回1是周日、2是周一、3是周二、4是周三、5是周四、6是周五、7是周六
     */
    public static String getStringDayFoWeek2(String aString, String sFormat) {
        Calendar cal = Calendar.getInstance();
        if (aString.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            cal.setTime(new Date(getDataFromStringData(aString, sFormat).getTime()));
        }
        int week = cal.get(Calendar.DAY_OF_WEEK);
        String strResult = "";
        if (week == 1) {
            strResult = "周日";
        } else if (week == 2) {
            strResult = "周一";
        } else if (week == 3) {
            strResult = "周二";
        } else if (week == 4) {
            strResult = "周三";
        } else if (week == 5) {
            strResult = "周四";
        } else if (week == 6) {
            strResult = "周五";
        } else if (week == 7) {
            strResult = "周六";
        }
        return strResult;
    }


    /**
     * 获取用户年龄
     * 将 Date 格式的日期转换为 新的 String 形式的年龄
     *
     * @param aDate   Date格式的日期 (生日)
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return String
     */
    public String getStringAge(Date aDate, String aFormat) {
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(aDate)) {
            return "";
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        //将日期设置为出生日期
        cal.setTime(aDate);
        //取出出生日期的年、月、日部分
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return String.valueOf(age);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间  yyyyMMddHHmmss
     * @param bdate  较大的时间  yyyyMMddHHmmss
     * @return 相差天数
     */
    public static int getDaysBetween(Date smdate, Date bdate) {
        String smDateStr = getStringData(smdate, DATA_FORMAT_05);
        String bDateStr = getStringData(bdate, DATA_FORMAT_05);
        Date smDateNew = getDataFromStringData(smDateStr, DATA_FORMAT_05);
        Date bDateNew = getDataFromStringData(bDateStr, DATA_FORMAT_05);
        if (smDateNew != null && bDateNew != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(smDateNew);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bDateNew);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        }
        return -1;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间 yyyyMMddHHmmss
     * @param bdate  较大的时间 yyyyMMddHHmmss
     * @return 相差天数
     */
    public static int getDaysBetween(String smdate, String bdate) throws ParseException {
        if (smdate.length() >= 8) {
            smdate = smdate.substring(0, 8);
        }
        if (bdate.length() >= 8) {
            bdate = bdate.substring(0, 8);
        }
        Date smDateNew = getDataFromStringData(smdate, DATA_FORMAT_05);
        Date bDateNew = getDataFromStringData(bdate, DATA_FORMAT_05);
        if (smDateNew != null && bDateNew != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(smDateNew);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bDateNew);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        }
        return -1;
    }

}
