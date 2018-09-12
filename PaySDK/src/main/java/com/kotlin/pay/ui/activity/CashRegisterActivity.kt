package com.kotlin.pay.ui.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.eightbitlab.rxbus.Bus
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMVPActivity
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.order.injection.component.DaggerPayComponent
import com.kotlin.order.injection.moudle.PayModule
import com.kotlin.pay.R
import com.kotlin.pay.R.id.*
import com.kotlin.pay.presenter.PayPresenter
import com.kotlin.pay.presenter.view.PayView
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.event.UpdatOrderListEvent
import com.kotlin.provider.router.RouterPath
import kotlinx.android.synthetic.main.activity_cash_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * 作者：何兆鸿 on 2018/5/19.
 * 学无止境~
 * 收银台界面
 */
@Route(path = RouterPath.PaySDK.PATH_PAY)
class CashRegisterActivity : BaseMVPActivity<PayPresenter>(), PayView , View.OnClickListener{

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)   // Arouter通过name来映射URL中的不同参数
    @JvmField  // 要是映射不成功就clean Project一下，不过每次映射不成功就clean很耗时间，先不用这个来映射，直接用intent获取得了哈
    var mOrderId: Int = 0

    @Autowired(name = ProviderConstant.KEY_ORDER_PRICE)   // Arouter通过name来映射URL中的不同参数
    @JvmField  // 要是映射不成功就clean Project一下，不过每次映射不成功就clean很耗时间，先不用这个来映射，直接用intent获取得了哈
    var mTotalPrice: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX) // 因为是本地服务器，所以这次用支付宝的沙箱环境

        initView()
        initData()
    }

    /**
     * 初始化视图和事件
     */
    private fun initView() {
        updatePayType(true, false, false)

        mAlipayTypeTv.onClick (this)
        mWeixinTypeTv.onClick (this)
        mBankCardTypeTv.onClick (this)

        mPayBtn.onClick (this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.mPayBtn -> {
                mPresenter.getPaySign(mOrderId, mTotalPrice)
            }
            R.id.mAlipayTypeTv -> { updatePayType(true, false, false) }
            R.id.mWeixinTypeTv -> { updatePayType(false, true, false) }
            R.id.mBankCardTypeTv -> { updatePayType(false, false, true) }
        }
    }

    // 根据参数设置默认选中
    private fun updatePayType(isApliPay: Boolean = true, isWeixinPay: Boolean, isBankCardPay: Boolean) {
        mAlipayTypeTv.isSelected = isApliPay
        mWeixinTypeTv.isSelected = isWeixinPay
        mBankCardTypeTv.isSelected = isBankCardPay
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID, -1) // ARouter库的@Autowired映射经常抽风，获取不了值。直接用intent获取
        mTotalPrice = intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE, -1)

        mTotalPriceTv.text = YuanFenConverter.changeF2YWithUnit(mTotalPrice)
    }

    override fun injectComponent() {
        DaggerPayComponent.builder().activityComponent(mActivityComponent).payModule(PayModule()).build().inject(this)
        mPresenter.mView = this
    }

    /**
     *  获取支付签名回调
     */
    override fun onGetSignResult(result: String) {
        doAsync {
            // kotlin的anko库的一个扩展,支付宝或微信请求支付通常在异步线程中执行
            val resultMap:Map<String,String> = PayTask(this@CashRegisterActivity).payV2(result,true)

            uiThread { // kotlin的anko库的一个扩展,mainUIMainThread：主线程
                if (resultMap["resultStatus"].equals("9000")) {
                    mPresenter.payOrder(mOrderId)
                } else {
                    toast("支付失败${resultMap["memo"]}") // 如果显示ALI3171：原因是订单号重复了，请确保订单号保持唯一。
                }
            }
        }
    }

    override fun onPayOrderResult(result: Boolean) {
        toast("支付成功")

        Bus.send(UpdatOrderListEvent(2)) // 支付成功后，待收货界面要刷新
        Handler().postDelayed({
            finish()
        }, 300)
    }

}