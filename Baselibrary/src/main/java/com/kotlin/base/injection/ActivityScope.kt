package com.kotlin.base.injection

import javax.inject.Scope



/**
 * create by 何兆鸿 as 2018/4/19
 * @Scope 标注是Activity的作用域，没啥用，只是标注下让大家看的更清楚些而已，方便理解里面的结构是什么样子的
 */

/**
 * Activity级别 作用域
 */
@Scope
@MustBeDocumented
@kotlin.annotation.Retention()
annotation class ActivityScope
