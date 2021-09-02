package com.aversyk.librarybase.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 应用存储数据的公共缓存
 *
 * @author Averysk
 */
public class SharedPreferencesUtil {

    private static SharedPreferencesUtil instance = null;

    public static SharedPreferencesUtil getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesUtil();
        }
        return instance;
    }

    /**
     * 保存对象实体 - 需实现 Serializable
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param ob      // 缓存对你
     * @return boolean  // 是否成功
     */
    public boolean savaObject(Context context, String spName, String key, Object ob) {
        if (context == null) {
            return false;
        }
        if (ob == null) {
            return false;
        }
        boolean falg = false;
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        // 创建字节输出
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            // 创建对象输出流，并封装字节流
            oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(ob);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            falg = preferences.edit().putString(key, oAuth_Base64).commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return falg;
    }

    /**
     * 获取存储的对象实体 - 需实现 Serializable
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return Object   // 对象实体
     */
    public Object getObject(Context context, String spName, String key) {
        if (context == null) {
            return null;
        }
        Object ob = null;
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String productBase64 = preferences.getString(key, "");
        // 读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes(), Base64.DEFAULT);
        // 封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        ObjectInputStream bis = null;
        try {
            // 再次封装
            bis = new ObjectInputStream(bais);
            // 读取对象
            ob = bis.readObject();
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ob;
    }

    /**
     * 移除某个缓存对象 - 根据key
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     */
    @SuppressLint("CommitPrefEdits")
    public void removeAct(Context context, String spName, String key) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        preferences.edit().remove(key).apply();
    }

    /**
     * 清除某个缓存对象 - 根据spName(将清除当前缓存名下所有缓存)
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     */
    public void clearByFileName(Context context, String spName) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        // 使用 apply() 代替； commit() 立即将其数据写入持久存储，而 apply() 将在后台处理它
        //preferences.edit().clear().commit();
        preferences.edit().clear().apply();
    }

    /**
     * 存储int数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // int数据
     */
    public void setIntValue(Context context, String spName, String key, int value) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).apply();
    }

    /**
     * 获取int数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return int
     */
    public int getIntValue(Context context, String spName, String key) {
        return getIntValue(context, spName, key, 0);
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
    public int getIntValue(Context context, String spName, String key, int defValue) {
        if (context == null) {
            return defValue;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.getInt(key, defValue);
    }

    /**
     * 存储float数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // float数据
     */
    public void setFloatValue(Context context, String spName, String key, float value) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        preferences.edit().putFloat(key, value).apply();
    }

    /**
     * 获取float数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return float
     */
    public float getFloatValue(Context context, String spName, String key) {
        return getFloatValue(context, spName, key, 0);
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
    public float getFloatValue(Context context, String spName, String key, float defValue) {
        if (context == null) {
            return defValue;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.getFloat(key, defValue);
    }

    /**
     * 存储boolean数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // boolean数据
     */
    public void setBooleanValue(Context context, String spName, String key, boolean value) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取boolean数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return boolean
     */
    public boolean getBooleanValue(Context context, String spName, String key) {
        return getBooleanValue(context, spName, key, false);
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
    public boolean getBooleanValue(Context context, String spName, String key, boolean defValue) {
        if (context == null) {
            return defValue;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defValue);
    }

    /**
     * 存储long数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // long数据
     */
    public void setLongValue(Context context, String spName, String key, long value) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        preferences.edit().putLong(key, value).apply();
    }

    /**
     * 获取long数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return long
     */
    public long getLongValue(Context context, String spName, String key) {
        return getLongValue(context, spName, key, 0);
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
    public long getLongValue(Context context, String spName, String key, long defValue) {
        if (context == null) {
            return defValue;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.getLong(key, defValue);
    }

    /**
     * 存储String数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @param value   // String数据
     */
    public void setStringValue(Context context, String spName, String key, String value) {
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
    }

    /**
     * 获取String数据
     *
     * @param context // 建议使用ApplicationContext
     * @param spName  // 缓存名称
     * @param key     // 缓存键值
     * @return String
     */
    public String getStringValue(Context context, String spName, String key) {
        return getStringValue(context, spName, key, "");
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
    public String getStringValue(Context context, String spName, String key, String defValue) {
        if (context == null) {
            return defValue;
        }
        SharedPreferences preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }


    private JSONObject json = null;

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
    public void saveJsonChache(Context context, String spName, String valueKeyName, String value, String jsonKeyName) {
        if (context == null) {
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(valueKeyName, value);
            if (json == null) {
                json = new JSONObject();
            }
            json.put(jsonKeyName, jsonObject.toString());
            setStringValue(context, spName, valueKeyName + "Chache", json.toString());
        } catch (JSONException e) {
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
    public String getJsonCache(Context context, String spName, String valueKeyName, String jsonKeyName) {
        if (context == null) {
            return "";
        }
        String str = getStringValue(context, spName, valueKeyName + "Chache", "");
        String returnStr = null;
        try {
            if (str.isEmpty()) {
                json = new JSONObject();
            } else {
                json = new JSONObject(str);
            }
            returnStr = "";
            if (json.has(jsonKeyName)) {
                String chacheStr = json.optString(jsonKeyName);
                if (chacheStr.isEmpty()) {
                    returnStr = "";
                } else {
                    JSONObject job = new JSONObject(chacheStr);
                    String value = job.optString(valueKeyName);
                    if (!value.isEmpty()) {
                        returnStr = value;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnStr;
    }

}
