package com.kotlin.user.service

import com.kotlin.user.data.protocol.UserInfo
import io.reactivex.Observable


/**
 * create by 何兆鸿 as 2018/4/17
 * 用户功能接口
 */
interface UserService{
    fun register(mobile:String,pwd:String,verifyCode:String): Observable<Boolean>

    fun login(mobile:String,pwd:String,pushId:String): Observable<UserInfo>

    fun forgetPwd(mobile:String,verifyCode:String): Observable<Boolean>

    fun resetPwd(mobile:String,pwd:String): Observable<Boolean>

    fun editUser(userIcon:String,userName:String,userGender:String,userSign:String): Observable<UserInfo>
}
