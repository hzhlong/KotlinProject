package com.kotlin.provider.router

/**
 * 作者：何兆鸿 on 2018/4/28.
 * 学无止境~
 * 用来保存Arouter路由框架 跨模块跳转的路径
 * 注意：跨模块跳转的路径是绝对不能重复的，否则会跳转失败【框架自身规则】
 */

object RouterPath {

    // 用户模块
    class UserCenter {
       companion object {
           const val PATH_LOGIN = "/userCenter/login"
       }
    }

    // 订单模块
    class OrderCenter {
       companion object {
           const val PATH_ORDER_CONFIRM = "/orderCenter/confirm"
       }
    }

    // 支付模块
    class PaySDK {
       companion object {
           const val PATH_PAY = "/paySDK/pay"
       }
    }

    // 消息模块
    class MessageCenter {
       companion object {
           const val PATH_MESSAGE_PUSH = "/messageCenter1/push"
           const val PATH_MESSAGE_ORDER = "/messageCenter2/order"
       }
    }
}
