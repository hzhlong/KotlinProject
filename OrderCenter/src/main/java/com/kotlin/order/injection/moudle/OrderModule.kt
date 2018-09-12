package com.kotlin.order.injection.moudle

import com.kotlin.order.service.OrderService
import com.kotlin.order.service.impl.OrderServiceImpl
import dagger.Module
import dagger.Provides

/**
 * create by 何兆鸿 as 2018/4/19
 * 订单信息的注解Module工厂类
 */
@Module
class OrderModule {

    @Provides
    fun providesOrderService(service: OrderServiceImpl): OrderService {
        return service
    }
}