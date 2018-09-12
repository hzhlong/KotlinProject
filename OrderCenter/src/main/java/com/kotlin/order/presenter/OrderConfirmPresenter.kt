package com.kotlin.order.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.presenter.view.OrderConfirmView
import com.kotlin.order.service.impl.OrderServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 订单确认页Presenter
 */
class OrderConfirmPresenter @Inject constructor(): BasePresenter<OrderConfirmView>() {

    @Inject
    lateinit var orderService: OrderServiceImpl

    // 根据ID获取订单
    fun getOrderById(orderId:Int){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        orderService.getOrderById(orderId)
                .execute(object : BaseSubscriber<Order>(mView){
                    override fun onNext(t: Order) {
                        mView.onGetOrderByIdResult(t)
                    }
                },lifecycleProvider)
    }

    // 提交订单
    fun submitOrder(order:Order){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        orderService.submitOrder(order)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        mView.onSubmitOrderResult(t)
                    }
                },lifecycleProvider)
    }


}