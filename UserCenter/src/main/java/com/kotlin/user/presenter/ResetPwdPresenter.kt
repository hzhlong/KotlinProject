package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.presenter.view.ForgetPwdView
import com.kotlin.user.presenter.view.ResetView
import com.kotlin.user.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 重置密码Presenter
 */
class ResetPwdPresenter @Inject constructor(): BasePresenter<ResetView>() {

    @Inject
    lateinit var userService: UserServiceImpl

    fun resetPwd(mobile:String, verifyCode:String){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        userService.resetPwd(mobile,verifyCode)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if(t)
                            mView.onResetResult("验证成功")
                    }
                },lifecycleProvider)
    }
}