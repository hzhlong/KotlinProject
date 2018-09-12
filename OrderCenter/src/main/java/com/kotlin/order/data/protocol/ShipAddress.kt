package com.kotlin.order.data.protocol

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 *  收货地址，kotlin中对象序列化使用Parcelable，java中用Serializable
 */
@SuppressLint("ParcelCreator")
@Parcelize // 【kotlin 1.14版本 可以直接下载ktolin的序列化插件自动序列化也行 Parcelable Code Generator(for kotlin)】
data class ShipAddress(
        val id: Int,
        var shipUserName: String,
        var shipUserMobile: String,
        var shipAddress: String,
        var shipIsDefault: Int
) : Parcelable