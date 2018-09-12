package com.kotlin.base.injection.component

import android.app.Activity
import android.content.Context
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.ActivityScope
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Component

/**
 * create by 何兆鸿 as 2018/4/19
 * Activity组件注解连接类的基类，用来管理所有Component（AppComponent只有一个，但是ActivityComponent可以有多个，可以让其依赖AppComponent）
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class, LifecycleProviderModule::class))
interface ActivityComponent {

    // 暴露出ActivityMoudle类中的providesActivity()方法 （直接取是拿不到activity，需要把方法暴露出來才行）
    fun activity(): Activity
    // 暴露出AppMoudle类中的providesContext()方法  （直接取是拿不到context，需要把方法暴露出來才行）
    fun context(): Context
    // 暴露出LifecycleProviderModule类中的providesLifecycleProvider()方法（直接取是拿不到lifecycleProvider，需要把方法暴露出來才行）
    fun lifecycleProvider(): LifecycleProvider<*>
}
