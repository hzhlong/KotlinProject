package com.kotlin.base.service

import android.content.Context
import com.kotlin.base.service.inter.ImageDownLoadCallBack
import com.kotlin.base.utils.GlideUtils
import java.io.File

/**
 * 作者：何兆鸿 on 2018/9/19.
 * 学无止境~
 * 图片下载Service
 */
class DownLoadImageService(context: Context, url: String, callBacksss: ImageDownLoadCallBack) : Runnable {

    private var c: Context? = null
    private var url: String? = null
    private var callBack: ImageDownLoadCallBack? = null

    init {
        this.c = context
        this.url = url
        this.callBack = callBacksss
    }

    override fun run() {
        var file: File? = null
        try {
            file = GlideUtils.downLoadImage(context = c!!, url = url)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (file != null) {
                callBack?.onDownLoadSuccess(file)
            }else{
                callBack?.onDownLoadFailed()
            }
        }
    }

}