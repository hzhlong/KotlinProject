package com.kotlin.order.service

import com.kotlin.order.data.protocol.Order
import io.reactivex.Observable

/**
 * create by 何兆鸿 as 2018/4/17
 * 订单业务接口
 */
interface OrderService {

    // 根据ID查询订单
    fun getOrderById(orderId: Int): Observable<Order>

    // 提交订单
    fun submitOrder(order: Order): Observable<Boolean>

    // 根据状态查询订单列表
    fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?>

    // 取消订单
    fun cancelOrder(orderId: Int): Observable<Boolean>

    // 确认订单
    fun confirmOrder(orderId: Int): Observable<Boolean>

}