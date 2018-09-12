package com.kotlin.goods.service

import com.kotlin.goods.data.protocol.Goods
import io.reactivex.Observable

/**
 * create by 何兆鸿 as 2018/4/17
 * 商品列表功能接口
 */
interface GoodsService {

    // 获取商品列表信息
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?>

    // 根据关键字 搜索商品
    fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<MutableList<Goods>?>

    // 商品详情
    fun getGoodsDetail(goodsId: Int): Observable<Goods>
}