package com.kotlin.base.injection.module

import android.app.Activity
import com.kotlin.base.injection.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * create by 何兆鸿 as 2018/4/19
 * Activity注解Module工厂类的基类
 */
@Module
class ActivityModule(private val activity: Activity){

    @Provides
    @ActivityScope
    fun providesActivity(): Activity {
        return activity
    }
}
