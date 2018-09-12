package com.kotlin.user.data.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.user.data.api.UserApi
import com.kotlin.user.data.protocol.*
import io.reactivex.Observable
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/19
 * 用户相关的api（真正去访问网络）
 */
class UserRepository @Inject constructor(){

    // 注册
    fun register(mobile:String,pwd:String,verifyCode:String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
                .register(RegisterReq(mobile,pwd,verifyCode))
    }

    // 登录
    fun login(mobile:String,pwd:String,pushId:String): Observable<BaseResp<UserInfo>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
                .login(LoginReq(mobile,pwd,pushId))
    }

    // 忘记密码
    fun forgetPwd(mobile:String,verifyCode: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
                .forgetPwd(ForgetPwdReq(mobile,verifyCode))
    }

    // 重置密码
    fun resetPwd(mobile:String,pwd: String): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UserApi::class.java)
                .resetPwd(ResetPwdReq(mobile,pwd))
    }

    // 编辑用户资料
    fun editUser(userIcon:String,userName:String,
                 userGender:String,userSign:String): Observable<BaseResp<UserInfo>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .editUser(EditUserReq(userIcon,userName,userGender,userSign))
    }
}
