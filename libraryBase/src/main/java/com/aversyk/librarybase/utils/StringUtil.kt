package com.aversyk.librarybase.utils

import android.os.Build
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import java.util.regex.Pattern

/**
 * String工具类
 * Created by Averysk.
 */
object StringUtil {
    /**
     * 校验: 为空对象或者空字符串
     */
    fun isEmptyObjectOrString(obj: Any?): Boolean {
        return null == obj || "null" == obj.toString().trim { it <= ' ' } || "" == obj.toString().trim { it <= ' ' }
    }

    /**
     * 校验: 不为为空对象或者空字符串
     */
    fun isNotEmptyObjectOrString(validateObj: Any?): Boolean {
        return !isEmptyObjectOrString(validateObj)
    }

    /**
     * 校验: 字符串是否为手机号码
     */
    fun isMobileNO(mobiles: String?): Boolean {
        return if (isNotEmptyObjectOrString(mobiles)) {
            val p = Pattern.compile("^(1[0-9])\\d{9}$")
            val m = p.matcher(mobiles)
            m.matches()
        } else {
            false
        }
    }

    /**
     * 校验: 字符串是否由字母或者数字组成的
     */
    fun isCharOrNum(str: String?): Boolean {
        return if (isNotEmptyObjectOrString(str)) {
            val p = Pattern.compile("^[A-Za-z0-9]+$")
            val m = p.matcher(str)
            m.matches()
        } else {
            false
        }
    }

    /**
     * 校验: 字符串是否由数字组成的(是否是整数)
     */
    fun isNumeric(str: String?): Boolean {
        return if (isNotEmptyObjectOrString(str)) {
            val pattern = Pattern.compile("[0-9]*")
            val isNum = pattern.matcher(str)
            isNum.matches()
        } else {
            false
        }
    }

    /**
     * 校验: 字符串是否为十六进制
     */
    fun isHexadecimal(str: String): Boolean {
        val regex = "^[A-Fa-f0-9]{6}|[A-Fa-f0-9]{8}$"
        return str.matches(Regex(regex))
    }

    /**
     * 校验: 字符串是否为空或长度为0
     */
    fun isEmptyString(str: String?): Boolean {
        return null == str || "null" == str || "" == str || str.isEmpty()
    }

    /**
     * 校验: 字符串是否不为空字符串
     */
    fun isNotEmptyString(str: String?): Boolean {
        return !isEmptyString(str)
    }

    /**
     * 校验: 字符串是否不为为空白文本
     */
    fun isNotBlank(str: String?): Boolean {
        return !isBlank(str)
    }

    /**
     * 校验: 字符串是否为空白文本
     */
    fun isBlank(str: String?): Boolean {
        var var1 = 0
        if (str != null && str.length.also { var1 = it } != 0) {
            for (var2 in 0 until var1) {
                if (!Character.isWhitespace(str[var2])) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * 拼接字符串
     * @param strList 需要拼接的字符串集合
     * @return String
     */
    fun concatString(strList: Array<String>): String {
        var length = 0
        for (s in strList) {
            length = +s.length
        }
        val stringBuilder = StringBuilder(length)
        for (s in strList) {
            stringBuilder.append(s)
        }
        return stringBuilder.toString()
    }

    /**
     * 简化字符串
     * @param str   需要被简化的字符串
     * @param length 需要简化的长度
     * @return String
     */
    fun simplifyString(str: String, length: Int): String {
        return if (str.length <= length) str else concatString(
            arrayOf(
                str.substring(0, length),
                "......"
            )
        )
    }

    /**
     * 十六进制低位字符
     */
    private val DIGITS_LOWER = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     * md5字符串转十六进制
     * @param str 必需是MD5格式的字符串
     * @return String
     */
    fun md5ToHexString(str: String): String? {
        return try {
            val messageDigest = MessageDigest.getInstance("MD5")
            bytesToHexString(messageDigest.digest(str.toByteArray(charset_UTF_8)))
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 字符串转字节
     * @param str 需要被转换的字符串
     * @return String
     */
    fun stringToByte(str: String): ByteArray {
        return if (isEmptyString(str)) {
            ByteArray(0)
        } else {
            str.toByteArray(charset_UTF_8)
        }
    }

    /**
     * 字节转字符串
     * @param bytes 需要被转换的字节
     * @return String
     */
    fun byteToString(bytes: ByteArray?): String {
        return if (bytes == null) {
            ""
        } else {
            String(bytes, charset_UTF_8)
        }
    }

    /**
     * 字节转十六进制
     * @param bytes 需要被转换的字节集合
     * @return String
     */
    fun bytesToHexString(bytes: ByteArray?): String {
        return if (bytes == null) "" else bytesToHexString(bytes, DIGITS_LOWER)
    }

    /**
     * 字节转十六进制
     * @param bytes 需要被转换的字节集合
     * @param digits 十六进制低位字符
     * @return String
     */
    private fun bytesToHexString(bytes: ByteArray, digits: CharArray): String {
        val length = bytes.size
        val charsNew = CharArray(length shl 1)
        var index = 0
        var i = 0
        while (index < length) {
            charsNew[i++] = digits[240 and bytes[index].toInt() ushr 4]
            charsNew[i++] = digits[15 and bytes[index].toInt()]
            ++index
        }
        return String(charsNew)
    }

    val charset_UTF_8: Charset
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StandardCharsets.UTF_8
        } else {
            Charset.forName("UTF-8")
        }

    /**
     * 将把数据源HashMap转换成json格式字符串
     * @param map 数据源HashMap
     * @return String
     */
    fun hashMapToJsonString(map: HashMap<String?, Any?>): String {
        var string = StringBuilder("{")
        for ((key, value) in map) {
            string.append("\"").append(key).append("\":")
            string.append("\"").append(value).append("\",")
        }
        string = StringBuilder(string.substring(0, string.lastIndexOf(",")))
        string.append("}")
        return string.toString()
    }
}