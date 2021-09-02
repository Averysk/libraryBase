package com.aversyk.librarybase.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.aversyk.librarybase.R

/**
 * 状态栏工具类
 *
 * @author Averysk
 */
object StatusBarUtil {
    // 默认状态栏透明度
    private const val DEFAULT_STATUS_BAR_ALPHA = 112
    private val FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view
    private val FAKE_TRANSLUCENT_VIEW_ID = R.id.statusbarutil_translucent_view
    private const val TAG_KEY_HAVE_SET_OFFSET = -123

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    fun setColor(activity: Activity, @ColorInt color: Int) {
        setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏颜色 - 自定义透明度
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColor(activity: Activity, @ColorInt color: Int, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = calculateStatusColor(color, statusBarAlpha)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val decorView = activity.window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
            } else {
                decorView.addView(createStatusBarView(activity, color, statusBarAlpha))
            }
            setRootView(activity)
        }
    }

    /**
     * 设置状态栏纯色 - 不加半透明效果
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    fun setColorNoTranslucent(activity: Activity, @ColorInt color: Int) {
        setColor(activity, color, Color.TRANSPARENT)
    }

    /**
     * 设置状态栏颜色 - (5.0以下无半透明效果,不建议使用)
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    @Deprecated("")
    fun setColorDiff(activity: Activity, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
        // 移除半透明矩形,以免叠加
        val fakeStatusBarView = contentView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.visibility == View.GONE) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
            fakeStatusBarView.setBackgroundColor(color)
        } else {
            contentView.addView(createStatusBarView(activity, color))
        }
        setRootView(activity)
    }

    /**
     * 设置状态栏颜色 - 针对为滑动返回的界面
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    fun setColorForSwipeBack(activity: Activity, @ColorInt color: Int) {
        setColorForSwipeBack(activity, color, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏颜色 - 针对为滑动返回的界面
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColorForSwipeBack(activity: Activity, @ColorInt color: Int, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
            val rootView = contentView.getChildAt(0)
            val statusBarHeight = getStatusBarHeight(activity)
            if (rootView != null && rootView is CoordinatorLayout) {
                val coordinatorLayout = rootView
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    coordinatorLayout.fitsSystemWindows = false
                    contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
                    val isNeedRequestLayout = contentView.paddingTop < statusBarHeight
                    if (isNeedRequestLayout) {
                        contentView.setPadding(0, statusBarHeight, 0, 0)
                        coordinatorLayout.post { coordinatorLayout.requestLayout() }
                    }
                } else {
                    coordinatorLayout.setStatusBarBackgroundColor(
                        calculateStatusColor(
                            color,
                            statusBarAlpha
                        )
                    )
                }
            } else {
                contentView.setPadding(0, statusBarHeight, 0, 0)
                contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
            }
            setTransparentForWindow(activity)
        }
    }

    /**
     * 设置状态栏颜色 - 针对为DrawerLayout 布局
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    fun setColorForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout, @ColorInt color: Int) {
        setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏颜色 - 针对为DrawerLayout 布局 - 不加半透明效果(纯色)
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    fun setColorNoTranslucentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout, @ColorInt color: Int) {
        setColorForDrawerLayout(activity, drawerLayout, color, 0)
    }

    /**
     * 设置状态栏颜色 - 针对为DrawerLayout 布局
     *
     * @param activity       需要设置的activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColorForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout, @ColorInt color: Int, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        // 生成一个状态栏大小的矩形
        // 添加 statusBarView 到布局中
        val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
        val fakeStatusBarView = contentLayout.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.visibility == View.GONE) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
            fakeStatusBarView.setBackgroundColor(color)
        } else {
            contentLayout.addView(createStatusBarView(activity, color), 0)
        }
        // 内容布局不是 LinearLayout 时,设置padding top
        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                .setPadding(
                    contentLayout.paddingLeft,
                    getStatusBarHeight(activity) + contentLayout.paddingTop,
                    contentLayout.paddingRight,
                    contentLayout.paddingBottom
                )
        }
        // 设置属性
        setDrawerLayoutProperty(drawerLayout, contentLayout)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 设置状态栏颜色 - 针对为DrawerLayout 布局 (5.0以下无半透明效果,不建议使用)
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    @Deprecated("")
    fun setColorForDrawerLayoutDiff(activity: Activity, drawerLayout: DrawerLayout, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 生成一个状态栏大小的矩形
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            val fakeStatusBarView = contentLayout.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA))
            } else {
                // 添加 statusBarView 到布局中
                contentLayout.addView(createStatusBarView(activity, color), 0)
            }
            // 内容布局不是 LinearLayout 时,设置padding top
            if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
            }
            // 设置属性
            setDrawerLayoutProperty(drawerLayout, contentLayout)
        }
    }

    /**
     * 设置状态栏半透明
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    fun setTranslucent(activity: Activity) {
        setTranslucent(activity, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏半透明 - 自定义透明度
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */
    fun setTranslucent(activity: Activity, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparent(activity)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 设置状态栏半透明 - (5.0以上半透明效果,不建议使用)
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    @Deprecated("")
    fun setTranslucentDiff(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setRootView(activity)
        }
    }

    /**
     * 设置状态栏半透明 - 针对根布局是 CoordinatorLayout
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */
    fun setTranslucentForCoordinatorLayout(activity: Activity, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 设置状态栏半透明 - 针对为 DrawerLayout 布局
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    fun setTranslucentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout) {
        setTranslucentForDrawerLayout(activity, drawerLayout, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏半透明 - 针对为 DrawerLayout 布局
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param statusBarAlpha 状态栏透明度
     */
    fun setTranslucentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparentForDrawerLayout(activity, drawerLayout)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 设置状态栏半透明 - 针对为 DrawerLayout 布局 (5.0以上半透明效果,不建议使用)
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    @Deprecated("")
    fun setTranslucentForDrawerLayoutDiff(activity: Activity, drawerLayout: DrawerLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 设置内容布局属性
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            contentLayout.fitsSystemWindows = true
            contentLayout.clipToPadding = true
            // 设置抽屉布局属性
            val vg = drawerLayout.getChildAt(1) as ViewGroup
            vg.fitsSystemWindows = false
            // 设置 DrawerLayout 属性
            drawerLayout.fitsSystemWindows = false
        }
    }

    /**
     * 设置状态栏半透明 - 针对为头部是 ImageView 的界面
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageView(activity: Activity, needOffsetView: View?) {
        setTranslucentForImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView)
    }

    /**
     * 设置状态栏半透明 - 针对为头部是 ImageView 的界面
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageView(activity: Activity, @IntRange(from = 0, to = 255) statusBarAlpha: Int, needOffsetView: View?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparentForWindow(activity)
        addTranslucentView(activity, statusBarAlpha)
        if (needOffsetView != null) {
            val haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET)
            if (haveSetOffset != null && haveSetOffset as Boolean) {
                return
            }
            val layoutParams = needOffsetView.layoutParams as MarginLayoutParams
            layoutParams.setMargins(
                layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity),
                layoutParams.rightMargin, layoutParams.bottomMargin
            )
            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true)
        }
    }

    /**
     * 设置状态栏半透明 - 针对为 fragment 头部是 ImageView 的设置
     *
     * @param activity       fragment 对应的 activity
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageViewInFragment(activity: Activity, needOffsetView: View?) {
        setTranslucentForImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView)
    }

    /**
     * 设置状态栏半透明 - 针对为 fragment 头部是 ImageView 的设置
     *
     * @param activity       fragment 对应的 activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageViewInFragment(activity: Activity, @IntRange(from = 0, to = 255) statusBarAlpha: Int, needOffsetView: View?) {
        setTranslucentForImageView(activity, statusBarAlpha, needOffsetView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            clearPreviousSetting(activity)
        }
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    fun setTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        setRootView(activity)
    }

    /**
     * 设置状态栏全透明 - 针对为 DrawerLayout 布局
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    fun setTransparentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
        // 内容布局不是 LinearLayout 时,设置padding top
        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
        }

        // 设置属性
        setDrawerLayoutProperty(drawerLayout, contentLayout)
    }

    /**
     * 设置状态栏全透明 - 针对为头部是 ImageView 的界面
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTransparentForImageView(activity: Activity, needOffsetView: View?) {
        setTranslucentForImageView(activity, 0, needOffsetView)
    }

    /**
     * 设置状态栏全透明 - 针对为 fragment 头部是 ImageView 的设置
     *
     * @param activity       fragment 对应的 activity
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTransparentForImageViewInFragment(activity: Activity, needOffsetView: View?) {
        setTranslucentForImageViewInFragment(activity, 0, needOffsetView)
    }

    /**
     * 设置透明
     */
    private fun setTransparentForWindow(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.statusBarColor = Color.TRANSPARENT
            val decorView = activity.window.decorView
            val option: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
                } else {
                    (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                }
            } else {
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            }
            decorView.systemUiVisibility = option
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun transparentStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 与华为等虚拟导航栏有冲突，故注释；存在等问题是底部无法沉浸式
            //activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 设置 DrawerLayout 属性
     *
     * @param drawerLayout              DrawerLayout
     * @param drawerLayoutContentLayout DrawerLayout 的内容布局
     */
    private fun setDrawerLayoutProperty(drawerLayout: DrawerLayout, drawerLayoutContentLayout: ViewGroup) {
        val drawer = drawerLayout.getChildAt(1) as ViewGroup
        drawerLayout.fitsSystemWindows = false
        drawerLayoutContentLayout.fitsSystemWindows = false
        drawerLayoutContentLayout.clipToPadding = true
        drawer.fitsSystemWindows = false
    }

    /**
     * 隐藏系统界面
     *
     * @param activity 需要设置的 activity
     */
    fun hideSystemUI(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        /*
         * 启用常规沉浸模式。
         * 对于“向后倾斜”模式，删除 View.SYSTEM_UI_FLAG_IMMERSIVE。
         * 或者对于“粘性沉浸式”，将其替换为 View.SYSTEM_UI_FLAG_IMMERSIVE
         */
        val decorView = activity.window.decorView
        val option: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                /*
                 * 将内容设置为显示在系统栏下方，以便在系统栏隐藏时内容不会调整大小
                 * 隐藏导航栏和状态栏
                 */
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
        } else {
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
        decorView.systemUiVisibility = option
    }

    /**
     * 隐藏系统底部虚拟按键，并且全屏(开启全面屏显示)
     *
     * @param activity 需要设置的 activity
     */
    fun hideBottomUIMenu(activity: Activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // lower api
            val v = activity.window.decorView
            v.systemUiVisibility = View.GONE
        } else {
            val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            activity.window.decorView.systemUiVisibility = uiOptions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val lp = activity.window.attributes
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                activity.window.attributes = lp
            }
        }
    }

    /**
     * 隐藏导航栏，并且保持布局状态位于状态栏下方
     *
     * @param activity 需要设置的 activity
     */
    fun hideNavigationBar(activity: Activity) {
        //保持布局状态
        var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or  //布局位于状态栏下方
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or  //全屏
                View.SYSTEM_UI_FLAG_FULLSCREEN or  //隐藏导航栏
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        uiOptions = if (Build.VERSION.SDK_INT >= 19) {
            uiOptions or 0x00001000
        } else {
            uiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
        }
        activity.window.decorView.systemUiVisibility = uiOptions
    }

    /**
     * 隐藏状态栏
     *
     * @param activity 调用的 Activity
     */
    fun hideStatusBarView(activity: Activity, isNeed: Boolean) {
        if (isNeed) {
            // 隐藏状态栏
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            // 显示状态栏
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    /**
     * 隐藏伪状态栏 View
     *
     * @param activity 调用的 Activity
     */
    fun hideFakeStatusBarView(activity: Activity) {
        val decorView = activity.window.decorView as ViewGroup
        val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            fakeStatusBarView.visibility = View.GONE
        }
        val fakeTranslucentView = decorView.findViewById<View>(FAKE_TRANSLUCENT_VIEW_ID)
        if (fakeTranslucentView != null) {
            fakeTranslucentView.visibility = View.GONE
        }
    }

    /**
     * 设置状态栏模式 - 亮色模式
     * @param activity 调用的 Activity
     * @return 1:MIUUI 2:Flyme 3:Android6.0
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun setLightMode(activity: Activity): Int {
        return setLightMode(activity, true)
    }

    /**
     * 设置状态栏模式 - 亮色模式
     * @param activity 调用的 Activity
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun setLightMode(activity: Activity, isdark: Boolean): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            when {
                setMIUIStatusBarDarkIcon(activity, isdark) -> {
                    result = 1
                }
                setMeizuStatusBarDarkIcon(activity, isdark) -> {
                    result = 2
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    val decorView = activity.window.decorView
                    val option: Int
                    if (isdark) {
                        option = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                    or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
                        } else {
                            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                        }
                    } else {
                        option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        val window = activity.window
                        if (window != null) {
                            window.navigationBarColor = Color.WHITE
                        }
                    }
                    decorView.systemUiVisibility = option
                    result = 3
                }
            }
        }
        return result
    }

    /**
     * 设置状态栏模式 - 暗色模式
     * @param activity 调用的 Activity
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun setDarkMode(activity: Activity) {
        setDarkMode(activity, 0)
    }

    /**
     * 设置状态栏模式 - 暗色模式
     * @param activity 调用的 Activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun setDarkMode(activity: Activity, type: Int) {
        if (type == 1) {
            setMIUIStatusBarDarkIcon(activity, false)
        } else if (type == 2) {
            setMeizuStatusBarDarkIcon(activity, false)
        } else if (type == 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val window = activity.window
                if (window != null) {
                    val decorView = window.decorView
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                }
            }
        }
    }

    /**
     * 设置状态栏模式 - 小米 - 需要MIUIV6以上
     * @param activity 调用的 Activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色 true: 状态栏透明且黑色字体,  false: 状态栏清除黑色字体
     * @return boolean 成功执行返回true
     */
    fun setMIUIStatusBarDarkIcon(activity: Activity, dark: Boolean): Boolean {
        var result = false
        val window = activity.window
        if (window != null) {
            try {
                val clazz: Class<out Window> = window.javaClass
                @SuppressLint("PrivateApi") val layoutParams =
                    Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                val darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                extraFlagField.invoke(window, if (dark) darkModeFlag else 0, darkModeFlag)
                result = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    val decorView = window.decorView
                    val option: Int = if (dark) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                    or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
                        } else {
                            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                        }
                    } else {
                        (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_VISIBLE)
                    }
                    decorView.systemUiVisibility = option
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // 设置导航栏为白色
                    window.navigationBarColor = Color.WHITE
                }
            } catch (ignored: Exception) {
            }
        }
        return result
    }

    /**
     * 设置状态栏模式 - 魅族 - 需要Flyme 4.0以上 - 可以用来判断是否为Flyme用户
     * @param activity 调用的 Activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色 true: 状态栏透明且黑色字体,  false: 状态栏清除黑色字体
     * @return boolean 成功执行返回true
     */
    fun setMeizuStatusBarDarkIcon(activity: Activity, dark: Boolean): Boolean {
        var result = false
        val window = activity.window
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                value = if (dark) {
                    value or bit
                } else {
                    value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.TRANSPARENT
                    val decorView = window.decorView
                    val option: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                                    or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
                        } else {
                            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                        }
                    } else {
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    }
                    decorView.systemUiVisibility = option
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    )
                }
            } catch (e: Exception) {
            }
        }
        return result
    }

    ///////////////////////////////////////////////////////////////////////////////////
    /**
     * 清除以前的设置
     * @param activity 调用的 Activity
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun clearPreviousSetting(activity: Activity) {
        val decorView = activity.window.decorView as ViewGroup
        val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            decorView.removeView(fakeStatusBarView)
            val rootView = (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
            rootView.setPadding(0, 0, 0, 0)
        }
    }

    /**
     * 添加半透明矩形条
     *
     * @param activity       需要设置的 activity
     * @param statusBarAlpha 透明值
     */
    private fun addTranslucentView(activity: Activity, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
        val fakeTranslucentView = contentView.findViewById<View>(FAKE_TRANSLUCENT_VIEW_ID)
        if (fakeTranslucentView != null) {
            if (fakeTranslucentView.visibility == View.GONE) {
                fakeTranslucentView.visibility = View.VISIBLE
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0))
        } else {
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha))
        }
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private fun createStatusBarView(activity: Activity, @ColorInt color: Int, alpha: Int = 0): View {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
        statusBarView.id = FAKE_STATUS_BAR_VIEW_ID
        return statusBarView
    }

    /**
     * 设置根布局参数
     */
    private fun setRootView(activity: Activity) {
        val parent = activity.findViewById<View>(android.R.id.content) as ViewGroup
        var i = 0
        val count = parent.childCount
        while (i < count) {
            val childView = parent.getChildAt(i)
            if (childView is ViewGroup) {
                childView.setFitsSystemWindows(true)
                childView.clipToPadding = true
            }
            i++
        }
    }

    /**
     * 创建半透明矩形 View
     *
     * @param alpha 透明值
     * @return 半透明 View
     */
    private fun createTranslucentStatusBarView(activity: Activity, alpha: Int): View {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(activity)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
        statusBarView.id = FAKE_TRANSLUCENT_VIEW_ID
        return statusBarView
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private fun calculateStatusColor(@ColorInt color: Int, alpha: Int): Int {
        if (alpha == 0) {
            return color
        }
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }
}