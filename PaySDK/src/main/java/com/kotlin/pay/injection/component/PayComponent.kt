package com.kotlin.order.injection.component

import com.kotlin.base.injection.PerComponmentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.order.injection.moudle.PayModule
import com.kotlin.pay.ui.activity.CashRegisterActivity
import dagger.Component

/** 如果依赖的Component还有一个自己的Scope作用域,那自身也需要添加一个Scope,同时自身的Scope不能与依赖的Component的Scope作用域重名{正常都不应该重名哈}
 * 例如：ActivityComponent有@ActivityScope作用域，那么UserComponent也需要添加一个自己的@PerComponmentScope*/

// 支付Component
@PerComponmentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
       modules = arrayOf(PayModule::class))

interface PayComponent {
    fun inject(activity: CashRegisterActivity)
}