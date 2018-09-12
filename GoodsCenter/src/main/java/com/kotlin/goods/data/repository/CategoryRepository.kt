package com.kotlin.goods.data.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.goods.data.api.CategoryApi
import com.kotlin.goods.data.protocol.Category
import com.kotlin.goods.data.protocol.GetCategoryReq
import io.reactivex.Observable
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/19
 * 商品分类相关的api（真正去访问网络）
 */
class CategoryRepository @Inject constructor(){

    // 获取商品分类
    fun getCategory(parentId:Int): Observable<BaseResp<MutableList<Category>?>> {
        return RetrofitFactory.Companion.instance.create(CategoryApi::class.java).getCategory(GetCategoryReq(parentId))
    }

}