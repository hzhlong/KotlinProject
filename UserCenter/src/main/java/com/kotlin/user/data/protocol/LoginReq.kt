package com.kotlin.user.data.protocol

/**
 * create by 何兆鸿 as 2018/4/19
 * 登录请求体
 */
data class LoginReq(val mobile:String, val pwd:String, val pushId:String)
