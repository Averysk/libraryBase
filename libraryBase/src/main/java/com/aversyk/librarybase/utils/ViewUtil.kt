package com.aversyk.librarybase.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.view.MotionEvent
import android.view.View

/**
 * View常用工具类
 *
 * @author Averysk
 */
object ViewUtil {


    /**
     * 添加点击缩放效果
     */
    @SuppressLint("ClickableViewAccessibility")
    fun View.addClickScale(scale: Float = 0.9f, duration: Long = 150) {
        this.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    this.animate().scaleX(scale).scaleY(scale).setDuration(duration).start()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    this.animate().scaleX(1f).scaleY(1f).setDuration(duration).start()
                }
            }
            // 点击事件处理，交给View自身
            this.onTouchEvent(event)
        }
    }

    /**
     * 按比例设置布局
     *
     * @param mContext      activity
     * @param view          要设置的view
     * @param defaultHeight 默认高度
     * @param defaultWidth  默认宽度
     * @return 布局
     */
    fun View.setViewLayoutParams(mContext: Activity?, width: String, height: String, defaultHeight: Double, defaultWidth: Double, padding: Int): View {
        val sw = DisplayUtil.getScreenWidth(mContext) - padding
        var originHeight = height.toDouble()
        var originWidth = width.toDouble()
        if (originHeight == 0.0 && defaultHeight != 0.0) {
            originHeight = defaultHeight
        }
        if (originWidth == 0.0 && defaultWidth != 0.0) {
            originWidth = defaultWidth
        }
        val lp = this.layoutParams
        lp.width = sw
        lp.height = (sw * originHeight / originWidth).toInt()
        this.layoutParams = lp
        return this
    }
}