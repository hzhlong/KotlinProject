package com.kotlin.order.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.order.data.protocol.Order

/**
 * 作者：何兆鸿 on 2018/5/18.
 * 学无止境~
 * 订单详情 view视图回调
 */

interface OrderDetailView : BaseView {

    fun onGetOrderByIdResult(result: Order)
}
