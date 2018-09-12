package com.kotlin.base.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.R
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.ext.applyPermit
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.base.injection.component.DaggerActivityComponent
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.presenter.view.BaseView
import com.kotlin.base.widgets.ProgressLoading
import com.kotlin.utilslibrary.widgets.alertView.AlertView
import com.kotlin.utilslibrary.widgets.alertView.OnItemClickListener
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import org.jetbrains.anko.toast
import javax.inject.Inject


/**
 * create by 何兆鸿 as 2018/4/17
 * 存在选择图片的Activity基础封装
 */
abstract class BaseTakePhotoActivity<T: BasePresenter<*>> : BaseActivity(), BaseView {

    private lateinit var mTempList: List<LocalMedia> // 图片集合

    @Inject
    lateinit var mPresenter:T

    lateinit var mActivityComponent: ActivityComponent

    private lateinit var mLoadingDialog: ProgressLoading // 进度加载框

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()

        mTempList = arrayListOf()

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
     *   @param selectionMode 选择模式：0-单选 1多选
     *   @param maxSelectNum 可选的图片数：默认9张
     */
    protected fun showAlertView(selectionMode:Int,maxSelectNum:Int = 9) {
        AlertView("选择图片", null, "取消", null, arrayOf("拍照", "相册"), this,
                AlertView.Style.ActionSheet, object : OnItemClickListener {
            override fun onItemClick(o: Any?, position: Int) {

                applyPermit(arrayOf(Manifest.permission.CAMERA),{
                    when (position) {
                        0 -> {
                            takePicture(1, selectionMode = selectionMode, maxSelectNum = maxSelectNum, mediaList = mTempList)
                        }
                        1 -> takePicture(0, selectionMode = selectionMode, maxSelectNum = maxSelectNum, mediaList = mTempList)
                    }
                })
            }
        }).show()
    }

    /**
     * PictureSelector默认实现
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    takeSuccess(selectList)
                    Log.d("TakePhoto", selectList!![0].path)
                    Log.d("TakePhoto", selectList!![0].compressPath)
                }
            }
        }
    }

    /**
     * 获取图片，成功回调
     */
    abstract fun takeSuccess(result: MutableList<LocalMedia>?)

    /**根据类型进去拍照还是选择图片
     * @param type 0-进入相册  1-拍照
     * @param minSelectNum   最小选择数量
     * @param maxSelectNum   最大图片选择数量
     * @param selectionMode  多选 or 单选
     * @param mediaList      保存的图片集合*/
    fun takePicture(type:Int = 0, minSelectNum:Int = 0, maxSelectNum:Int = 9, selectionMode:Int = PictureConfig.MULTIPLE, mediaList:List<LocalMedia>){
        // 进入相册 以下是例子：不需要的api可以不写
        val picSelector = PictureSelector.create(this)

        // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
        if(type == 0){
            picSelector.openGallery(PictureMimeType.ofImage()) // 启动相册并拍照
        }else{
            picSelector.openCamera(PictureMimeType.ofImage())  // 单独启动拍照或视频 根据PictureMimeType自动识别
        }
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(minSelectNum)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(selectionMode)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath(PictureConfig.getCacheDir(Utils.getPackNmae(RegisterInfoInputActivity.this), Configs.Consumer_Dir))// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(false)//同步true或异步false 压缩 默认同步
                //.compressSavePath(PictureConfig.getCacheDir(Utils.getPackNmae(RegisterInfoInputActivity.this), Configs.Consumer_Dir))//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(mediaList)// 是否传入已选图片
                //.videoMaxSecond(15)
                //.videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
    }
}