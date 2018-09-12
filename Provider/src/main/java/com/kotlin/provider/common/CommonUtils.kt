package com.kotlin.provider.common

import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.provider.router.RouterPath
import java.lang.reflect.Method

/**
 * 作者：何兆鸿 on 2018/4/24.
 * 学无止境~
 * 逻辑层面的顶级函数【 在kotlin中有一种函数叫顶级函数，也叫全局函数，全局都能使用】
 */

/**
 * 顶级函数，判断是否登录
 */
fun isLogined(): Boolean {
    return AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN).isNotEmpty()
}

/**
 * 顶级函数
 * 如果已经登录，进行传入的方法处理
 * 如果没有登录，进入登录界面
 */
fun afterLogin(method: () -> Unit) {
    if(isLogined()){
        method()
    }else{
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
    }
}
