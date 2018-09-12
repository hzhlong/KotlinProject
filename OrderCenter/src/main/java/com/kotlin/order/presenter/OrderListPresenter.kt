package com.kotlin.order.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.presenter.view.OrderListView
import com.kotlin.order.service.impl.OrderServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 订单列表Presenter
 */
class OrderListPresenter @Inject constructor(): BasePresenter<OrderListView>() {

    @Inject
    lateinit var orderService: OrderServiceImpl

    // 根据订单状态获取订单列表
    fun getOrderList(orderStatus:Int){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        orderService.getOrderList(orderStatus)
                .execute(object : BaseSubscriber<MutableList<Order>?>(mView){
                    override fun onNext(t: MutableList<Order>?) {
                        mView.onGetOrderListResult(t)
                    }
                },lifecycleProvider)
    }

    // 订单确认收货
    fun confirmOrder(orderId: Int){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        orderService.confirmOrder(orderId)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        mView.onConfirmOrderResult(t)
                    }
                },lifecycleProvider)
    }

    // 取消订单
    fun cacnelOrder(orderId: Int){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        orderService.cancelOrder(orderId)
                .execute(object : BaseSubscriber<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        mView.onCancelOrderResult(t)
                    }
                },lifecycleProvider)
    }


}