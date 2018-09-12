package com.kotlin.mall.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.GlideUtils
import com.kotlin.mall.R
import kotlinx.android.synthetic.main.layout_home_discount_item.view.*

/**
 * 作者：何兆鸿 on 2018/4/24.
 * 学无止境~
 * 主页折扣商品adapter
 */
class HomeDiscountAdapter(mContext: Context) : BaseRecyclerViewAdapter<String, HomeDiscountAdapter.ViewHolder>(mContext) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_home_discount_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        GlideUtils.loadUrlImage(mContext,dataList[position],holder.itemView.mGoodsIconIv)
        holder.itemView.mDiscountAfterTv.text = "￥123.00"
        holder.itemView.mDiscountBeforeTv.text = "$1000.00"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        init {
            view.mDiscountBeforeTv.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG // 添加下划线
            view.mDiscountBeforeTv.paint.isAntiAlias = true // 加锯齿
        }
    }

}



