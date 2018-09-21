package com.kotlin.user.ui.activity

import android.os.Bundle
import android.view.View
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.isMobile
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.passConfirm
import com.kotlin.base.ui.activity.BaseMVPActivity
import com.kotlin.user.R
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.RegisterPresenter
import com.kotlin.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

/***
 * author 何兆鸿 to time 2018/4/22
 * 学无止境~
 * desc 注册界面
 */
class RegisterActivity : BaseMVPActivity<RegisterPresenter>(), RegisterView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()

    }

    /**
     * 初始化视图
     */
    private fun initView() {

        mRegisterBtn.enable(mMobileEt, {isBtnEnable()})
        mRegisterBtn.enable(mVerifyCodeEt, {isBtnEnable()})
        mRegisterBtn.enable(mPwdEt, {isBtnEnable()})
        mRegisterBtn.enable(mPwdConfirmEt, {isBtnEnable()})

        mRegisterBtn.onClick(this)
        mVerifyCodeBtn.onClick(this)
    }

    override fun injectComponent() {

        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)

        mPresenter.mView = this
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.mVerifyCodeBtn -> {
                if(mMobileEt.isMobile()){
                    mVerifyCodeBtn.requestSendVerifyNumber()
                    toast("发送验证成功")
                }
            }
            R.id.mRegisterBtn -> {
                if(verifyInput()){
                    mPresenter.register(mMobileEt.text.toString(), mPwdEt.text.toString(), mVerifyCodeEt.text.toString())
                }
            }
        }
    }

    // 判断注册按钮是否可点击
    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() &&
                mVerifyCodeEt.text.isNullOrEmpty().not() &&
                mPwdEt.text.isNullOrEmpty().not() &&
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }

    // 输入内容验证
    private fun verifyInput() : Boolean{
        if(!mMobileEt.isMobile()) {
            return false
        }else if(!mPwdEt.passConfirm(mPwdConfirmEt)){
            return false
        }
        return true
    }


    /**
     * 注册回调
     */
    override fun onRegisterResult(result: String) {
        toast(result)
        finish()
    }

}
