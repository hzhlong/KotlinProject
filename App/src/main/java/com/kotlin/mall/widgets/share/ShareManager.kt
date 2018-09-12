package com.kotlin.mall.widgets.share

import android.content.Context

import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.tencent.qzone.QZone
import cn.sharesdk.wechat.friends.Wechat
import cn.sharesdk.wechat.moments.WechatMoments

/**
 * @author 何兆鸿
 * @function 分享功能统一入口，负责发送数据到指定平台,可以优化为一个单例模式
 */

object ShareManager {

    /**
     * 第一个执行的方法,最好在程序入口入执行
     *
     * @param context
     */
    fun initSDK(context: Context) {
        ShareSDK.initSDK(context)
    }

    /**
     * 要分享到的平台
     */
    private var mCurrentPlatform: Platform? = null

    /**
     * 分享数据到不同平台
     */
    fun shareData(shareData: ShareData, listener: PlatformActionListener?) {
        when (shareData.mPlatformType) {
            ShareManager.PlatformType.QQ -> mCurrentPlatform = ShareSDK.getPlatform(QQ.NAME)
            ShareManager.PlatformType.QZone -> mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME)
            ShareManager.PlatformType.WeChat -> mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME)
            ShareManager.PlatformType.WechatMoments -> mCurrentPlatform = ShareSDK.getPlatform(WechatMoments.NAME)
        }
        listener?.let {
            mCurrentPlatform!!.platformActionListener = listener //由应用层去处理回调,分享平台不关心。
        }
        mCurrentPlatform!!.share(shareData.mShareParams)
    }

    /**
     * 第三方用户授权信息获取
     * 将获取到的用户信息发送到服务器
     * 是
     * 登陆流程：通过Aouth认证拿到第三方平台用户信息--------------------------------> 服务器判断是否绑定过本地帐号------->  返回本地系统帐号信息登陆应用
     *
     * 否
     * ------->  直接用第三方信息登陆应用(应用内可以提供绑定功能)
     * 注意：1.第三方帐号与本地帐号的绑定关系是一一对应的(即一个QQ号绑定了本地帐号以后,同一用户的微信号也不能再绑定)。
     * 2.绑定的过程其实就是一个使用第三方平台用户信息再自己服务器的一个再注册功能。
     *
     * @param type     第三方类型
     * @param listener 事件回调处理
     */
    fun getUserMsg(type: PlatformType, listener: PlatformActionListener) {
        when (type) {
            ShareManager.PlatformType.QQ, ShareManager.PlatformType.QZone ->
                /**
                 * 没有微信那么严格，测试环境下也可以正常登陆
                 */
                mCurrentPlatform = ShareSDK.getPlatform(QZone.NAME)
            ShareManager.PlatformType.WeChat ->
                /**
                 * 微信第三方登陆条件：1.应用必须通过微信平台审核 2.开通了登陆功能(300一年) 3.测试的时候需要以签名包测试
                 * 必须同时满足以上三个条件才能登陆成功。
                 */
                mCurrentPlatform = ShareSDK.getPlatform(Wechat.NAME)
        }
        mCurrentPlatform!!.platformActionListener = listener //用户信息回调接口
        mCurrentPlatform!!.showUser(null) // 请求对应用户信息
    }

    /**
     * 删除授权
     */
    fun removeAccount() {
        if (mCurrentPlatform != null) {
            mCurrentPlatform!!.db.removeAccount()
        }
    }

    /**
     * @author 应用程序需要的平台
     */
    enum class PlatformType {
        QQ, QZone, WeChat, WechatMoments
    }

}