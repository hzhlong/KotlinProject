package com.kotlin.order.injection.moudle

import com.kotlin.order.service.ShipAddressService
import com.kotlin.order.service.impl.ShipAddressServiceImpl
import dagger.Module
import dagger.Provides

/**
 * create by 何兆鸿 as 2018/4/19
 * 收货人列表的注解Module工厂类
 */
@Module
class ShipAddressModule {

    @Provides
    fun providesShipAddressService(service: ShipAddressServiceImpl): ShipAddressService {
        return service
    }
}