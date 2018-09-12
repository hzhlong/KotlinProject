package com.kotlin.base.common

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.injection.component.AppComponent
import com.kotlin.base.injection.component.DaggerAppComponent
import com.kotlin.base.injection.module.AppModule

/**
 * 作者：何兆鸿 on 2018/4/23.
 * 学无止境~
 * 基类Application
 */
// Android应用使用Multidex突破64K方法数限制-MultiDexApplication
// 【升级到2.3.1后不能自动引用 只能手动添加了, 在dependencies中添加： compile "com.android.support:multidex:1.0.1" 即可。 】
open class BaseApplication : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initAppInjection()
        initArouterConfig()
        initCrashLog()

        context = this
    }

    // 初始化日志收集器
    private fun initCrashLog() {
//        CrashHandler.init(this)
    }

    // 初始化Application Component的注解
    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

    // 初始化路由框架，用于跨模块操作：例如多模块跳转等
    private fun initArouterConfig(){
        //ARouter初始化
//        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
//            ARouter.openLog()     // 打印日志
//            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
        ARouter.openLog()     // 打印日志
        ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    companion object {
        lateinit var context: Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
