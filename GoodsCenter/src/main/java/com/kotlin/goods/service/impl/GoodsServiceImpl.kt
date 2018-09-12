package com.kotlin.goods.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.data.repository.GoodsRepository
import com.kotlin.goods.service.GoodsService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 商品列表功能接口实现类
 */
class GoodsServiceImpl @Inject constructor(): GoodsService {
    @Inject
    lateinit var repository: GoodsRepository

    // 获取商品列表信息
    override fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?> {

        return repository.getGoodsList(categoryId, pageNo).convert()
    }

    // 根据关键字 搜索商品
    override fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<MutableList<Goods>?> {

        return repository.getGoodsListByKeyword(keyword, pageNo).convert()
    }

    // 商品详情
    override fun getGoodsDetail(goodsId: Int): Observable<Goods> {
        return repository.getGoodsDetail(goodsId).convert()
    }
}