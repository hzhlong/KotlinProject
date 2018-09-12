package com.kotlin.mall.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.mall.R
import com.kotlin.mall.ui.adapter.PhotoPagerAdapter
import kotlinx.android.synthetic.main.activity_photo_view.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.toast


/**
 * 作者：何兆鸿 on 2018/6/11.
 * 学无止境~
 * desc：显示产品大图页面
 */

class PhotoViewActivity : BaseActivity() {

    companion object {
        const val POS = "pos" // 当前位置
        const val PHOTO_LIST = "photo_list" // 数据集合
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setNeedLoadStatusBar(false) // 子类是否需要实现沉浸式,默认需要
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view)

        initView()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        var currentPos = intent.getIntExtra(POS, 0)
        val list = intent.getStringArrayListExtra(PHOTO_LIST)

        mIndictorView.text = "1/" + list.size

        mPager.pageMargin = dip(30.0f)
        mPager.adapter = PhotoPagerAdapter(this, list, false)
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                var pos = position % list.size
                mIndictorView.text = (pos + 1).toString() + "/" + list.size
                currentPos = pos
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        mPager.currentItem = currentPos + 1000 * list.size

        mShareView.onClick {
            toast("分享") // 分享
        }
    }
}
