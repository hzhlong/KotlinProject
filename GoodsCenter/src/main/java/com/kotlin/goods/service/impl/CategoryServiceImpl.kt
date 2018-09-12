package com.kotlin.goods.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.goods.data.protocol.Category
import com.kotlin.goods.data.repository.CategoryRepository
import com.kotlin.goods.service.CategoryService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 商品分类功能接口实现类
 */
class CategoryServiceImpl @Inject constructor(): CategoryService {

    @Inject
    lateinit var repository:CategoryRepository

    // 获取商品分类信息
    override fun getCategory(parentId:Int): Observable<MutableList<Category>?> {

        return repository.getCategory(parentId).convert()
    }
}