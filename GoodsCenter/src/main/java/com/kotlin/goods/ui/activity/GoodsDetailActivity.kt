package com.kotlin.goods.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Gravity
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.goods.R
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.event.AddCartEvent
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.goods.ui.adpater.GoodsDetailVpAdapter
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.common.isLogined
import kotlinx.android.synthetic.main.activity_goods_detail.*
import org.jetbrains.anko.startActivity
import q.rorbin.badgeview.QBadgeView

/**
 * 作者：何兆鸿 on 2018/4/26.
 * 学无止境~
 * desc 商品详情界面
 */
class GoodsDetailActivity  : BaseActivity() {

    private lateinit var mCartBdaget : QBadgeView // 购物车角标

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_detail)

        initView()
        initObserve()
        loadCartSize()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mGoodsDetailTab.tabMode = TabLayout.MODE_FIXED // 当tab的内容超过屏幕宽度是否支持横向水平滑动;MODE_FIXED是不支持，默认不支持水平滑动。
        mGoodsDetailVp.adapter = GoodsDetailVpAdapter(supportFragmentManager)
        //TabLayout关联ViewPager
        mGoodsDetailTab.setupWithViewPager(mGoodsDetailVp)

        mAddCartBtn.onClick {

            afterLogin { // 顶级函数
                Bus.send(AddCartEvent())
            }
        }

        mEnterCartTv.onClick {
            afterLogin { // 顶级函数
                startActivity<CartActivity>()
            }
        }

        mLeftIv.onClick {
            finish()
        }

        mCartBdaget = QBadgeView(this)
    }

    /**
     * 加载购物车数量
     */
    private fun loadCartSize() {
        setCartBadge()
    }

    /**
     * 监听购物车数量变化
     */
    private fun initObserve() {
        Bus.observe<UpdateCartSizeEvent>()
                .subscribe{
                    setCartBadge()
                }.registerInBus(this)
    }

    /**
     * 设置购物车标签
     */
    private fun setCartBadge() {
        if(isLogined()) {
            mCartBdaget.badgeGravity = Gravity.END or Gravity.TOP
            mCartBdaget.setGravityOffset(22f, -2f , true)
            mCartBdaget.setBadgeTextSize(6f, true)
            mCartBdaget.bindTarget(mEnterCartTv).badgeNumber = AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE)
        }
    }

    /**
     * 取消事件监听
     */
    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}