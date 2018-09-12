package com.kotlin.base.rx

import android.util.Log
import com.kotlin.base.presenter.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import rx.Subscriber


/***
 * author 何兆鸿 to time 2018/4/22
 * 学无止境~
 * desc 自定义订阅者Subcriber
 */
open class BaseSubscriber<T>(val baseView:BaseView): Observer<T>{

    private var disposable: Disposable? = null // 根据源码分析，取消网络请求的方法在rxjava2中被封装在Disposable类中

    /**
     * 订阅开始时调用
     * 相当于Rxjava1 @Override public void onStart() {}
     */
    override fun onSubscribe(disposable: Disposable) {
        this.disposable = disposable
    }

    override fun onComplete() {
        baseView.hideLoading()
        onCancelDisposed()
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        if (e is BaseException){
            baseView.onError(e.msg)
        }
        onCancelDisposed()
    }

    // 解绑订阅，释放资源
    private fun onCancelDisposed() {
        disposable?.let {
            if (disposable!!.isDisposed) {
                disposable!!.dispose()
                Log.e("======call", "cancel")
            }
        }
    }

}
