package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.presenter.view.UserInfoView
import com.kotlin.user.service.impl.UploadServiceImpl
import com.kotlin.user.service.impl.UserServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 用户信息Presenter
 */
class UserInfoPresenter @Inject constructor(): BasePresenter<UserInfoView>() {

    @Inject
    lateinit var userService: UserServiceImpl

    @Inject
    lateinit var uploadService: UploadServiceImpl

    fun getUploadToken(){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()
        uploadService.getUploadToken().execute(object : BaseSubscriber<String>(mView){
            override fun onNext(t: String) {
                mView.onGetUploadTokenResult(t)
            }
        },lifecycleProvider)

    }

    fun editUser(userIcon:String,userName:String, userGender:String,userSign:String){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()
        userService.editUser(userIcon,userName,userGender,userSign).execute(object : BaseSubscriber<UserInfo>(mView){
            override fun onNext(t: UserInfo) {
                mView.editUser(t)
            }
        },lifecycleProvider)

    }
}