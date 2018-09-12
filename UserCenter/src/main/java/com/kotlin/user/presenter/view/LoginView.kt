package com.kotlin.user.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.user.data.protocol.UserInfo

/**
 * create by 何兆鸿 as 2018/4/17
 * 登录 view视图回调
 */
interface LoginView:BaseView {

    fun onLoginResult(result:UserInfo)
}
