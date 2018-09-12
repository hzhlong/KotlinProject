package com.kotlin.mall.ui.activity

import android.Manifest
import android.os.Bundle
import android.os.Handler
import com.eightbitlab.rxbus.Bus
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ui.activity.BaseMVPActivity
import com.kotlin.base.utils.AppVersionUtils
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.goods.presenter.NullPresenter
import com.kotlin.mall.R
import com.kotlin.mall.data.protocol.UpdateInfo
import com.kotlin.mall.data.protocol.UpdateModelReq
import com.kotlin.provider.common.isLogined
import com.kotlin.user.utils.UserPrefsUtils
import com.kotlin.utilslibrary.widgets.alertView.AlertView
import com.kotlin.utilslibrary.widgets.alertView.OnItemClickListener
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.toast
import android.content.Intent
import com.kotlin.base.ext.applyPermit
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.mall.services.update.UpdateService


/***
 * author 何兆鸿 to time 2018/4/22
 * 学无止境~
 * desc 设置界面
 */
class SettingActivity : BaseMVPActivity<NullPresenter>()  {

    override fun injectComponent() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        mUserProtocolTv.onClick {
            toast("用户协议")
        }
        mFeedBackTv.onClick {
            toast("反馈意见")
        }
        mAboutTv.onClick {
            toast("关于")
        }
        mCheckVersionTv.onClick {
            applyPermit(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),{
                checkVersion()
            })
        }

        mLogoutBtn.setVisible(isLogined())

        //退出登录，清空本地用户数据
        mLogoutBtn.onClick {
            UserPrefsUtils.putUserInfo(null)

            showLoading()
            Bus.send(UpdateCartSizeEvent()) // 更新购物车数量
            Handler().postDelayed({ // 延迟一秒是为了防止RxBus的消息发送来不及
                hideLoading()
                finish()
            },1000)
        }
    }

    // 发送版本检查更新请求
    private fun checkVersion() {
        // 由于后台没有写这个接口，所以只写逻辑流程

        //--假设已经请求了数据
        var updateModel = UpdateModelReq(0,"", UpdateInfo(2))
        if(AppVersionUtils.getVersionCode(this) < updateModel.data.currentVersion){
            // 说明有新版本,开始下载
            AlertView(getString(R.string.update_title)  ,null,  "取消", null, arrayOf("确定"), this, AlertView.Style.Alert, OnItemClickListener { _: Any, position: Int ->
                if(position == 0){
                    // 安装时间回调处理，就是启动我们的更新服务。
                    val intent = Intent(this, UpdateService::class.java)
                    startService(intent)
                }
            }).show()

        } else {
            //弹出一个toast提示当前已经是最新版本等处理
            toast(getString(R.string.no_new_version_msg))
        }
        //--假设已经请求了数据
    }
}