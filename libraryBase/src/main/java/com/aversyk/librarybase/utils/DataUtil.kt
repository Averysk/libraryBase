package com.aversyk.librarybase.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 数据转换工具
 *
 * @author Averysk
 */
@SuppressLint("SimpleDateFormat")
object DataUtil {

    const val DATA_FORMAT_01 = "yyyy"
    const val  DATA_FORMAT_02 = "yyyyMM"
    const val  DATA_FORMAT_03 = "yyyy-MM"
    const val  DATA_FORMAT_04 = "yyyy.MM"
    const val  DATA_FORMAT_05 = "yyyyMMdd"
    const val  DATA_FORMAT_06 = "yyyy-MM-dd"
    const val  DATA_FORMAT_07 = "yyyy.MM.dd"
    const val  DATA_FORMAT_08 = "yyyyMMddHH"
    const val  DATA_FORMAT_09 = "yyyy-MM-dd HH"
    const val  DATA_FORMAT_10 = "yyyy.MM.dd HH"
    const val  DATA_FORMAT_11 = "yyyyMMddHHmm"
    const val  DATA_FORMAT_12 = "yyyy-MM-dd HH:mm"
    const val  DATA_FORMAT_13 = "yyyy.MM.dd HH:mm"
    const val  DATA_FORMAT_14 = "yyyyMMddHHmmss"
    const val  DATA_FORMAT_15 = "yyyy-MM-dd HH:mm:ss"
    const val  DATA_FORMAT_16 = "yyyy.MM.dd HH:mm:ss"
    const val  DATA_FORMAT_17 = "yyyyMMddHHmmssSSS"
    const val  DATA_FORMAT_18 = "yyyy-MM-dd HH:mm:ss:SSS"
    const val  DATA_FORMAT_19 = "yyyy.MM.dd HH:mm:ss:SSS"
    const val  DATA_FORMAT_20 = "MM"
    const val  DATA_FORMAT_21 = "MMdd"
    const val  DATA_FORMAT_22 = "MM-dd"
    const val  DATA_FORMAT_23 = "MM.dd"
    const val  DATA_FORMAT_24 = "MMddHH"
    const val  DATA_FORMAT_25 = "MM-dd HH"
    const val  DATA_FORMAT_26 = "MM.dd HH"
    const val  DATA_FORMAT_27 = "MMddHHmm"
    const val  DATA_FORMAT_28 = "MM-dd HH:mm"
    const val  DATA_FORMAT_29 = "MM.dd HH:mm"
    const val  DATA_FORMAT_30 = "MMddHHmmss"
    const val  DATA_FORMAT_31 = "MM-dd HH:mm:ss"
    const val  DATA_FORMAT_32 = "MM.dd HH:mm:ss"
    const val  DATA_FORMAT_33 = "MMddHHmmssSSS"
    const val  DATA_FORMAT_34 = "MM-dd HH:mm:ss:SSS"
    const val  DATA_FORMAT_35 = "MM.dd HH:mm:ss:SSS"
    const val  DATA_FORMAT_36 = "dd"
    const val  DATA_FORMAT_37 = "ddHH"
    const val  DATA_FORMAT_38 = "dd HH"
    const val  DATA_FORMAT_39 = "ddHHmm"
    const val  DATA_FORMAT_40 = "dd HH:mm"
    const val  DATA_FORMAT_41 = "ddHHmmss"
    const val  DATA_FORMAT_42 = "dd HH:mm:ss"
    const val  DATA_FORMAT_43 = "ddHHmmssSSS"
    const val  DATA_FORMAT_44 = "dd HH:mm:ss:SSS"
    const val  DATA_FORMAT_45 = "HH"
    const val  DATA_FORMAT_46 = "HHmm"
    const val  DATA_FORMAT_47 = "HH:mm"
    const val  DATA_FORMAT_48 = "HHmmss"
    const val  DATA_FORMAT_49 = "HH:mm:ss"
    const val  DATA_FORMAT_50 = "HHmmssSSS"
    const val  DATA_FORMAT_51 = "HH:mm:ss:SSS"
    const val  DATA_FORMAT_52 = "mm"
    const val  DATA_FORMAT_53 = "mmss"
    const val  DATA_FORMAT_54 = "mm:ss"
    const val  DATA_FORMAT_55 = "mmssSSS"
    const val  DATA_FORMAT_56 = "mm:ss:SSS"
    const val  DATA_FORMAT_57 = "ss"
    const val  DATA_FORMAT_58 = "ssSSS"
    const val  DATA_FORMAT_59 = "ss:SSS"
    const val  DATA_FORMAT_60 = "SSS"

    /**
     * 获取当前系统时间
     *
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return String
     */
    fun getStringData(aFormat: String?): String {
        return getStringData(Date(), aFormat)
    }

    /**
     * 将 毫秒 格式的时间转换为 新的 String 形式的日期
     *
     * @param millis  毫秒 格式的日期
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return String
     */
    fun getStringData(millis: Long?, aFormat: String?): String {
        if (millis == null)
            return ""
        val date = Date(millis)
        val sf = SimpleDateFormat(aFormat)
        // 设置时区，+08是北京时间
        sf.timeZone = TimeZone.getTimeZone("GMT+08")
        return sf.format(date)
    }

    /**
     * 将 Date 格式的日期转换为 新的 String 形式的日期
     *
     * @param aDate   Date格式的日期
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return
     */
    fun getStringData(aDate: Date?, aFormat: String?): String {
        if (aDate == null) {
            return ""
        }
        val sf = SimpleDateFormat(aFormat)
        // 设置时区，+08是北京时间
        sf.timeZone = TimeZone.getTimeZone("GMT+08")
        return sf.format(aDate)
    }

    /**
     * 将 字符串 形式的日期转换为 新的 String 形式的日期 (字符串格式 - > 字符串格式)
     *
     * @param aString 字符串 形式的日期
     * @param sFormat 字符串 形式的日期的当前格式化类型
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return
     */
    fun getStringData(aString: String?, sFormat: String?, aFormat: String?): String {
        val date = getDataFromStringData(aString, sFormat) ?: return ""
        return getStringData(date, aFormat)
    }

    /**
     * 将 字符串 格式的日期转换为 Date 格式的日期
     *
     * @param aString 字符串 格式的日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getDataFromStringData(aString: String?, sFormat: String?): Date? {
        var date: Date? = null
        try {
            val sf = SimpleDateFormat(sFormat)
            date = sf.parse(aString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    /**
     * 将 字符串 形式的日期 加上一定的时间 再转换为 新的 String 形式的日期 (字符串格式 - > 字符串格式)
     *
     * @param aString         字符串 形式的日期
     * @param sFormat         字符串 形式的日期的当前格式化类型
     * @param addMilliseconds 需要加上的时间(毫秒)
     * @return
     */
    fun getStringDataAddMilliseconds(aString: String?, sFormat: String?, addMilliseconds: Long): String {
        val sf = SimpleDateFormat(sFormat)
        try {
            val time = sf.parse(aString).time
            val millis = time + addMilliseconds
            return sf.format(Date(millis))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 将 字符串 格式的日期转换为 String 格式的星期几
     *
     * @param aString 字符串 形式的日期
     * @param sFormat 字符串 形式的日期的当前格式化类型
     * @return 返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
     */
    fun getStringDayFoWeek(aString: String, sFormat: String?): String {
        val cal = Calendar.getInstance()
        if (aString == "") {
            cal.time = Date(System.currentTimeMillis())
        } else {
            cal.time = Date(
                getDataFromStringData(aString, sFormat)!!.time
            )
        }
        val week = cal[Calendar.DAY_OF_WEEK]
        var strResult = ""
        when (week) {
            1 -> {
                strResult = "星期日"
            }
            2 -> {
                strResult = "星期一"
            }
            3 -> {
                strResult = "星期二"
            }
            4 -> {
                strResult = "星期三"
            }
            5 -> {
                strResult = "星期四"
            }
            6 -> {
                strResult = "星期五"
            }
            7 -> {
                strResult = "星期六"
            }
        }
        return strResult
    }

    /**
     * 将 字符串 格式的日期转换为 String 格式的周几
     *
     * @param aString 字符串 形式的日期
     * @param sFormat 字符串 形式的日期的当前格式化类型
     * @return 返回1是周日、2是周一、3是周二、4是周三、5是周四、6是周五、7是周六
     */
    fun getStringDayFoWeek2(aString: String, sFormat: String?): String {
        val cal = Calendar.getInstance()
        if (aString == "") {
            cal.time = Date(System.currentTimeMillis())
        } else {
            cal.time = Date(
                getDataFromStringData(aString, sFormat)!!.time
            )
        }
        val week = cal[Calendar.DAY_OF_WEEK]
        var strResult = ""
        when (week) {
            1 -> {
                strResult = "周日"
            }
            2 -> {
                strResult = "周一"
            }
            3 -> {
                strResult = "周二"
            }
            4 -> {
                strResult = "周三"
            }
            5 -> {
                strResult = "周四"
            }
            6 -> {
                strResult = "周五"
            }
            7 -> {
                strResult = "周六"
            }
        }
        return strResult
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间  yyyyMMddHHmmss
     * @param bdate  较大的时间  yyyyMMddHHmmss
     * @return 相差天数
     */
    fun getDaysBetween(smdate: Date?, bdate: Date?): Int {
        val smDateStr = getStringData(smdate, DATA_FORMAT_05)
        val bDateStr = getStringData(bdate, DATA_FORMAT_05)
        val smDateNew = getDataFromStringData(smDateStr, DATA_FORMAT_05)
        val bDateNew = getDataFromStringData(bDateStr, DATA_FORMAT_05)
        if (smDateNew != null && bDateNew != null) {
            val cal = Calendar.getInstance()
            cal.time = smDateNew
            val time1 = cal.timeInMillis
            cal.time = bDateNew
            val time2 = cal.timeInMillis
            val betweenDays = (time2 - time1) / (1000 * 3600 * 24)
            return betweenDays.toString().toInt()
        }
        return -1
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间 yyyyMMddHHmmss
     * @param bdate  较大的时间 yyyyMMddHHmmss
     * @return 相差天数
     */
    @Throws(ParseException::class)
    fun getDaysBetween(smdate: String, bdate: String): Int {
        var smdate = smdate
        var bdate = bdate
        if (smdate.length >= 8) {
            smdate = smdate.substring(0, 8)
        }
        if (bdate.length >= 8) {
            bdate = bdate.substring(0, 8)
        }
        val smDateNew = getDataFromStringData(smdate, DATA_FORMAT_05)
        val bDateNew = getDataFromStringData(bdate, DATA_FORMAT_05)
        if (smDateNew != null && bDateNew != null) {
            val cal = Calendar.getInstance()
            cal.time = smDateNew
            val time1 = cal.timeInMillis
            cal.time = bDateNew
            val time2 = cal.timeInMillis
            val betweenDays = (time2 - time1) / (1000 * 3600 * 24)
            return betweenDays.toString().toInt()
        }
        return -1
    }


    /**
     * 获取用户年龄
     * 将 Date 格式的日期转换为 新的 String 形式的年龄
     *
     * @param aDate   Date格式的日期 (生日)
     * @param aFormat 格式化要求 如:yyyyMMddHHmmssSSS
     * @return String
     */
    fun getStringAge(aDate: Date?, aFormat: String?): String {
        //获取当前系统时间
        val cal = Calendar.getInstance()
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(aDate)) {
            return ""
        }
        //取出系统当前时间的年、月、日部分
        val yearNow = cal[Calendar.YEAR]
        val monthNow = cal[Calendar.MONTH]
        val dayOfMonthNow = cal[Calendar.DAY_OF_MONTH]

        //将日期设置为出生日期
        cal.time = aDate
        //取出出生日期的年、月、日部分
        val yearBirth = cal[Calendar.YEAR]
        val monthBirth = cal[Calendar.MONTH]
        val dayOfMonthBirth = cal[Calendar.DAY_OF_MONTH]
        //当前年份与出生年份相减，初步计算年龄
        var age = yearNow - yearBirth
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--
            } else {
                age--
            }
        }
        return age.toString()
    }

}