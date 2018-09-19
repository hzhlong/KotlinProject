package com.kotlin.base.service.inter

import java.io.File

/**
 * 作者：何兆鸿 on 2018/9/19.
 * 学无止境~
 * 图片下载回调
 */
interface ImageDownLoadCallBack {

    fun onDownLoadSuccess(file: File)

    fun onDownLoadFailed()
}