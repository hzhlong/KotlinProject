package com.kotlin.goods.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.Goods
import com.kotlin.goods.presenter.view.GoodsDetailView
import com.kotlin.goods.presenter.view.GoodsListView
import com.kotlin.goods.service.impl.CartServiceImpl
import com.kotlin.goods.service.impl.GoodsServiceImpl
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 商品详情 Presenter
 */
class GoodsDetailPresenter @Inject constructor(): BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var goodsService: GoodsServiceImpl

    @Inject
    lateinit var cartService: CartServiceImpl

    // 获取商品详情信息
    fun getGoodsDetail(goodsId: Int){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        goodsService.getGoodsDetail(goodsId)
                .execute(object : BaseSubscriber<Goods>(mView){
                    override fun onNext(t: Goods) {
                        mView.onGetGoodsDetailResult(t)
                    }
                },lifecycleProvider)
    }

    // 添加商品到购物车
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long, goodsCount: Int, goodsSku: String){
        /**
         * 业务逻辑
         */
        if(!checkNetWork()){ // 检查网络是否可用
            return
        }
        mView.showLoading()

        cartService.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku)
                .execute(object : BaseSubscriber<Int>(mView){
                    override fun onNext(t: Int) {
                        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE,t)
                        mView.onAddCartResult(t)
                    }
                },lifecycleProvider)
    }


}