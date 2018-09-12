package com.kotlin.goods.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.goods.data.protocol.Category

/**
 * create by 何兆鸿 as 2018/4/17
 * 商品分类 view视图回调
 */
interface CategoryView : BaseView {

    fun onGetCategoryResult(result: MutableList<Category>?)
}