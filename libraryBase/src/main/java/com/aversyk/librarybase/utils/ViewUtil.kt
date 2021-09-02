package com.aversyk.librarybase.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * View常用工具类
 *
 * @author Averysk
 */
public class ViewUtil {

    /**
     * 按比例设置布局
     *
     * @param mContext      activity
     * @param view          要设置的view
     * @param defaultHeight 默认高度
     * @param defaultWidth  默认宽度
     * @return 布局
     */
    public static View setViewLayoutParams(Activity mContext, String width, String height, View view, double defaultHeight, double defaultWidth, int padding) {
        int sw = DisplayUtil.getScreenWidth(mContext) - padding;
        double originHeight = Double.parseDouble(height);
        double originWidth = Double.parseDouble(width);
        if (originHeight == 0 && defaultHeight != 0) {
            originHeight = defaultHeight;
        }
        if (originWidth == 0 && defaultWidth != 0) {
            originWidth = defaultWidth;
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = sw;
        lp.height = (int) (sw * originHeight / originWidth);
        view.setLayoutParams(lp);
        return view;
    }
}
