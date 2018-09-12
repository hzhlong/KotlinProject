package com.kotlin.mall.widgets.share

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import com.kotlin.base.common.BaseApplication.Companion.context
import com.kotlin.mall.R
import kotlinx.android.synthetic.main.dialog_share_layout.view.*
import org.jetbrains.anko.toast

/**
 * 作者：何兆鸿 on 2018/4/23.
 * 学无止境~
 * desc:分享弹窗
 */
class ShareDialog (context:Context, private var isShowDownload: Boolean = false, private var mListener: PlatformActionListener?) : PopupWindow(context), View.OnClickListener {

    //根视图
    private val mRootView: View

    /**
     * share 内容
     */
    var mShareType: Int = 0 //指定分享类型
    var mShareTitle: String? = null //指定分享内容标题
    var mShareText: String? = null //指定分享内容文本
    var mSharePhoto: String? = null //指定分享本地图片
    var mShareTileUrl: String? = null
    var mShareSiteUrl: String? = null
    var mShareSite: String? = null
    var mUrl: String? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mRootView = inflater.inflate(R.layout.dialog_share_layout, null)

        initView()

        // 设置SelectPicPopupWindow的View
        this.contentView = mRootView
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.MATCH_PARENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.isFocusable = true
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.animationStyle = R.style.AnimBottom
        background.alpha = 150
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mRootView.setOnTouchListener { _, event ->
            val height = mRootView.findViewById<HorizontalScrollView>(R.id.mPopView).top
            val y = event.y.toInt()
            if (event.action == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss()
                }
            }
            true
        }
    }

    /**
     * 初始化view
     */
    private fun initView() {

        mRootView.mWeixinLayout.setOnClickListener(this)
        mRootView.mIvWeixinMomentLayout.setOnClickListener(this)
        mRootView.mQQLayout.setOnClickListener(this)
        mRootView.mQzoneLayout.setOnClickListener(this)
        mRootView.mDownLoadLayout.setOnClickListener(this)

        if (isShowDownload) {
            mRootView.mDownLoadLayout.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvWeixin -> shareData(ShareManager.PlatformType.WeChat)
            R.id.mIvWeixinMomentLayout -> shareData(ShareManager.PlatformType.WechatMoments)
            R.id.mQQLayout -> shareData(ShareManager.PlatformType.QQ)
            R.id.mQzoneLayout -> shareData(ShareManager.PlatformType.QZone)
            R.id.mDownLoadLayout -> context.toast("在图片预览的界面中用户可能想在这里提供保存图片的按钮，不碍事~") // 下载
        }
    }

    /**
     * 分享
     */
    private fun shareData(platform: ShareManager.PlatformType) {
        var mData = ShareData()
        var params: Platform.ShareParams = Platform.ShareParams()
        params.shareType = mShareType
        params.title = mShareTitle
        params.titleUrl = mShareTileUrl
        params.site = mShareSite
        params.siteUrl = mShareSiteUrl
        params.text = mShareText
        params.imagePath = mSharePhoto
        params.url = mUrl
        mData.mPlatformType = platform
        mData.mShareParams = params
        ShareManager.shareData(mData,mListener)
    }


}