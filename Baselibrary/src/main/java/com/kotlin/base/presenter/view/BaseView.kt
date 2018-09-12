package com.kotlin.base.presenter.view

/**
 * create by 何兆鸿 as 2018/4/17
 * View基类
 */
interface BaseView{
    fun showLoading() // 显示加载
    fun hideLoading() // 隐藏加载
    fun onError(text: String)     // 错误回调
}
