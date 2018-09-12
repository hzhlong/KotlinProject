package com.kotlin.base.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import com.kotlin.base.R
import org.jetbrains.anko.find

/**
 * 作者：何兆鸿 on 2018/4/21.
 * 学无止境~
 * 进度加载组件
 */
class ProgressLoading private constructor (context: Context?, theme: Int) : Dialog(context, theme) {

    companion object {
        private lateinit var mDialog:ProgressLoading
        private var animDrawable:AnimationDrawable?= null // 滚动图片的动画

        fun create(context: Context?) : ProgressLoading{
            mDialog = ProgressLoading(context, R.style.LightProgressDialog)
            mDialog.setContentView(R.layout.progress_dialog)
            mDialog.setCancelable(true) // 是否可取消
            mDialog.setCanceledOnTouchOutside(false)// 不可直接点击屏幕取消
            mDialog.window.attributes.gravity = Gravity.CENTER // 居中

            val lp = mDialog.window.attributes
            lp.dimAmount = 0.2f // 设置灰暗程度

            mDialog.window.attributes = lp

            val loadingView = mDialog.find<ImageView>(R.id.iv_loading)
            animDrawable = loadingView.background as AnimationDrawable

            return mDialog
        }
    }

    // 显示dialog
    fun showLoading(){
        super.show()
        animDrawable?.start()
    }

    // 隐藏dialog
    fun hideLoading(){
        super.dismiss()
        animDrawable?.stop()
    }


}
