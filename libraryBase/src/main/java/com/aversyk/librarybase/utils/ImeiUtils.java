package com.aversyk.librarybase.utils;

import android.util.Log;

import java.security.MessageDigest;

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
 *
 */
public class ImeiUtils {

    /**
     * 通过imei的前14位获取完整的imei(15位)
     * @param imeiString
     * @return
     */
    public static String getImeiBy14(String imeiString) {
        String retVal = "0";

        char[] imeiChar=imeiString.toCharArray();
        int resultInt=0;
        for (int i = 0; i < imeiChar.length; i++) {
            int a= Integer.parseInt(String.valueOf(imeiChar[i]));
            i++;
            final int temp= Integer.parseInt(String.valueOf(imeiChar[i]))*2;
            final int b=temp<10?temp:temp-9;
            resultInt+=a+b;
        }
        resultInt%=10;
        resultInt=resultInt==0?0:10-resultInt;
        retVal = imeiString+resultInt;
        //System.out.println("imei:"+imeiString+resultInt);

        return retVal;
    }
    
    // MD5的引入
    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            //System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
/*
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
*/
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = Math.abs((int) md5Bytes[i]);
            Log.d("val : " ,  val + "");
            if (val <= 33 || val == 34 || val == 39 || val == 47 || val == 92 || val == 124 || val > 126){
                hexValue.append(val);
            } else {
                hexValue.append((char)val);
            }
        }
        return hexValue.toString();
    }


}
