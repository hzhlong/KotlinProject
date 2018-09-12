package com.kotlin.user.service

import io.reactivex.Observable

/**
 * create by 何兆鸿 as 2018/4/17
 * 上传相关功能接口
 */
interface UploadService {
    fun getUploadToken(): Observable<String>
}