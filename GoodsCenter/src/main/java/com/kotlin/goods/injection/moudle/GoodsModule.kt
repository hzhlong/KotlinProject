package com.kotlin.goods.injection.moudle

import com.kotlin.goods.service.GoodsService
import com.kotlin.goods.service.impl.GoodsServiceImpl
import dagger.Module
import dagger.Provides

/**
 * create by 何兆鸿 as 2018/4/19
 * 商品信息的注解Module工厂类
 */
@Module
class GoodsModule {

    @Provides
    fun providesGoodsService(service: GoodsServiceImpl): GoodsService {
        return service
    }
}