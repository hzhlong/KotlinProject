package com.kotlin.base.injection.module

import android.content.Context
import com.kotlin.base.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * create by 何兆鸿 as 2018/4/19
 * app注解Module工厂类的基类
 */
@Module
class AppModule(private val context: BaseApplication){

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }
}
