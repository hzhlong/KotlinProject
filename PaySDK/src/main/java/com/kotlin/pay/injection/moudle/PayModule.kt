package com.kotlin.order.injection.moudle

import com.kotlin.pay.service.PayService
import com.kotlin.pay.service.impl.PayServiceImpl
import dagger.Module
import dagger.Provides

/**
 * create by 何兆鸿 as 2018/4/19
 * 支付的注解Module工厂类
 */
@Module
class PayModule {

    @Provides
    fun providesPayService(service: PayServiceImpl): PayService {
        return service
    }
}