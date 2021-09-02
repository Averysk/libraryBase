package com.aversyk.librarybase.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

/**
 * TextView添加图片工具类
 * @author Averysk
 */
public class ImageToolUtil {

    /**
     * 设置TextView左边图片
     * @param srcId
     */
    public static void serLeftIcon(Context context, TextView tv, int srcId) {
        if (context == null) {
            return;
        }
        Drawable db = ContextCompat.getDrawable(context, srcId);
        if (db != null) {
            db.setBounds(0, 0, db.getMinimumWidth(), db.getMinimumHeight());
            tv.setCompoundDrawables(db, null, null, null);
        }
    }

    /**
     * 设置TextView右边图片
     * @param srcId
     */
    public static void serRightIcon(Context context, TextView tv, int srcId) {
        if (context == null) {
            return;
        }
        Drawable db = ContextCompat.getDrawable(context, srcId);
        if (db != null) {
            db.setBounds(0, 0, db.getMinimumWidth(), db.getMinimumHeight());
            tv.setCompoundDrawables(null, null, db, null);
        }
    }


}
