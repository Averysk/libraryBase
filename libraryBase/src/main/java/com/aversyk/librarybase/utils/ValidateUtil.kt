package com.aversyk.librarybase.utils;

import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

/**
 * 通用的校验判断工具类
 *
 * @author Averysk
 */
public class ValidateUtil {


    private static long lastClickTime;

    /**
     * 防止控件被重复点击，如果点击间隔时间小于指定时间就点击无
     * 也可做为控件的单击事件 和 双击事件
     *
     * 默认方法: 默认值为 500毫秒(半秒)(一般双击事件的间隔时间范围判断为500毫秒)
     *
     * @return boolean true: 再次点击小于XX毫秒(即双击事件), false: 没有重复点击(即单击事件)
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        // 两次点击间隔小于500毫秒
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 防止控件被重复点击，如果点击间隔时间小于指定时间就点击无
     * 也可做为控件的单击事件 和 双击事件
     *
     * @param times 自定义间隔时间(以 毫秒 为单位)
     * @return boolean true: 再次点击小于XX毫秒(即双击事件), false: 没有重复点击(即单击事件)
     */
    public static boolean isFastDoubleClick(long times) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < times) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 校验: 是否为空json对象
     *
     * @param jo 待校验的json对象
     */
    public static boolean isEmptyJSONObject(JSONObject jo) {
        return null == jo || "null".equals(jo.toString())  || "[]".equals(jo.toString()) || "".equals(jo.toString());
    }

    /**
     * 校验: 是否不为空json对象
     *
     * @param jo 待校验的json对象
     */
    public static boolean isNotEmptyJSONObject(JSONObject jo) {
        return !isEmptyJSONObject(jo);
    }

    /**
     * 校验: 是否为空对象或者空字符串
     *
     * @param o 待校验的对象
     */
    public static boolean isEmptyObjectOrString(Object o) {
        return null == o || "null".equals(o.toString().trim()) || "".equals(o.toString().trim());
    }

    /**
     * 校验: 是否不为为空对象或者空字符串
     *
     * @param o 待校验的对象
     */
    public static boolean isNotEmptyObjectOrString(Object o) {
        return !isEmptyObjectOrString(o);
    }

    /**
     * 校验: 是否为空collection()
     *
     * @param collection 待校验的collection(List: 数组, Set: 集合, Map: 表)
     */
    public static boolean isEmptyCollection(Collection<?> collection) {
        return null == collection || collection.size() == 0;
    }

    /**
     * 校验: 是否不为空collection
     *
     * @param collection 待校验的collection
     */
    public static boolean isNotEmptyCollection(Collection<?> collection) {
        return !isEmptyCollection(collection);
    }


    /**
     * 校验：是否为空Map
     *
     * @param map 待校验的Map
     */
    public static boolean isEmptyMap(Map<?, ?> map) {
        return null == map || 0 == map.entrySet().size() || 0 == map.size();
    }

    /**
     * 校验: 不为空Map
     *
     * @param map 待校验的Map
     * @return boolean
     */
    public static boolean isNotEmptyMap(Map<?, ?> map) {
        return !isEmptyMap(map);
    }



}
