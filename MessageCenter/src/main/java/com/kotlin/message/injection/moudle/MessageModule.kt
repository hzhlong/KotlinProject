package com.kotlin.message.injection.moudle

import com.kotlin.message.service.MessageService
import com.kotlin.message.service.impl.MessageServiceImpl
import dagger.Module
import dagger.Provides

/**
 * create by 何兆鸿 as 2018/4/19
 * 消息模块业务的注解Module工厂类
 */
@Module
class MessageModule {

    @Provides
    fun providesCartService(service: MessageServiceImpl): MessageService {
        return service
    }
}