package com.kotlin.user.presenter.view

import com.kotlin.base.presenter.view.BaseView

/**
 * create by 何兆鸿 as 2018/4/17
 * 注册 view视图回调
 */
interface RegisterView:BaseView {

    fun onRegisterResult(result:String)
}
