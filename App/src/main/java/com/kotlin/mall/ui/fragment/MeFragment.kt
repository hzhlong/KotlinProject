package com.kotlin.mall.ui.fragment

import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import com.kotlin.base.ext.loadUrl
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.mall.R
import com.kotlin.mall.ui.activity.MainActivity
import com.kotlin.mall.ui.activity.SettingActivity
import com.kotlin.mall.widgets.share.ShareDialog
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.common.OrderStatus
import com.kotlin.order.ui.activity.OrderActivity
import com.kotlin.order.ui.activity.ShipAddressActivity
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.common.isLogined
import com.kotlin.user.ui.activity.UserInfoActivity
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.util.*


/**
 * 作者：何兆鸿 on 2018/4/23.
 * 学无止境~
 * 我的Fragment
 */
class MeFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_me, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) { // View已经加载完成
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)

        mWaitPayOrderTv.onClick(this)
        mWaitConfirmOrderTv.onClick(this)
        mCompleteOrderTv.onClick(this)
        mAllOrderTv.onClick(this)
        mAddressTv.onClick(this)
        mShareTv.onClick(this)
        mSettingTv.onClick(this)

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /**
     * 加载初始数据
     */
    private fun loadData(){

        if (isLogined()){
            // 已登录
            val userIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
            if(userIcon.isNotEmpty()){
                mUserIconIv.loadUrl(userIcon)
            }
            mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        }else{
            // 未登录
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = getString(R.string.un_login_text)
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.mUserIconIv,R.id.mUserNameTv -> {
                afterLogin {
                    startActivity<UserInfoActivity>()
                }
            }
            R.id.mWaitPayOrderTv -> {
                startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
            }
            R.id.mWaitConfirmOrderTv -> {
                startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)
            }
            R.id.mCompleteOrderTv -> {
                startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
            }
            R.id.mAllOrderTv -> {
                afterLogin {
                    startActivity<OrderActivity>()
                }
            }
            R.id.mAddressTv -> {
                afterLogin {
                    startActivity<ShipAddressActivity>()
                }
            }
            R.id.mShareTv -> {
                toast(R.string.coming_soon_tip)
                shareKtolin() // 我没去腾讯开发平台和微信开发平台创建应用，所以暂时是无法分享的
            }
            R.id.mSettingTv -> {
                startActivity<SettingActivity>()
            }
        }
    }

    /**
     * 分享kotlin给大家
     */
    private fun shareKtolin() {
        val dialog = ShareDialog(activity, true, object : PlatformActionListener {
            override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {

            }

            override fun onCancel(p0: Platform?, p1: Int) {
                toast("取消分享")
            }

            override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
                toast("分享失败："+ p2)
            }
        })
        dialog.mShareType = Platform.SHARE_IMAGE
        dialog.mShareTitle = "Kotlin"
        dialog.mShareSiteUrl = "https://github.com/hzhlong/KotlinProject"
        dialog.mShareText = "Kotlin"
        dialog.mShareSite = "hzh"
        dialog.mShareSiteUrl = "https://github.com/hzhlong/KotlinProject"
        dialog.mSharePhoto = Environment.getExternalStorageDirectory().absolutePath + "/test2.jpg"
        dialog.showAtLocation((activity as MainActivity).contentView,Gravity.BOTTOM and Gravity.CENTER_HORIZONTAL,0,0)
    }

}