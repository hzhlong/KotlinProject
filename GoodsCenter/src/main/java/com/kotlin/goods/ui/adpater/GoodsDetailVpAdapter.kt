package com.kotlin.goods.ui.adpater

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kotlin.goods.ui.fragment.GoodsDetailTabOneFragment
import com.kotlin.goods.ui.fragment.GoodsDetailTabTwoFragment

/**
 * 作者：何兆鸿 on 2018/4/27.
 * 学无止境~
 * desc 商品详情viewPager的adpater
 */

class GoodsDetailVpAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){

    private val titles = arrayOf("商品","详情")

    override fun getItem(position: Int): Fragment {
        return if(position == 0){
            GoodsDetailTabOneFragment()
        }else{
            GoodsDetailTabTwoFragment()
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

}
