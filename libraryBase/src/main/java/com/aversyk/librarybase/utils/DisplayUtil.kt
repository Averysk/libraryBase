package com.aversyk.librarybase.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import com.aversyk.librarybase.R

/**
 * 屏幕相关工具类
 * @author  Averysk
 */
object DisplayUtil {

    /**
     * 获取设备信息
     *
     * @return
     */
    val model: String
        get() {
            return Build.MODEL.replace(" ", "_")
        }

    /**
     * 获取屏幕宽度【像素】
     *
     * @return
     */
    fun getScreenWidth(activity: Activity?): Int {
        if (activity == null) {
            return 0
        }
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels // (int)(dm.widthPixels * dm.density +0.5f);
    }

    /**
     * 获取屏幕高度【像素】
     *
     * @return
     */
    fun getScreenHeight(activity: Activity?): Int {
        if (activity == null) {
            return 0
        }
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels // (int)(dm.heightPixels * dm.density +0.5f);
    }

    /**
     * 获取屏幕尺寸
     * @param activity
     * @return 宽高
     */
    fun getScreenSize(activity: Activity): IntArray {
        val screenSize = IntArray(2)
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenSize[0] = displayMetrics.widthPixels
        screenSize[1] = displayMetrics.heightPixels
        return screenSize
    }

    /**
     * 获取屏幕宽高
     */
    fun getScreenSize(context: Context): IntArray {
        val manager = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        return intArrayOf(outMetrics.widthPixels, outMetrics.heightPixels)
    }

    /**
     * 获取设备宽度
     */
    fun getDeviceWidth(context: Context): Int {
        val dm: DisplayMetrics = context.resources.displayMetrics
        // 屏幕宽（像素，如：480px）
        return dm.widthPixels
    }

    /**
     * 获取设备高度
     */
    fun getDeviceHeight(context: Context): Int {
        val dm: DisplayMetrics = context.resources.displayMetrics
        // 屏幕高（像素，如：800px）
        return dm.heightPixels
    }

    /**
     * 获取分辨率
     */
    fun getResolution(context: Context): String {
        val dm = context.resources.displayMetrics
        val screenWidth = dm.widthPixels
        val screenHeight = dm.heightPixels
        return "$screenWidth*$screenHeight"
    }

    /**
     * 获取手机大小（分辨率）
     */
    fun getScreenPix(activity: Activity): DisplayMetrics {
        val displaysMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displaysMetrics)
        return displaysMetrics
    }

    /**
     * 获得状态栏的高度
     */
    @SuppressLint("PrivateApi")
    fun getStatusHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = clazz.getField("status_bar_height")[`object`].toString().toInt()
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusHeight
    }

    /**
     * 获取状态栏高度
     * @param activity
     * @return
     */
    fun getStatusBarHeight(activity: Activity): Int {
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        return if (rect.top == 0) 60 else rect.top
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val res = context.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    /**
     * 获取状态栏高度
     * @param activity
     * @return
     */
    fun getActionBarHeight(activity: Activity): Int {
        val tv = TypedValue()
        return if (activity.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(tv.data, activity.resources.displayMetrics)
        } else 0
    }

    /**
     * 获取内容区域
     * @param activity
     * @return
     */
    fun getContextRect(activity: Activity): Int {
        //应用区域
        val outRect1 = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(outRect1)
        return outRect1.height()
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    fun px2dip(context: Context?, pxValue: Float): Int {
        if (context == null) {
            return 0
        }
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    fun dip2px(context: Context?, dipValue: Float): Int {
        if (context == null) {
            return 0
        }
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 将dp值转换为px值，保证尺寸大小不变
     */
    fun dp2Px(context: Context?, dp: Float): Int {
        if (context == null) {
            return 0
        }
        val r = context.resources
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics)
        return px.toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    fun sp2px(context: Context?, spValue: Float): Int {
        if (context == null) {
            return 0
        }
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    fun px2sp(context: Context?, pxValue: Float): Int {
        if (context == null) {
            return 0
        }
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 获取MetaData信息
     *
     * @param name
     * @param def
     * @return
     */
    fun getMetaDataValue(context: Context, name: String, def: String): String {
        return getMetaDataValue(context, name)
    }

    fun getMetaDataValue(context: Context, name: String): String {
        var value: Any? = null
        val packageManager = context.packageManager
        val applicationInfo: ApplicationInfo?
        try {
            applicationInfo = packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            if (applicationInfo?.metaData != null) {
                value = applicationInfo.metaData[name]
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Could not read the name in the manifest file.", e)
        }
        if (value == null) {
            throw RuntimeException("The name '$name' is not defined in the manifest file's meta data.")
        }
        return value.toString()
    }
}