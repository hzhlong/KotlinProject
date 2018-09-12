package com.kotlin.goods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.ext.loadUrl
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.goods.R
import com.kotlin.goods.event.GoodsDetailImageEvent
import kotlinx.android.synthetic.main.fragment_goods_detail_tab_two.*

/**
 * 作者：何兆鸿 on 2018/4/24.
 * 学无止境~
 * 商品详情TabTwoFragment
 */
class GoodsDetailTabTwoFragment : BaseFragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_goods_detail_tab_two, container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) { // View已经加载完成
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    /**
     * 初始化监听，商品详情获取成功后，加载当前页面
     */
    private fun initObserve() {
        Bus.observe<GoodsDetailImageEvent>()
                .subscribe{
                    t: GoodsDetailImageEvent ->
                    run{
                        mGoodsDetailOneIv.loadUrl(t.imgOne)
                        mGoodsDetailOneIv.loadUrl(t.imgTwo)
                    }
                }
                .registerInBus(this)
    }

    /**
     * 取消事件监听
     */
    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}