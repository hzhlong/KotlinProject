package com.kotlin.user.data.protocol

/**
 * create by 何兆鸿 as 2018/4/19
 * 注册请求体
 */
data class RegisterReq(val mobile: String, val pwd: String, val verifyCode: String)
