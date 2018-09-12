package com.kotlin.message.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.event.MessageBadgeEvent
import com.kotlin.provider.router.RouterPath
import org.json.JSONObject


/**
 * 作者：何兆鸿 on 2018/5/23.
 * 学无止境~
 * 自定义Push 接收器
 */
class MessageReceiver : BroadcastReceiver() {

    private val TAG = "MessageReceiverReceiver"

    private var nm: NotificationManager? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (null == nm) {
            nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        val bundle = intent.extras
        Log.d(TAG, "onReceive - " + intent.action + ", extras: " + bundle)

        when {
            JPushInterface.ACTION_REGISTRATION_ID == intent.action -> Log.d(TAG, "JPush用户注册成功")

            JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action -> {
                Log.d(TAG, "接受到推送下来的自定义消息")
                Toast.makeText(context,"推送下来的自定义消息："+bundle.getString(JPushInterface.EXTRA_MESSAGE),Toast.LENGTH_SHORT).show()
                Bus.send(MessageBadgeEvent(true))

            }

            JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action -> Log.d(TAG, "接受到推送下来的通知")

            JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action -> {
                Log.d(TAG, "用户点击打开了通知")

                val extra = bundle.getString(JPushInterface.EXTRA_EXTRA) // 在后台发送推送信息时添加的一些自定义数据
                val json = JSONObject(extra)
                val orderId = json.getInt("orderId")
                ARouter.getInstance().build(RouterPath.MessageCenter.PATH_MESSAGE_ORDER)
                        .withInt(ProviderConstant.KEY_ORDER_ID, orderId)
                        .navigation()

            }
            else -> Log.d(TAG, "Unhandled intent - " + intent.action!!)
        }
    }
}
