package com.aversyk.librarybase.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * TextView添加图片工具类
 * @author Averysk
 */
object ImageToolUtil {

    /**
     * 设置TextView左边图片
     * @param srcId
     */
    fun serLeftIcon(context: Context?, tv: TextView, srcId: Int) {
        if (context == null) {
            return
        }
        val db: Drawable? = ContextCompat.getDrawable(context, srcId)
        if (db != null) {
            db.setBounds(0, 0, db.minimumWidth, db.minimumHeight)
            tv.setCompoundDrawables(db, null, null, null)
        }
    }

    /**
     * 设置TextView右边图片
     * @param srcId
     */
    fun serRightIcon(context: Context?, tv: TextView, srcId: Int) {
        if (context == null) {
            return
        }
        val db: Drawable? = ContextCompat.getDrawable(context, srcId)
        if (db != null) {
            db.setBounds(0, 0, db.minimumWidth, db.minimumHeight)
            tv.setCompoundDrawables(null, null, db, null)
        }
    }

    /**
     * 设置TextView上面图片
     * @param srcId
     */
    fun serTopIcon(context: Context?, tv: TextView, srcId: Int) {
        if (context == null) {
            return
        }
        val db = ContextCompat.getDrawable(context, srcId)
        if (db != null) {
            db.setBounds(0, 0, db.minimumWidth, db.minimumHeight)
            tv.setCompoundDrawables(null, db, null, null)
        }
    }

    /**
     * 设置TextView下面图片
     * @param srcId
     */
    fun serBottomIcon(context: Context?, tv: TextView, srcId: Int) {
        if (context == null) {
            return
        }
        val db = ContextCompat.getDrawable(context, srcId)
        if (db != null) {
            db.setBounds(0, 0, db.minimumWidth, db.minimumHeight)
            tv.setCompoundDrawables(null, null, null, db)
        }
    }

}