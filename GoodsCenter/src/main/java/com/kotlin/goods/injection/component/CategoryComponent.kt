package com.kotlin.user.injection.component

import com.kotlin.base.injection.PerComponmentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.goods.injection.moudle.CategoryModule
import com.kotlin.goods.ui.fragment.CategoryFragment
import dagger.Component


/**
 * create by 何兆鸿 as 2018/4/19
 * 用户组件注解连接类（将需要实例化的类联系起来,不连接是无法实例化的）并且让其组件依赖于ActivityComponent
 */
/** 如果依赖的Component还有一个自己的Scope作用域,那自身也需要添加一个Scope,同时自身的Scope不能与依赖的Component的Scope作用域重名{正常都不应该重名哈}
 * 例如：ActivityComponent有@ActivityScope作用域，那么UserComponent也需要添加一个自己的@PerComponmentScope*/

// 商品分类Component
@PerComponmentScope
@Component(dependencies = arrayOf(ActivityComponent::class),
        modules = arrayOf(CategoryModule::class))

interface CategoryComponent {
    fun inject(fragment: CategoryFragment)
}
