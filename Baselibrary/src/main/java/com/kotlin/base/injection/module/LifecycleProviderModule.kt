package com.kotlin.base.injection.module

import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Module
import dagger.Provides

/**
 * create by 何兆鸿 as 2018/4/19
 * RxLifectcle用來解決Rx內存泄露
 * RxLifectcle注解Module工厂类的基类
 */
@Module
class LifecycleProviderModule(private val lifecycleProvider: LifecycleProvider<*>){

    @Provides
    fun providesLifecycleProvider(): LifecycleProvider<*> {
        return lifecycleProvider
    }
}