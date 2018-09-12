package com.kotlin.goods.ui.activity

import android.os.Bundle
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.goods.R
import com.kotlin.goods.ui.fragment.CartFragment

/**
 * 作者：何兆鸿 on 2018/4/26.
 * 学无止境~
 *
 *  购物车Activity
    只是Fragment一个壳
 */
class CartActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cart)

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_cart)
        (fragment as CartFragment).setBackVisible(true)

    }

}