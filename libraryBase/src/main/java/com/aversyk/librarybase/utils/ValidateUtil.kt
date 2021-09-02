package com.aversyk.librarybase.utils

import org.json.JSONObject

/**
 * 通用的校验判断工具类
 *
 * @author Averysk
 */
object ValidateUtil {

    private var lastClickTime: Long = 0// 两次点击间隔小于500毫秒

    /**
     * 防止控件被重复点击，如果点击间隔时间小于指定时间就点击无
     * 也可做为控件的单击事件 和 双击事件
     *
     * 默认方法: 默认值为 500毫秒(半秒)(一般双击事件的间隔时间范围判断为500毫秒)
     *
     * @return boolean true: 再次点击小于XX毫秒(即双击事件), false: 没有重复点击(即单击事件)
     */
    val isFastDoubleClick: Boolean
        get() {
            val time = System.currentTimeMillis()
            // 两次点击间隔小于500毫秒
            if (time - lastClickTime < 500) {
                return true
            }
            lastClickTime = time
            return false
        }

    /**
     * 防止控件被重复点击，如果点击间隔时间小于指定时间就点击无
     * 也可做为控件的单击事件 和 双击事件
     *
     * @param times 自定义间隔时间(以 毫秒 为单位)
     * @return boolean true: 再次点击小于XX毫秒(即双击事件), false: 没有重复点击(即单击事件)
     */
    fun isFastDoubleClick(times: Long): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (timeD in 1 until times) {
            return true
        }
        lastClickTime = time
        return false
    }

    /**
     * 校验: 是否为空json对象
     *
     * @param jo 待校验的json对象
     */
    fun isEmptyJSONObject(jo: JSONObject?): Boolean {
        return null == jo || "null" == jo.toString() || "[]" == jo.toString() || "" == jo.toString()
    }

    /**
     * 校验: 是否不为空json对象
     *
     * @param jo 待校验的json对象
     */
    fun isNotEmptyJSONObject(jo: JSONObject?): Boolean {
        return !isEmptyJSONObject(jo)
    }

    /**
     * 校验: 是否为空对象或者空字符串
     *
     * @param o 待校验的对象
     */
    fun isEmptyObjectOrString(o: Any?): Boolean {
        return null == o || "null" == o.toString().trim { it <= ' ' } || "" == o.toString().trim { it <= ' ' }
    }

    /**
     * 校验: 是否不为为空对象或者空字符串
     *
     * @param o 待校验的对象
     */
    fun isNotEmptyObjectOrString(o: Any?): Boolean {
        return !isEmptyObjectOrString(o)
    }

    /**
     * 校验: 是否为空collection()
     *
     * @param collection 待校验的collection(List: 数组, Set: 集合, Map: 表)
     */
    fun isEmptyCollection(collection: Collection<*>?): Boolean {
        return null == collection || collection.isEmpty()
    }

    /**
     * 校验: 是否不为空collection
     *
     * @param collection 待校验的collection
     */
    fun isNotEmptyCollection(collection: Collection<*>?): Boolean {
        return !isEmptyCollection(collection)
    }

    /**
     * 校验：是否为空Map
     *
     * @param map 待校验的Map
     */
    fun isEmptyMap(map: Map<*, *>?): Boolean {
        return null == map || map.entries.isEmpty() || map.isEmpty()
    }

    /**
     * 校验: 不为空Map
     *
     * @param map 待校验的Map
     * @return boolean
     */
    fun isNotEmptyMap(map: Map<*, *>?): Boolean {
        return !isEmptyMap(map)
    }
}