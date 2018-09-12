package com.kotlin.base.injection.component

import android.content.Context
import com.kotlin.base.injection.module.AppModule
import javax.inject.Singleton
import dagger.Component

/**
 * create by 何兆鸿 as 2018/4/19
 * app组件注解连接类的基类，用来管理所有Component（将需要实例化的类联系起来,不连接是无法实例化的）
 */
//1.@SingleTon是作用域的一种实现方式，可以这样理解，@Scope是个接口，而@SingleTon则是他的子类，所以我们平常使用的时候是使用他的子类
//2.SingleTon是Scope的一种默认实现
//3.其实Component有与否，并不会影响我们的程序，添加Component只是让大家更清晰的理解代码结构

@Singleton // Singleton并没有实际的能力，只是让大家知道Component是个单例的模式
@Component(modules = arrayOf(AppModule::class))
interface AppComponent{

    // 暴露出AppMoudle类中的providesContext()方法 （注解的名字不用一定要写inject,任何名字都可以的）
    fun context(): Context
}
