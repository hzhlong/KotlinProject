package com.kotlin.base.presenter

import android.content.Context
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.base.utils.NetWorkUtils
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import javax.inject.Inject


/**
 * create by 何兆鸿 as 2018/4/17
 * Presenter基类
 */
open class BasePresenter<T:BaseView> {

    lateinit var mView:T

    @Inject
    lateinit var lifecycleProvider: LifecycleProvider<*> // RxLifectcle用來解決Rx內存泄露

    @Inject
    lateinit var context:Context

    // 检查网络是否可用
    fun checkNetWork(): Boolean{
        if(NetWorkUtils.isNetWorkAvailable(context)){
            return true
        }
        mView.onError("网络不可用")
        return false
    }


}