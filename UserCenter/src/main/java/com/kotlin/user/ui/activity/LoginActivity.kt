package com.kotlin.user.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMVPActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.provider.PushProvider
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.router.RouterPath
import com.kotlin.user.R
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.LoginPresenter
import com.kotlin.user.presenter.view.LoginView
import com.kotlin.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/***
 * author 何兆鸿 to time 2018/4/22
 * 学无止境~
 * desc 注册界面
 */

// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = RouterPath.UserCenter.PATH_LOGIN) // Arouter路由框架的跨模块跳转需要注解一个 @Route 来标记跳转到哪个界面
class LoginActivity : BaseMVPActivity<LoginPresenter>(), LoginView, View.OnClickListener {

//    @Autowired(name = RouterPath.MessageCenter.PATH_MESSAGE_PUSH) // ARouter模块间接口调用实例化【阿里巴巴也有坑。@Autowired用不了】
    @JvmField
    var mPushProvider:PushProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()

    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mMobileEt.setText(AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE_FORVER))
        mLoginBtn.isEnabled = isBtnEnable()

        mLoginBtn.enable(mMobileEt, {isBtnEnable()})
        mLoginBtn.enable(mPwdEt, {isBtnEnable()})

        mLoginBtn.onClick(this)
        mForgetPwdTv.onClick(this)

        // kotlin在当前布局中可以直接引用组件id来使用（例如直接使用mHeaderBar），但是组件里面的组件则不能直接操作（例如直接使用mHeaderBar中的mRightTv）
        mHeaderBar.getRightView().onClick(this)

        // 使用依赖查找的方式发现服务，主动去发现服务并使用，下面两种方式分别是byName和byType
        //HelloService3 helloService3 = ARouter.getInstance().navigation<HelloService>(HelloService::class.java)
        //HelloService4 helloService4 = ARouter.getInstance().build("/service/hello").navigation() as HelloService
        mPushProvider = ARouter.getInstance().build(RouterPath.MessageCenter.PATH_MESSAGE_PUSH).navigation() as PushProvider? // 只能用这个方法
    }

    override fun injectComponent() {

        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)

        mPresenter.mView = this
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.mRightTv -> {
                startActivity<RegisterActivity>()
            }
            R.id.mLoginBtn -> {
//                toast("${11111}")
                mPresenter.login(mMobileEt.text.toString(), mPwdEt.text.toString(), mPushProvider?.getPushId()?:"")
            }
            R.id.mForgetPwdTv -> {
                startActivity<ForgetPwdActivity>()
            }
        }
    }

    // 判断登录按钮是否可点击
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not()
    }

    /**
     * 登录回调
     */
    override fun onLoginResult(result: UserInfo) {
        toast("登录成功")
        UserPrefsUtils.putUserInfo(result)
        Bus.send(UpdateCartSizeEvent()) // 更新购物车数量

        Handler().postDelayed({ // 延迟一秒是为了防止RxBus的消息发送来不及
            finish()
        },500)
    }

}
