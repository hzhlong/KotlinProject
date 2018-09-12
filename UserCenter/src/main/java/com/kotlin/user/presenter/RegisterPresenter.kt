package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.presenter.view.RegisterView
import com.kotlin.user.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 注册Presenter
 */
class RegisterPresenter @Inject constructor(): BasePresenter<RegisterView>() {

    @Inject
    lateinit var userService: UserServiceImpl

    fun register(mobile:String, pwd:String, verifyCode:String){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        userService.register(mobile,pwd,verifyCode)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if(t)
                            mView.onRegisterResult("注册成功")
                    }
                },lifecycleProvider)
    }
}