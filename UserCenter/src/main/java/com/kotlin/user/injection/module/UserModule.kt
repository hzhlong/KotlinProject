package com.kotlin.user.injection.module

import com.kotlin.user.service.UserService
import com.kotlin.user.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

/**
 * create by 何兆鸿 as 2018/4/19
 * 用户信息的注解Module工厂类
 */
@Module
class UserModule{

    @Provides
    fun providesUserService(service:UserServiceImpl):UserService{
        return service
    }
}
