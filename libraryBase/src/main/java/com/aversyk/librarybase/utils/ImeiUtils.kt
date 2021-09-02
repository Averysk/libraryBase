package com.aversyk.librarybase.utils

import android.util.Log
import java.security.MessageDigest
import kotlin.math.abs

/**
 * IMEI生成工具类
 *
 * @author Averysk
 *
 * imei由15位数字组成，
 * 前6位(TAC)是型号核准号码，代表手机类型。
 * 接着2位(FAC)是最后装配号，代表产地。
 * 后6位(SNR)是串号，代表生产顺序号。
 * 最后1位 (SP)是检验码。
 *
 * 检验码计算：
 * (1).将偶数位数字分别乘以2，分别计算个位数和十位数之和
 * (2).将奇数位数字相加，再加上上一步算得的值
 * (3).如果得出的数个位是0则校验位为0，否则为10减去个位数
 */
object ImeiUtils {

    /**
     * 通过imei的前14位获取完整的imei(15位)
     * @param imeiString
     * @return
     */
    fun getImeiBy14(imeiString: String): String {
        var retVal = "0"
        val imeiChar = imeiString.toCharArray()
        var resultInt = 0
        var i = 0
        while (i < imeiChar.size) {
            val a = imeiChar[i].toString().toInt()
            i++
            val temp = imeiChar[i].toString().toInt() * 2
            val b = if (temp < 10) temp else temp - 9
            resultInt += a + b
            i++
        }
        resultInt %= 10
        resultInt = if (resultInt == 0) 0 else 10 - resultInt
        retVal = imeiString + resultInt
        //System.out.println("imei:"+imeiString+resultInt);
        return retVal
    }

    // MD5的引入
    fun MD5(inStr: String): String {
        val md5: MessageDigest? = try {
            MessageDigest.getInstance("MD5")
        } catch (e: Exception) {
            //System.out.println(e.toString());
            //e.printStackTrace()
            return ""
        }
        val charArray = inStr.toCharArray()
        val byteArray = ByteArray(charArray.size)
        for (i in charArray.indices) byteArray[i] = charArray[i].toByte()
        val md5Bytes = md5?.digest(byteArray)
        val hexValue = StringBuffer()
        md5Bytes?.forEachIndexed { _, byte ->
            val `val` = abs(byte.toInt())
            Log.d("val : ", `val`.toString() + "")
            if (`val` <= 33 || `val` == 34 || `val` == 39 || `val` == 47 || `val` == 92 || `val` == 124 || `val` > 126) {
                hexValue.append(`val`)
            } else {
                hexValue.append(`val`.toChar())
            }
        }
        return hexValue.toString()
    }
}