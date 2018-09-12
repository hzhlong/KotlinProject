package com.kotlin.goods.injection.moudle

import com.kotlin.goods.service.CartService
import com.kotlin.goods.service.impl.CartServiceImpl
import dagger.Module
import dagger.Provides

/**
 * create by 何兆鸿 as 2018/4/19
 * 购物车数据层信息的注解Module工厂类
 */
@Module
class CartModule {

    @Provides
    fun providesCartService(service: CartServiceImpl): CartService {
        return service
    }
}