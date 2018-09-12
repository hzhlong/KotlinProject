package com.kotlin.provider

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 作者：何兆鸿 on 2018/5/24.
 * 学无止境~
 * ARounter跨模块接口调用需要实现IProvider 接口定义
 */
interface PushProvider : IProvider {

    fun getPushId(): String
}