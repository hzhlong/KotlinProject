package com.kotlin.goods.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.presenter.view.GoodsListView
import com.kotlin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 商品列表 Presenter
 */
class GoodsListPresenter @Inject constructor(): BasePresenter<GoodsListView>() {

    @Inject
    lateinit var goodsService: GoodsServiceImpl

    // 获取商品列表信息
    fun getGoodsList(categoryId: Int, pageNo: Int){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        goodsService.getGoodsList(categoryId, pageNo)
                .execute(object : BaseSubscriber<MutableList<Goods>?>(mView){
                    override fun onNext(t: MutableList<Goods>?) {
                        mView.onGetGoodsListResult(t)
                    }
                },lifecycleProvider)
    }

    //  根据关键字 搜索商品
    fun getGoodsListByKeyword(keyword: String, pageNo: Int){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        goodsService.getGoodsListByKeyword(keyword, pageNo)
                .execute(object : BaseSubscriber<MutableList<Goods>?>(mView){
                    override fun onNext(t: MutableList<Goods>?) {
                        mView.onGetGoodsListResult(t)
                    }
                },lifecycleProvider)
    }
}