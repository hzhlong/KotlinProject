package com.kotlin.pay.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.pay.presenter.view.PayView
import com.kotlin.pay.service.impl.PayServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 支付 Presenter
 */
class PayPresenter @Inject constructor(): BasePresenter<PayView>() {

    @Inject
    lateinit var payService: PayServiceImpl

    // 获取支付签名
    fun getPaySign(orderId: Int, totalPrice: Long){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        payService.getPaySign(orderId, totalPrice)
                .execute(object : BaseSubscriber<String>(mView){
                    override fun onNext(t: String) {
                        mView.onGetSignResult(t)
                    }
                },lifecycleProvider)
    }

    // 订单支付，同步订单状态
    fun payOrder(orderId: Int) {
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        payService.payOrder(orderId)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        mView.onPayOrderResult(t)
                    }
                },lifecycleProvider)
    }


}