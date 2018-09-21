package com.kotlin.base.ext

import android.app.Activity
import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.kennyc.view.MultiStateView
import com.kotlin.base.R
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.BaseFun
import com.kotlin.base.rx.BaseFunBoolean
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.service.DownLoadImageService
import com.kotlin.base.service.inter.ImageDownLoadCallBack
import com.kotlin.base.utils.GlideUtils
import com.kotlin.base.utils.RegexUtils
import com.kotlin.base.widgets.DefaultTextWatcher
import com.kotlin.utilslibrary.widgets.alertView.AlertView
import com.kotlin.utilslibrary.widgets.alertView.OnItemClickListener
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by 何兆鸿 on 2018/4/17.
 * 扩展类，通用结构的可以写在这里
 */


/**
 * 扩展Observable执行（rxjava1异步请求时的Observable每次都要写这个步骤）
 */
fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>, lifecycleProvider: LifecycleProvider<*>){
    this.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribe(subscriber)
}


/***
 * 扩展数据转换（在*Impl实现类中需要把数据映射回去，如果是T泛型类型就使用这个方法）
 */
fun <T> Observable<BaseResp<T>>.convert(): Observable<T>{
    return this.flatMap(BaseFun())
}

/***
 * 扩展数据转换（在*Impl实现类中需要把数据映射回去，如果是Boolean类型就使用这个方法）
 */
fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean>{
    return this.flatMap(BaseFunBoolean())
}


/**
 *   多状态视图开始加载
 */
fun MultiStateView.startLoading(){
    viewState = MultiStateView.VIEW_STATE_LOADING
    val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
    val animBackground = loadingView!!.find<View>(R.id.loading_anim_view).background
    (animBackground as AnimationDrawable).start()
}

/**
 * 申请权限,不愧是鸿，擅长举一反三！厉害！加油！
 */
fun Activity.applyPermit(permiss: Array<String>, method: () -> Unit){

    RxPermissions(this)
            .request(*permiss)//多个权限用","隔开
            .subscribe({ aBoolean ->
                this.permitCallBack(aBoolean,method)
            })
}

/**
 * 权限回调
 */
fun Activity.permitCallBack(result: Boolean,method: () -> Unit) {
    if(result){
        method()
    }else{
        toast("请先申请权限！")
    }
}


/**
 * 显示选择图片弹出框
 */
fun Activity.showPhotoView(array : Array<String>, clickListener : OnItemClickListener){
    AlertView("选择图片", null, "取消", null, array, this,
            AlertView.Style.ActionSheet, clickListener).show()
}



// ----------------View类型
/**
 * View点击事件扩展1(测试Kotlin用的)
 */
fun View.onClick(listener:View.OnClickListener){
    this.setOnClickListener(listener)
}

/**
 * View点击事件扩展2(函数可以当成参数来传递的)
 */
fun View.onClick(method:() ->Unit){
    this.setOnClickListener { method() }
}

/**
 *  扩展视图可见性
 */
fun View.setVisible(visible:Boolean){
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
// ----------------View类型



// ----------------Button类型
/**
 * 扩展Button可用性（按钮是否可用）
 */
fun Button.enable(et:EditText, method: () -> Boolean){
    val btn = this

    et.addTextChangedListener(object : DefaultTextWatcher(){
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}
// ----------------Button类型



// ----------------ImageView类型
/**
 *  ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}

/***
 *  ImageView下载网络图片,执行单线程列队执行
 */
private val singleExecutor: ExecutorService by lazy { Executors.newSingleThreadExecutor() }
fun ImageView.onDownLoad(url: String, callBack: ImageDownLoadCallBack) {
    singleExecutor.submit(DownLoadImageService(context,url, callBack))
}
// ----------------ImageView类型



// ----------------EditText类型
/**
 * 判断编辑文本内容是否手机格式
 */
fun EditText.isMobile(errorMsg : String = "输入的手机格式不正确") : Boolean{
    var isMobile = RegexUtils.checkMobile(this.text.toString())
    if(!isMobile){
        BaseApplication.context.toast(errorMsg)
    }
    return isMobile
}

/**
 * 判断编辑文本内容是否一致
 */
fun EditText.passConfirm(etPass:EditText , errorMsg : String = "输入的密码不一致") : Boolean{
    var isSame = etPass.text.toString() == this.text.toString()
    if(!isSame){
        BaseApplication.context.toast(errorMsg)
    }
    return isSame
}
// ----------------EditText类型






