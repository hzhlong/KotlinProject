package com.kotlin.base.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.base.widgets.ProgressLoading
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * MVPActivity基类
 */
abstract class BaseMVPActivity<T:BasePresenter<*>> : BaseActivity(),BaseView {

    @Inject
    lateinit var mPresenter:T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading // 进度加载框

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()

        mLoadingDialog = ProgressLoading.create(this) //初始加载框
        ARouter.getInstance().inject(this) // 如果使用到解析URL中的参数【@Autowired】时需要配置这个
    }

    // 初始化Componment注解，用于子类实现
    abstract fun injectComponent()

    // 初始化Activity通用化注解
    private fun initActivityInjection() {

        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .activityModule(ActivityModule(this)).build()
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
