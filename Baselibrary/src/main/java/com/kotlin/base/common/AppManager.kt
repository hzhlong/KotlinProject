package com.kotlin.base.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * 作者：何兆鸿 on 2018/4/21.
 * 学无止境~
 * App管理器，负责管理Activity
 */
class AppManager private constructor(){

    private val activityStatck:Stack<Activity> = Stack()

    companion object {
        val instance:AppManager by lazy { AppManager() }
    }

    // 入栈
    fun addActivity(activity: Activity){
        activityStatck.add(activity)
    }

    // 出栈
    fun finishActivity(activity: Activity){
        activityStatck.remove(activity)
    }

    // 获取当前栈顶
    fun currentActivity(){
        activityStatck.lastElement()
    }

    // 清除栈
    fun finishAllActivity(){
        for (activity in activityStatck){
            activity.finish()
        }
        activityStatck.clear()
    }

    // 退出应用程序
    fun exitApp(context: Context){
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }

}