package com.kotlin.user.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.base.ext.convertBoolean
import com.kotlin.user.data.repository.UploadRepository
import com.kotlin.user.service.UploadService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/17
 * 上传相关功能接口实现类
 */
class UploadServiceImpl @Inject constructor(): UploadService {

    @Inject
    lateinit var repository: UploadRepository

    // 上传图片
    override fun getUploadToken(): Observable<String> {
       return repository.getUploadToken().convert()
    }
}