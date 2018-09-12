package com.kotlin.pay.presenter.view

import com.kotlin.base.presenter.view.BaseView

/**
 * 作者：何兆鸿 on 2018/5/21.
 * 学无止境~
 * 支付 视图回调
 */
interface PayView : BaseView{

    //获取支付签名
    fun onGetSignResult(result: String)
    //同步支付成功状态
    fun onPayOrderResult(result: Boolean)
}