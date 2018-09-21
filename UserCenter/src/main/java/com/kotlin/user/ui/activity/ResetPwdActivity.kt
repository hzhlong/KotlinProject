package com.kotlin.user.ui.activity

import android.os.Bundle
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.passConfirm
import com.kotlin.base.ui.activity.BaseMVPActivity
import com.kotlin.user.R
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.ResetPwdPresenter
import com.kotlin.user.presenter.view.ResetView
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast

/***
 * author 何兆鸿 to time 2018/4/22
 * 学无止境~
 * desc 重置密码界面
 */
class ResetPwdActivity : BaseMVPActivity<ResetPwdPresenter>(), ResetView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)

        initView()

    }

    /**
     * 初始化视图
     */
    private fun initView() {

        mConfirmBtn.enable(mPwdEt, {isBtnEnable()})
        mConfirmBtn.enable(mPwdConfirmEt, {isBtnEnable()})

        mConfirmBtn.onClick{
            if (!mPwdEt.passConfirm(mPwdConfirmEt)){
                toast("密码不一致")
                return@onClick
            }
            mPresenter.resetPwd(intent.getStringExtra("mobile"), mPwdConfirmEt.text.toString())
        }
    }

    override fun injectComponent() {

        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)

        mPresenter.mView = this
    }

    // 判断重置按钮是否可点击
    private fun isBtnEnable(): Boolean {
        return mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }


    /**
     * 重置密码回调
     */
    override fun onResetResult(result: String) {
        toast(result)
        startActivity(intentFor<LoginActivity>().singleTop().clearTop())
    }
}
