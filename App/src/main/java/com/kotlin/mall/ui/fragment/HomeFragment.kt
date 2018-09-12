package com.kotlin.mall.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.widgets.BannerImageLoader
import com.kotlin.goods.ui.activity.SearchGoodsActivity
import com.kotlin.mall.R
import com.kotlin.mall.common.*
import com.kotlin.mall.ui.activity.PhotoViewActivity
import com.kotlin.mall.ui.adapter.HomeDiscountAdapter
import com.kotlin.mall.ui.adapter.TopicAdapter
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*
import me.crosswall.lib.coverflow.CoverFlow
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


/**
 * 作者：何兆鸿 on 2018/4/23.
 * 学无止境~
 * 主页Fragment
 */
class HomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, null)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) { // View已经加载完成
        super.onViewCreated(view, savedInstanceState)
        initView()
        initBanner() // 在onCreateView中调用的话是来不及画完view的，所以直接操作viewId是会报错的（例如：mHomeBanner）
        initNews()
        initDiscount()
        initTopic()
    }

    /**
     *   初始化视图
     */
    private fun initView() {
        mSearchEt.onClick {
            startActivity<SearchGoodsActivity>()
        }

        mScanIv.onClick {
            toast(R.string.coming_soon_tip)
        }
    }

    // 初始化Banner轮播图
    private fun initBanner() {
        val listOf = listOf(HOME_BANNER_ONE, HOME_BANNER_TWO, HOME_BANNER_THREE, HOME_BANNER_FOUR);
        mHomeBanner.setImageLoader(BannerImageLoader())
        mHomeBanner.setImages(listOf)
        mHomeBanner.setBannerAnimation(Transformer.Accordion) // 设置viewpager的默认动画
        mHomeBanner.setDelayTime(2000) // 轮播间隔时间，默认2000
        mHomeBanner.setIndicatorGravity(BannerConfig.RIGHT)  // 设置指示器位置（当banner模式中有指示器时）
        mHomeBanner.start() // banner设置方法全部调用完毕时最后调用

        mHomeBanner.setOnBannerListener { position ->
            startActivity<PhotoViewActivity>(PhotoViewActivity.POS to position, PhotoViewActivity.PHOTO_LIST to listOf)
        }
    }

    // 初始化公告(textView上下轮播)
    private fun initNews() {
        // 公告
        mNewsFlipperView.setData(arrayOf("夏日炎炎，第一波福利还有30秒到达战场", "新用户立领1000元优惠券"))
    }

    // 初始化折扣
    private fun initDiscount(){
        val manager = LinearLayoutManager(activity)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        mHomeDiscountRv.layoutManager = manager

        val discountAdapter = HomeDiscountAdapter(activity)
        mHomeDiscountRv.adapter = discountAdapter
        discountAdapter.setData(mutableListOf(HOME_DISCOUNT_ONE,HOME_DISCOUNT_TWO,
                HOME_DISCOUNT_THREE,HOME_DISCOUNT_FOUR,HOME_DISCOUNT_FIVE)) // mutable是可变的意思
    }

    // 初始化主题
    private fun initTopic(){
        //话题
        mTopicPager.adapter = TopicAdapter(activity, listOf(HOME_TOPIC_ONE, HOME_TOPIC_TWO, HOME_TOPIC_THREE, HOME_TOPIC_FOUR, HOME_TOPIC_FIVE))
        mTopicPager.currentItem = 1
        mTopicPager.offscreenPageLimit = 5

        CoverFlow.Builder().with(mTopicPager).scale(0.3f).pagerMargin(-30.0f).spaceSize(0.0f).build()
    }
}
