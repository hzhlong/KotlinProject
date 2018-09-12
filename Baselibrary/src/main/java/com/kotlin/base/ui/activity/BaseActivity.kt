package com.kotlin.base.ui.activity

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import com.kotlin.base.R
import com.kotlin.base.common.AppManager
import com.readystatesoftware.systembartint.SystemBarTintManager
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.find


/**
 * create by 何兆鸿 as 2018/4/17
 * Activity基类
 */
open class BaseActivity : RxAppCompatActivity() {

    private var mColorId = R.color.common_blue // 状态栏的默认背景色
    private var isNeedLoadBar = true // 子类是否需要实现沉浸式,默认需要

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.instance.addActivity(this)

        initStateBar()
    }

    //------ 设置沉浸式  -------//
    /**
     * 初始化沉浸式
     */
    private fun initStateBar() {
        setThemeColorId()
        if(isNeedLoadStatusBar())
            loadStateBar()
    }

    /**
     * 加载沉浸式
     */
    private fun loadStateBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true)
        }
        var tintManager = SystemBarTintManager(this)
        // 激活状态栏设置
        tintManager.isStatusBarTintEnabled = true
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true)
        // 设置一个状态栏颜色
        tintManager.setStatusBarTintResource(mColorId)
    }

    @TargetApi(19)
    private fun setTranslucentStatus(on: Boolean) {
        var winParams: WindowManager.LayoutParams = window.attributes
        var bits: Int = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }

        window.attributes = winParams
    }

    /**
     * 如果子类使用非默认的StatusBar,就重写此方法,传入布局的id
     */
    fun setThemeColorId(colorId: Int = R.color.common_blue) {
        this.mColorId = colorId
    }

    /**
     * 子类是否需要实现沉浸式,默认需要
     */
    fun setNeedLoadStatusBar(on: Boolean = true){
        isNeedLoadBar = on
    }

    /**
     * 子类是否需要实现沉浸式,默认需要
     */
    fun isNeedLoadStatusBar(): Boolean {
        return isNeedLoadBar
    }
    //------ 设置沉浸式  -------//


    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.finishActivity(this)
    }

    // 获取Window中视图content
    val contentView: View
        get() {
            val content = find<FrameLayout>(android.R.id.content) // 记住是android最外面的android.R.id.content【这个不是定义的，是android本身的】
            content.getChildAt(0).setBackgroundColor(R.color.colorRefreshColor)
            return content.getChildAt(0)
        }

}
