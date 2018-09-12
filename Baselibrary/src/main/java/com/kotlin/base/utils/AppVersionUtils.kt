package com.kotlin.base.utils

import android.content.Context

/**
 * 作者：何兆鸿 on 2018/6/6.
 * 学无止境~
 * App版本信息类
 */
object AppVersionUtils {

    /**
     * 获取版本号
     */
    fun getVersionCode(context: Context): Int {
        var versionCode: Int
        val pm = context.packageManager
        val pi = pm.getPackageInfo(context.packageName, 0)
        versionCode = pi.versionCode

        return versionCode
    }

    /**
     * 获取版本名
     */
    fun getVersionNamee(context: Context): String {
        var versionName: String
        val pm = context.packageManager
        val pi = pm.getPackageInfo(context.packageName, 0)
        versionName = pi.versionName

        return versionName
    }

    
}
