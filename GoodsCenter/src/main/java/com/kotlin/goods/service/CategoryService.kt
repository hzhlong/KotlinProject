package com.kotlin.goods.service

import com.kotlin.goods.data.protocol.Category
import io.reactivex.Observable

/**
 * create by 何兆鸿 as 2018/4/17
 * 商品分类功能接口
 */
interface CategoryService {

    fun getCategory(parentId:Int): Observable<MutableList<Category>?>

}