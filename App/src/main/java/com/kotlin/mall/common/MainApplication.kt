package com.kotlin.mall.common

import cn.jpush.android.api.JPushInterface
import com.kotlin.base.common.BaseApplication
import com.kotlin.mall.widgets.share.ShareManager

/**
 * 作者：何兆鸿 on 2018/5/23.
 * 学无止境~
 * 主工程Application
 */
class MainApplication : BaseApplication(){

    override fun onCreate() {
        super.onCreate()

        initJPush()
        initShareSDK()
    }

    // ShareSDK分享初始化
    private fun initShareSDK() {
        ShareManager.initSDK(this)
    }

    // 极光推送初始化
    private fun initJPush() {
        JPushInterface.setDebugMode(true) // debug模式在打包时记得设置为false，防止一些风险的发生
        JPushInterface.init(this)
    }
}