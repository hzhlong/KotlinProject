package com.kotlin.base.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.ext.applyPermit
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.widgets.ProgressLoading
import com.kotlin.utilslibrary.widgets.alertView.AlertView
import com.kotlin.utilslibrary.widgets.alertView.OnItemClickListener
import org.jetbrains.anko.toast
import java.io.File
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 存在选择图片的Activity基础封装
 */
abstract class BaseTakePhotoActivity2<T: BasePresenter<*>> : BaseActivity(), BaseView, TakePhoto.TakeResultListener {

    private lateinit var mTakePhoto: TakePhoto // 图片选择器

    private lateinit var mTempFile: File // 临时文件

    @Inject
    lateinit var mPresenter:T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading // 进度加载框

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()

        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)

        mLoadingDialog = ProgressLoading.create(this)
        ARouter.getInstance().inject(this) // 如果使用到解析URL中的参数【@Autowired】时需要配置这个
    }

    // Dagger注册 初始化Componment注解，用于子类实现
    abstract fun injectComponent()

    // 初始化Activity通用化注解【Activity Component】
    private fun initActivityInjection() {

        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .activityModule(ActivityModule(this)).build()
    }

    // 显示加载
    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    // 隐藏加载
    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    // 错误回调
    override fun onError(text: String) {
        toast(text)
    }

    /**
     *   弹出选择框，默认实现
     *   可根据实际情况，自行修改
     *   @param selectionMode 选择模式：0-单选 1多选 默认多选
     *   @param maxSelectNum 可选的图片数：默认9张
     */
    protected fun showAlertView(selectionMode:Int = 1, maxSelectNum:Int = 9) {
        AlertView("选择图片", null, "取消", null, arrayOf("拍照", "相册"), this,
                AlertView.Style.ActionSheet, object : OnItemClickListener {
            override fun onItemClick(o: Any?, position: Int) {

                applyPermit(arrayOf(Manifest.permission.CAMERA),{
                    mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false) // 图片压缩

                    when (position) {
                        0 -> {
                            createTempFile() //从相机获取图片(不裁剪) @param outPutUri 图片保存的路径
                            mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))

                        }
                        1 -> {
                            if(selectionMode == 0){ // 单选
                                mTakePhoto.onPickFromDocuments()  // 从文件中获取图片
                            }else{
                                mTakePhoto.onPickMultiple(maxSelectNum)  // 从文件中获取图片 @param limit 最多选择图片张数的限制
                            }
                        }
                    }
                })
            }
        }).show()
    }

    /**
     * 获取图片，成功回调
     */
    override fun takeSuccess(result: TResult?) {
        Log.d("TakePhoto", result?.image?.originalPath)
        Log.d("TakePhoto", result?.image?.compressPath)
    }

    /**
     * 获取图片，取消回调
     */
    override fun takeCancel() {

    }

    /**
     * 获取图片，失败回调
     */
    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("takePhoto", msg)
    }

    /**
     * 创建临时文件
     */
    fun createTempFile(){
        val tempFile = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()){
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFile)
            return
        }

        this.mTempFile = File(filesDir, tempFile)
    }

    /**
     * TakePhoto默认实现
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

}