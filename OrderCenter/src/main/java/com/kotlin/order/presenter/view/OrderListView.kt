package com.kotlin.order.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.order.data.protocol.Order

/**
 * create by 何兆鸿 as 2018/4/17
 * 订单列表 view视图回调
 */
interface OrderListView : BaseView {

    //获取订单列表回调
    fun onGetOrderListResult(result: MutableList<Order>?)

    //确认订单回调
    fun onConfirmOrderResult(result: Boolean)

    //取消订单回调
    fun onCancelOrderResult(result: Boolean)
}