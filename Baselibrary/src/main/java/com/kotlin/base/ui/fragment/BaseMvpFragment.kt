package com.kotlin.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.base.widgets.ProgressLoading
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * 作者：何兆鸿 on 2018/4/21.
 * 学无止境~
 * Fragment父类
 */
abstract class BaseMvpFragment<T: BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter:T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading // 进度加载框

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initActivityInjection()
        injectComponent()

        mLoadingDialog = ProgressLoading.create(activity) //初始加载框

        return super.onCreateView(inflater, container, savedInstanceState)
    }



    // 初始化Componment注解，用于子类实现【Dagger注册】
    abstract fun injectComponent()

    // 初始化Activity通用化注解
    private fun initActivityInjection() {

        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((activity.application as BaseApplication).appComponent)
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .activityModule(ActivityModule(activity)).build()
    }


    // 显示加载
    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    // 隐藏加载
    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    // 错误回调
    override fun onError(text: String) {
        toast(text)
    }
}