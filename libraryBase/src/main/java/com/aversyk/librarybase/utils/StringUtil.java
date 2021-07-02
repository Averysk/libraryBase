package com.aversyk.librarybase.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * String工具类
 * Created by Averysk.
 */
public class StringUtil {

    /**
     * 校验: 为空对象或者空字符串
     */
    public static boolean isEmptyObjectOrString(Object obj) {
        return null == obj || "null".equals(obj.toString().trim()) || "".equals(obj.toString().trim());
    }

    /**
     * 校验: 不为为空对象或者空字符串
     */
    public static boolean isNotEmptyObjectOrString(Object validateObj) {
        return !isEmptyObjectOrString(validateObj);
    }

    /**
     * 校验: 字符串是否为手机号码
     */
    public static boolean isMobileNO(String mobiles) {
        if (isNotEmptyObjectOrString(mobiles)) {
            Pattern p = compile("^(1[0-9])\\d{9}$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } else {
            return false;
        }
    }

    /**
     * 校验: 字符串是否由字母或者数字组成的
     */
    public static boolean isCharOrNum(String str) {
        if (isNotEmptyObjectOrString(str)) {
            Pattern p = compile("^[A-Za-z0-9]+$");
            Matcher m = p.matcher(str);
            return m.matches();
        } else {
            return false;
        }
    }

    /**
     * 校验: 字符串是否由数字组成的(是否是整数)
     */
    public static boolean isNumeric(String str) {
        if (isNotEmptyObjectOrString(str)) {
            Pattern pattern = compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            return isNum.matches();
        } else {
            return false;
        }
    }

    /**
     * 校验: 字符串是否为十六进制
     */
    public static boolean isHexadecimal(String str) {
        String regex = "^[A-Fa-f0-9]{6}|[A-Fa-f0-9]{8}$";
        return str.matches(regex);
    }

    /**
     * 校验: 字符串是否为空或长度为0
     */
    public static boolean isEmptyString(String str) {
        return null == str || "null".equals(str)  || "".equals(str)|| str.length() == 0;
    }

    /**
     * 校验: 字符串是否不为空字符串
     */
    public static boolean isNotEmptyString(String str) {
        return !isEmptyString(str);
    }

    /**
     * 校验: 字符串是否不为为空白文本
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 校验: 字符串是否为空白文本
     */
    public static boolean isBlank(String str) {
        int var1;
        if (str != null && (var1 = str.length()) != 0) {
            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isWhitespace(str.charAt(var2))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 拼接字符串
     * @param strList 需要拼接的字符串集合
     * @return String
     */
    public static String concatString(String[] strList) {
        int length = 0;
        for (String s : strList) {
            length =+ s.length();
        }
        StringBuilder stringBuilder = new StringBuilder(length);
        for (String s : strList) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    /**
     * 简化字符串
     * @param str   需要被简化的字符串
     * @param length 需要简化的长度
     * @return String
     */
    public static String simplifyString(String str, int length) {
        return str.length() <= length ? str : concatString(new String[]{str.substring(0, length), "......"});
    }

    /**
     * 十六进制低位字符
     */
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * md5字符串转十六进制
     * @param str 必需是MD5格式的字符串
     * @return String
     */
    public static String md5ToHexString(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return bytesToHexString(messageDigest.digest(str.getBytes(getCharset_UTF_8())));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串转字节
     * @param str 需要被转换的字符串
     * @return String
     */
    public static byte[] stringToByte(String str) {
        if (isEmptyString(str)) {
            return new byte[0];
        } else {
            return str.getBytes(getCharset_UTF_8());
        }
    }

    /**
     * 字节转字符串
     * @param bytes 需要被转换的字节
     * @return String
     */
    public static String byteToString(byte[] bytes) {
        if (bytes == null) {
            return "";
        } else {
            return new String(bytes, getCharset_UTF_8());
        }
    }

    /**
     * 字节转十六进制
     * @param bytes 需要被转换的字节集合
     * @return String
     */
    public static String bytesToHexString(byte[] bytes) {
        return bytes == null ? "" : bytesToHexString(bytes, DIGITS_LOWER);
    }

    /**
     * 字节转十六进制
     * @param bytes 需要被转换的字节集合
     * @param digits 十六进制低位字符
     * @return String
     */
    private static String bytesToHexString(byte[] bytes, char[] digits) {
        int length = bytes.length;
        char[] charsNew = new char[length << 1];
        int index = 0;
        for (int i = 0; index < length; ++index) {
            charsNew[i++] = digits[(240 & bytes[index]) >>> 4];
            charsNew[i++] = digits[15 & bytes[index]];
        }
        return new String(charsNew);
    }

    public static Charset getCharset_UTF_8(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return StandardCharsets.UTF_8;
        } else {
            return Charset.forName("UTF-8");
        }
    }


    /**
     * 将把数据源HashMap转换成json格式字符串
     * @param map 数据源HashMap
     * @return String
     */
    public static String hashMapToJsonString(HashMap<String, Object> map) {
        StringBuilder string = new StringBuilder("{");
        for (Map.Entry<String, Object> e : map.entrySet()) {
            string.append("\"").append(e.getKey()).append("\":");
            string.append("\"").append(e.getValue()).append("\",");
        }
        string = new StringBuilder(string.substring(0, string.lastIndexOf(",")));
        string.append("}");
        return string.toString();
    }

}
