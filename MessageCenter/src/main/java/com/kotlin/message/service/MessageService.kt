package com.kotlin.message.service

import com.kotlin.message.data.protocol.Message
import io.reactivex.Observable

/**
 * create by 何兆鸿 as 2018/4/17
 * 消息模块业务功能接口
 */
interface MessageService {

    // 获取消息列表
    fun getMessageList(): Observable<MutableList<Message>?>

}