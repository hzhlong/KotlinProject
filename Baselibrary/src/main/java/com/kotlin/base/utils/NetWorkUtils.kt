package com.kotlin.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

/**
 * 作者：何兆鸿 on 2018/4/21.
 * 学无止境~
 * 网络工具
 */
object NetWorkUtils {

    /**
     * 判断网络是否可用
     */
    @SuppressLint("MissingPermission")
    fun isNetWorkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    /**
     * 检测wifi是否连接
     */
    @SuppressLint("MissingPermission")
    fun isWifiConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 检测3G是否连接
     */
    @SuppressLint("MissingPermission")
    fun is3gConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE
    }
}
