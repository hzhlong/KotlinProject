package com.kotlin.user.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.base.ext.convertBoolean
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.data.repository.UserRepository
import com.kotlin.user.service.UserService
import io.reactivex.Observable
import javax.inject.Inject


/**
 * create by 何兆鸿 as 2018/4/17
 * 用户功能接口实现类
 */
class UserServiceImpl @Inject constructor(): UserService{

    @Inject
    lateinit var repository:UserRepository

    // 注册
    override fun register(mobile:String,pwd:String,verifyCode:String): Observable<Boolean> {

        return repository.register(mobile,pwd,verifyCode).convertBoolean()

        /*
        // 原本是写这些重复的代码，现在封装到一个类中
        return repository.register(mobile,pwd,verifyCode)
        .flatMap(object : Func1<BaseResp<String>,Observable<Boolean>>{
            override fun call(t: BaseResp<String>): Observable<Boolean> {
                if(t.status != 0){
                    return Observable.error(BaseException(t.status,t.message))
                }
                return Observable.just(true)
            }
        })*/
    }

    // 登录
    override fun login(mobile:String,pwd:String,pushId:String): Observable<UserInfo> {

        return repository.login(mobile,pwd,pushId).convert()
    }

    // 忘记密码
    override fun forgetPwd(mobile: String, verifyCode: String): Observable<Boolean> {

        return repository.forgetPwd(mobile,verifyCode).convertBoolean()
    }

    // 重置密码
    override fun resetPwd(mobile: String, pwd: String): Observable<Boolean> {

        return repository.resetPwd(mobile,pwd).convertBoolean()
    }

    // 编辑用户资料
    override fun editUser(userIcon:String,userName:String,
                          userGender:String,userSign:String): Observable<UserInfo> {

        return repository.editUser(userIcon,userName, userGender,userSign).convert()
    }
}
