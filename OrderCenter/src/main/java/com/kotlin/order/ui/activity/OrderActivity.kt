package com.kotlin.order.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.order.R
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.common.OrderStatus
import com.kotlin.order.ui.adpater.OrderVpAdapter
import kotlinx.android.synthetic.main.activity_order.*

/**
 * 作者：何兆鸿 on 2018/5/18.
 * 学无止境~
 * desc 订单Activity
        主要包括不同订单状态的Fragment
 */
class OrderActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mOrderTab.tabMode = TabLayout.MODE_FIXED // 当tab的内容超过屏幕宽度是否支持横向水平滑动;MODE_FIXED是不支持，默认不支持水平滑动。
        mOrderVp.adapter = OrderVpAdapter(supportFragmentManager, this)
        //TabLayout关联ViewPager
        mOrderTab.setupWithViewPager(mOrderVp)

        mOrderVp.offscreenPageLimit = 4 // 设置

        //根据订单状态设置当前页面
        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS,OrderStatus.ORDER_ALL)
    }
}
