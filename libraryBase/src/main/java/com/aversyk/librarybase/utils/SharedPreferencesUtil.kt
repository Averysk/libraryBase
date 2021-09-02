package com.aversyk.librarybase.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64
import org.json.JSONException
import org.json.JSONObject
import java.io.*

/**
 * 应用存储数据的公共缓存
 *
 * @author Averysk
 */
object SharedPreferencesUtil {

    /**
     * 保存对象实体 - 需实现 Serializable
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param ob      // 缓存对你
     * @return boolean  // 是否成功
     */
    fun savaObject(context: Context?, spName: String?, key: String?, ob: Any?): Boolean {
        if (context == null) {
            return false
        }
        if (ob == null) {
            return false
        }
        var falg = false
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        // 创建字节输出
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            // 创建对象输出流，并封装字节流
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            // 将对象写入字节流
            oos.writeObject(ob)
            // 将字节流编码成base64的字符窜
            val oAuthBase64 = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
            falg = preferences.edit().putString(key, oAuthBase64).commit()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                oos?.close()
                baos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return falg
    }

    /**
     * 获取存储的对象实体 - 需实现 Serializable
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return Object   // 对象实体
     */
    fun getObject(context: Context?, spName: String?, key: String?): Any? {
        if (context == null) {
            return null
        }
        var ob: Any? = null
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        val productBase64: String? = preferences.getString(key, "")
        // 读取字节
        val base64 = Base64.decode(productBase64?.toByteArray(), Base64.DEFAULT)
        // 封装到字节流
        var bais: ByteArrayInputStream? = null
        var bis: ObjectInputStream? = null
        try {
            // 再次封装
            bais = ByteArrayInputStream(base64)
            bis = ObjectInputStream(bais)
            // 读取对象
            ob = bis.readObject()
        } catch (e: Exception) {
            // e.printStackTrace();
        } finally {
            try {
                bis?.close()
                bais?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return ob
    }

    /**
     * 移除某个缓存对象 - 根据key
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     */
    @SuppressLint("CommitPrefEdits")
    fun removeAct(context: Context?, spName: String?, key: String?) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        preferences.edit().remove(key).apply()
    }

    /**
     * 清除某个缓存对象 - 根据spName(将清除当前缓存名下所有缓存)
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     */
    fun clearByFileName(context: Context?, spName: String?) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        // 使用 apply() 代替； commit() 立即将其数据写入持久存储，而 apply() 将在后台处理它
        //preferences.edit().clear().commit();
        preferences.edit().clear().apply()
    }

    /**
     * 存储int数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // int数据
     */
    fun setIntValue(context: Context?, spName: String?, key: String?, value: Int) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        preferences.edit().putInt(key, value).apply()
    }

    /**
     * 获取int数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return int
     */
    fun getIntValue(context: Context?, spName: String?, key: String?): Int {
        return getIntValue(context, spName, key, 0)
    }

    /**
     * 获取int数据
     *
     * @param context  // 建议使用ApplicationContext
     * @param spName   // 缓存名称
     * @param key      // 缓存键值
     * @param defValue // 未数据数据返回的默认值
     * @return int
     */
    fun getIntValue(context: Context?, spName: String?, key: String?, defValue: Int): Int {
        if (context == null) {
            return defValue
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        return preferences.getInt(key, defValue)
    }

    /**
     * 存储float数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // float数据
     */
    fun setFloatValue(context: Context?, spName: String?, key: String?, value: Float) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        preferences.edit().putFloat(key, value).apply()
    }

    /**
     * 获取float数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return float
     */
    fun getFloatValue(context: Context?, spName: String?, key: String?): Float {
        return getFloatValue(context, spName, key, 0f)
    }

    /**
     * 获取float数据
     *
     * @param context  // 建议使用ApplicationContext
     * @param spName   // 缓存名称
     * @param key      // 缓存键值
     * @param defValue // 未数据数据返回的默认值
     * @return float
     */
    fun getFloatValue(context: Context?, spName: String?, key: String?, defValue: Float): Float {
        if (context == null) {
            return defValue
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        return preferences.getFloat(key, defValue)
    }

    /**
     * 存储boolean数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // boolean数据
     */
    fun setBooleanValue(context: Context?, spName: String?, key: String?, value: Boolean) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        preferences.edit().putBoolean(key, value).apply()
    }

    /**
     * 获取boolean数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return boolean
     */
    fun getBooleanValue(context: Context?, spName: String?, key: String?): Boolean {
        return getBooleanValue(context, spName, key, false)
    }

    /**
     * 获取boolean数据
     *
     * @param context  // 建议使用ApplicationContext
     * @param spName   // 缓存名称
     * @param key      // 缓存键值
     * @param defValue // 未数据数据返回的默认值
     * @return boolean
     */
    fun getBooleanValue(context: Context?, spName: String?, key: String?, defValue: Boolean): Boolean {
        if (context == null) {
            return defValue
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        return preferences.getBoolean(key, defValue)
    }

    /**
     * 存储long数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // long数据
     */
    fun setLongValue(context: Context?, spName: String?, key: String?, value: Long) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        preferences.edit().putLong(key, value).apply()
    }

    /**
     * 获取long数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return long
     */
    fun getLongValue(context: Context?, spName: String?, key: String?): Long {
        return getLongValue(context, spName, key, 0)
    }

    /**
     * 获取long数据
     *
     * @param context  // 建议使用ApplicationContext
     * @param spName   // 缓存名称
     * @param key      // 缓存键值
     * @param defValue // 未数据数据返回的默认值
     * @return long
     */
    fun getLongValue(context: Context?, spName: String?, key: String?, defValue: Long): Long {
        if (context == null) {
            return defValue
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        return preferences.getLong(key, defValue)
    }

    /**
     * 存储String数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // String数据
     */
    fun setStringValue(context: Context?, spName: String?, key: String?, value: String?) {
        if (context == null) {
            return
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        preferences.edit().putString(key, value).apply()
    }

    /**
     * 获取String数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return String
     */
    fun getStringValue(context: Context?, spName: String?, key: String?): String? {
        return getStringValue(context, spName, key, "")
    }

    /**
     * 获取String数据
     *
     * @param context  // 建议使用ApplicationContext
     * @param spName   // 缓存名称
     * @param key      // 缓存键值
     * @param defValue // 未数据数据返回的默认值
     * @return String
     */
    fun getStringValue(context: Context?, spName: String?, key: String?, defValue: String): String? {
        if (context == null) {
            return defValue
        }
        val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        return preferences.getString(key, defValue)
    }

    private var json: JSONObject? = null

    /**
     * 以Json的数据形式存储
     *
     * @param context      // 建议使用ApplicationContext
     * @param spName       // 缓存名称
     * @param valueKeyName // 需要以JSON存储的String数据的参数名
     * @param value        // 需要以JSON存储的String数据
     * @param jsonKeyName  // Json的数据对象缓存的参数名
     * @throws JSONException
     */
    fun saveJsonChache(context: Context?, spName: String?, valueKeyName: String, value: String?, jsonKeyName: String?) {
        if (context == null) {
            return
        }
        try {
            val jsonObject = JSONObject()
            jsonObject.put(valueKeyName, value)
            if (json == null) {
                json = JSONObject()
            }
            json!!.put(jsonKeyName, jsonObject.toString())
            setStringValue(context, spName, valueKeyName + "Chache", json.toString())
        } catch (e: JSONException) {
            //e.printStackTrace();
        }
    }

    /**
     * 获取json缓存
     * 以Json的数据形式存储
     *
     * @param context      // 建议使用ApplicationContext
     * @param spName       // 缓存名称
     * @param valueKeyName // 需要以JSON存储的String数据的参数名
     * @param jsonKeyName  // Json的数据对象缓存的参数名
     * @throws JSONException
     */
    fun getJsonCache(context: Context?, spName: String?, valueKeyName: String, jsonKeyName: String?): String? {
        if (context == null) {
            return ""
        }
        val str = getStringValue(context, spName, valueKeyName + "Chache", "")
        var returnStr: String? = null
        try {
            json = if (str?.isEmpty() == true) {
                JSONObject()
            } else {
                JSONObject(str)
            }
            returnStr = ""
            if (json!!.has(jsonKeyName)) {
                val chacheStr = json!!.optString(jsonKeyName)
                if (chacheStr.isEmpty()) {
                    returnStr = ""
                } else {
                    val job = JSONObject(chacheStr)
                    val value = job.optString(valueKeyName)
                    if (!value.isEmpty()) {
                        returnStr = value
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return returnStr
    }
}