package com.kotlin.message.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.message.data.protocol.Message
import com.kotlin.message.data.repository.MessageRepository
import com.kotlin.message.service.MessageService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 消息模块业务功能接口实现类
 */
class MessageServiceImpl @Inject constructor(): MessageService {

    @Inject
    lateinit var repository: MessageRepository

    /**
     * 获取消息列表
     */
    override fun getMessageList(): Observable<MutableList<Message>?> {

        return repository.getMessageList().convert()
    }
}