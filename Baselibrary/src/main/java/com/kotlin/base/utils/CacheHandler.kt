package com.kotlin.base.utils

import android.content.Context
import android.os.Environment
import com.kotlin.base.R
import java.io.File


/**
 * 作者：何兆鸿 on 2018/6/25.
 * 学无止境~
 * 缓存Handler
 */
class CacheHandler {

    companion object {
        const val CACHE_TAG = "CACHE_DATA"

        val instance : CacheHandler by lazy { CacheHandler() }
    }

    /**
     * crash日志目录
     * @param context
     * @return
     */
    fun getCrashlogDir(context: Context): File {
        val cacheDir = File(getCacheDir(context), "crash")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        return cacheDir
    }

    /**
     * 获取存储的路径， 如果sdcard不存在， 则存储在手机内存空间
     */
    fun getCacheDir(context: Context): File {
        var sdDir: File = if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
            Environment.getExternalStorageDirectory()
        }else{
            context.cacheDir
        }
        val cacheDir = File(sdDir, context.getString(R.string.app_dir))
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        return cacheDir
    }
}
