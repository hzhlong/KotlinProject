package com.kotlin.goods.injection.component

import com.kotlin.base.injection.PerComponmentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.goods.injection.moudle.CartModule
import com.kotlin.goods.ui.fragment.CartFragment
import dagger.Component

/** 如果依赖的Component还有一个自己的Scope作用域,那自身也需要添加一个Scope,同时自身的Scope不能与依赖的Component的Scope作用域重名{正常都不应该重名哈}
 * 例如：ActivityComponent有@ActivityScope作用域，那么UserComponent也需要添加一个自己的@PerComponmentScope*/

// 购物车数据层Component
@PerComponmentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(CartModule::class))

interface CartComponent {
    fun inject(fragment: CartFragment)
}