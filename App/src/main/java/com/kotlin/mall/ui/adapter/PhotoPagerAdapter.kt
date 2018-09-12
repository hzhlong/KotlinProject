package com.kotlin.mall.ui.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import com.github.chrisbanes.photoview.PhotoView
import com.kotlin.base.ext.loadUrl


/**
 * 作者：何兆鸿 on 2018/4/23.
 * 学无止境~
 * desc 显示产品大图页面Adapter
 */
class PhotoPagerAdapter(private val mContext: Context, private val list: List<String>, private val mIsMatch: Boolean) : PagerAdapter() {

    override fun getCount(): Int {
        //设置成最大，使无限循环
        return Int.MAX_VALUE
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val photoView: ImageView
        if (mIsMatch) {
            photoView = ImageView(mContext)
            photoView.scaleType = ScaleType.FIT_XY
            photoView.setOnClickListener {
                /*Intent intent = new Intent(mContext, CourseDetailActivity.class);
                    mContext.startActivity(intent);*/
            }
        } else {
            photoView = PhotoView(mContext)
        }
        photoView.loadUrl(list[position % list.size])
        container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        return photoView
    }

    override fun destroyItem(parent: ViewGroup, paramInt: Int, paramObject: Any) {
        parent.removeView(paramObject as View)
    }

    override fun isViewFromObject(paramView: View, paramObject: Any): Boolean {
        return paramView === paramObject
    }
}
