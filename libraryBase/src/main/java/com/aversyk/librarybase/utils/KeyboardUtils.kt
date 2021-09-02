package com.aversyk.librarybase.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

/**
 * 软键盘工具
 *
 * @author Averysk
 */
object KeyboardUtils {
    // 显示软键盘
    fun showKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        imm.showSoftInput(view, 0)
    }

    // 隐藏软键盘
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // 隐藏软键盘
    fun hideKeyboard(dialog: Dialog) {
        val view = dialog.currentFocus
        if (view is TextView) {
            val mInputMethodManager = dialog.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }
    }

    // 软键盘在显示和隐藏之间切换
    fun toggleSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 当点击其他View时隐藏软键盘
     *
     * @param activity     Activity
     * @param ev           事件
     * @param excludeViews 点击这些View不会触发隐藏软键盘动作
     */
    fun hideInputWhenTouchOtherView(activity: Activity, ev: MotionEvent, excludeViews: List<View?>?) {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            if (excludeViews != null && excludeViews.isNotEmpty()) {
                for (i in excludeViews.indices) {
                    if (isTouchView(excludeViews[i], ev)) {
                        return
                    }
                }
            }
            val v:View? = activity.currentFocus
            if (isShouldHideInput(v, ev)) {
                val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v?.windowToken, 0)
            }
        }
    }

    fun isTouchView(view: View?, event: MotionEvent?): Boolean {
        if (view == null || event == null) {
            return false
        }
        val leftTop = intArrayOf(0, 0)
        view.getLocationInWindow(leftTop)
        val left = leftTop[0]
        val top = leftTop[1]
        val bottom = top + view.height
        val right = left + view.width
        return event.rawX > left && event.rawX < right && event.rawY > top && event.rawY < bottom
    }

    fun isShouldHideInput(v: View?, event: MotionEvent?): Boolean {
        if (v != null) {
            if (v is EditText) {
                return !isTouchView(v, event)
            }
        }
        return false
    }
}