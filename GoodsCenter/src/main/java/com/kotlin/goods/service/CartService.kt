package com.kotlin.goods.service

import com.kotlin.goods.data.protocol.CartGoods
import io.reactivex.Observable

/**
 * create by 何兆鸿 as 2018/4/17
 * 购物车数据层功能接口
 */
interface CartService {

    // 获取购物车列表
    fun getCartList(): Observable<MutableList<CartGoods>?>

    // 添加商品到购物车
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                goodsCount: Int, goodsSku: String): Observable<Int>

    // 删除购物车商品
    fun deleteCartList(list: List<Int>): Observable<Boolean>

    // 购物车结算
    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int>
}