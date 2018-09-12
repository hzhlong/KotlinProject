package com.kotlin.mall.data.protocol


/**
 * 检查版本更新请求体
 */
data class UpdateModelReq(var ecode: Int, var emsg: String, var data: UpdateInfo)