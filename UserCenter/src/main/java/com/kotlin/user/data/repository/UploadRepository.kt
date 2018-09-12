package com.kotlin.user.data.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.user.data.api.UploadApi
import io.reactivex.Observable
import javax.inject.Inject

/**
 * create by 何兆鸿 as 2018/4/19
 * 上传相关的api（真正去访问网络）
 */
class UploadRepository @Inject constructor(){

    // 上传图片
    fun getUploadToken(): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UploadApi::class.java)
                .getUploadToken()
    }

}